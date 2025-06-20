package com.wyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
@作者：wyl  
*/
@SpringBootApplication(scanBasePackages = {"com.wyl", "com.wyl.service"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class CommissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommissionApplication.class, args);
    }
}
