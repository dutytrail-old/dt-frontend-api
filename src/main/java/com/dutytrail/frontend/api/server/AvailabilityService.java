package com.dutytrail.frontend.api.server;

import com.dutytrail.frontend.api.remote.DutyClient;
import com.dutytrail.frontend.api.remote.TrailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.*;

@RestController
public class AvailabilityService {

    @Value("${ping.alive}") private String configPingAlive;
    @Autowired private DutyClient dutyClient;
    @Autowired private TrailClient trailClient;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public String ping() {
        return "Api Front End Alive. Profile in use: "+this.configPingAlive;
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Status status() {
        return new Status(this.dutyClient.ping(), this.trailClient.ping(), this.configPingAlive);
    }

    @XmlRootElement(name = "status")
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"dutyServicePing","dutyServicePing","configPing"})
    private class Status{
        @XmlElement(name = "duty") private String dutyServicePing;
        @XmlElement(name = "trail") private String trailServicePing;
        @XmlElement(name = "config") private String configPing;

        Status(String dutyServicePing, String trailServicePing, String configPing) {
            this.dutyServicePing = dutyServicePing;
            this.trailServicePing = trailServicePing;
            this.configPing = configPing;
        }

        public String getDutyServicePing() {
            return dutyServicePing;
        }

        public String getTrailServicePing() {
            return trailServicePing;
        }

        public String getConfigPing() {
            return configPing;
        }
    }

}