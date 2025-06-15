package com.wyl.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@作者：wyl  
*/
@RestController
public class ConfigController {
    @Value("${some.config.value:default}")
    private String configValue;

    @GetMapping("/config/value")
    public String getConfigValue() {
        return "Config value: " + configValue;
    }
}
