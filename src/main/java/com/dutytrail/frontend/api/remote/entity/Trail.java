package com.dutytrail.frontend.api.remote.entity;

import javax.xml.bind.annotation.*;
import java.sql.Timestamp;

@XmlRootElement(name = "Trail")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"userId","status"})
public class Trail {

    @XmlElement(name = "userId") private Long userId;
    @XmlElement(name = "status") private String status;
    @XmlElement(name = "timestamp") private Timestamp timestamp;

    public Trail() {

    }

    public Trail(Long userId, String status, Timestamp timestamp) {
        this.userId = userId;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}