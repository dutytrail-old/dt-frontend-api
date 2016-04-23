package com.dutytrail.frontend.api.starter;

import com.dutytrail.frontend.api.server.ApiService;
import com.netflix.config.ConfigurationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@ComponentScan(basePackages = {"com.dutytrail.frontend.api"})
@EnableFeignClients(basePackages = {"com.dutytrail.frontend.api"})
public class Api {

    public static void main(String[] args) {
        configureHystrix();
        SpringApplication.run(Api.class, args);
    }

    private static void configureHystrix() {
        for (Method method : ApiService.class.getMethods()) {
            ConfigurationManager.getConfigInstance().setProperty(String.format("hystrix.command.%s.execution.isolation.thread.timeoutInMilliseconds", method.getName()), 1500000);
        }
    }

}