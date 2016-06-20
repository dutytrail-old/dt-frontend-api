package com.dutytrail.frontend.api.entity;

import javax.xml.bind.annotation.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@XmlRootElement(name = "Trail")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"userId","status"})
public class Trail {

    @XmlElement(name = "userId") private Long userId;
    @XmlElement(name = "status") private String status;
    @XmlElement(name = "date") private String date;

    public Trail() {

    }

    public Trail(Long userId, String status, Timestamp timestamp) {
        this.userId = userId;
        this.status = status;
        this.date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);
    }

    public Long getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}