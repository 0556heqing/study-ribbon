package com.heqing.ribbon.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * 自定义规则。。每2次都是同一台服务
 * @author heqing
 * @date 2021/11/12 16:16
 */
public class TwoRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return two(getLoadBalancer(), key);
    }

    /**
     * 总共被调用的次数，目前要求每台被调用2次
     */
    private int total = 1;

    /**
     * 当前提供服务的机器号
     */
    private int currentIndex = 0;

    public Server two(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        //刚开始不知道服务哪个为null
        Server server = null;

        //循环判断服务
        while (server == null) {
            //判断线程是不是中断
            if (Thread.interrupted()) {
                return null;
            }

            //获取活着的微服务
            List<Server> upList = lb.getReachableServers();
            //统计微服务的数量
            int serverCount = upList.size();
            // 没有活着的微服务直接结束
            if (serverCount == 0) {
                return null;
            }

            // 点击2次才换下一个ip
            int num = total;
            if((num%2) == 1) {
                num = num + 1;
            }
            currentIndex = num % 4;
            if(currentIndex >= upList.size()) {
                currentIndex = 1;
            }
            server = upList.get(currentIndex);
            total++;

            //如果微服务微null
            if (server == null) {
                //线程礼让
                Thread.yield();
                continue;
            }

            //假如这个微server是活的
            if (server.isAlive()) {
                //返回这个微服务
                return (server);
            }

            server = null;
            Thread.yield();
        }
        return server;
    }

}
