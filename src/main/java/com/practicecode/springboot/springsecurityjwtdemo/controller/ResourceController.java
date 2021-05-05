package com.practicecode.springboot.springsecurityjwtdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
