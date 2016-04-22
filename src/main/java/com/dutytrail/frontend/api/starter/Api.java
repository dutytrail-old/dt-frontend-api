package com.dutytrail.frontend.api.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@ComponentScan(basePackages = {"com.dutytrail.forntend.api"})
@EnableFeignClients(basePackages = {"com.dutytrail.frontend.api"})
public class Api {

    public static void main(String[] args) {
        SpringApplication.run(Api.class, args);
    }

}