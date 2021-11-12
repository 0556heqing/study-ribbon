package com.heqing.ribbon.config;

import com.heqing.ribbon.rule.TwoRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author heqing
 * @date 2021/11/12 14:13
 */
@Configuration
public class ServerConfig {

    /**
     * '@LoadBalanced'注解表示使用Ribbon实现客户端负载均衡
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 自定义规则：每2次换一个ip
     */
    @Bean
    public IRule rule() {
        return new TwoRule();
    }

}
