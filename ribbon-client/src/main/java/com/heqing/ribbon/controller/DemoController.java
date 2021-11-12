package com.heqing.ribbon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author heqing
 * @date 2021/7/16 16:34
 */
@RestController
@RequestMapping("/study")
public class DemoController {

    private  final Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/ribbon")
    public String ribbon(){
        String services = restTemplate.getForObject("http://study-ribbon-server/ribbon/server", String.class);
        return "调用 --> " + services;
    }
}
