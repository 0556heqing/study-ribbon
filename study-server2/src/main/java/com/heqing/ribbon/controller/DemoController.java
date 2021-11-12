package com.heqing.ribbon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heqing
 * @date 2021/7/16 16:34
 */
@RestController
@RequestMapping("/ribbon")
public class DemoController {

    private  final Logger log = LoggerFactory.getLogger(DemoController.class);

    @Value("${server.port}")
    private String ip;

    @GetMapping("/server")
    public String server(){
        return "主服务器 ip：" + ip;
    }
}
