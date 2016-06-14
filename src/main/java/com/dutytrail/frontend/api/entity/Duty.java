package com.dutytrail.frontend.api.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Duty")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id","name"})
public class Duty {

    @XmlElement(name = "id") private Long id;
    @XmlElement(name = "name") private String name;

    public Duty() {

    }

    public Duty(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}