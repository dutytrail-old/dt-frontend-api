package com.dutytrail.frontend.api.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "status")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"dutyServicePing","dutyServicePing","configPing"})
public class Status{
    @XmlElement(name = "duty") private String dutyServicePing;
    @XmlElement(name = "trail") private String trailServicePing;
    @XmlElement(name = "config") private String configPing;

    public Status(String dutyServicePing, String trailServicePing, String configPing) {
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