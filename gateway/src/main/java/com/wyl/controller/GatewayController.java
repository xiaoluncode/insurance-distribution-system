package com.wyl.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@作者：wyl  
*/
@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Gateway";
    }
}
