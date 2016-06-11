package com.dutytrail.frontend.api.server;

import com.dutytrail.frontend.api.remote.DutyClient;
import com.dutytrail.frontend.api.remote.TrailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiService {

    @Value("${ping.alive}")
    private String configPingAlive;

    @Autowired
    DutyClient dutyClient;

    @Autowired
    TrailClient trailClient;

    @RequestMapping("/ping")
    public String ping() {
        return "Api Front End Alive. Profile in use: "+this.configPingAlive;
    }

    @RequestMapping("/dutyPing")
    public String dutyPing() {
        return "Duty Service says: "+this.dutyClient.ping();
    }

    @RequestMapping("/trailPing")
    public String trailPing() {
        return "Trail Service says: "+this.trailClient.ping();
    }

}