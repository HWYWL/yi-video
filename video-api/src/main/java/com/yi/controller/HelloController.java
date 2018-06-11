package com.yi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试视图
 * @author YI
 * @date 2018-6-11 17:53:38
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring Boot";
    }
}
