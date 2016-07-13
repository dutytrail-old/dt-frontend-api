package com.dutytrail.frontend.api.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "DutyInput")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name"})
public class DutyInput {

    @XmlElement(name = "name") private String name;
    @XmlElement(name = "userId") private Long userId;

    public DutyInput(){

    }

    public DutyInput(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}