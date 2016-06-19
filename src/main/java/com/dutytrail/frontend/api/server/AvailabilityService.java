package com.dutytrail.frontend.api.server;

import com.dutytrail.frontend.api.entity.ApiOutput;
import com.dutytrail.frontend.api.entity.Status;
import com.dutytrail.frontend.api.remote.DutyClient;
import com.dutytrail.frontend.api.remote.TrailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

@RestController
public class AvailabilityService {

    @Value("${ping.alive}") private String configPingAlive;
    @Autowired private DutyClient dutyClient;
    @Autowired private TrailClient trailClient;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ApiOutput ping() {
        return new ApiOutput("Api Front End Alive. Profile in use: "+this.configPingAlive);
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ApiOutput status() {
        return new ApiOutput(new Status(this.dutyClient.ping(), this.trailClient.ping(), this.configPingAlive));
    }

}