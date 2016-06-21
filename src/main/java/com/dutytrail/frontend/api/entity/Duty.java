package com.dutytrail.frontend.api.entity;

import com.dutytrail.frontend.api.remote.entity.User;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "duty")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id","name", "trails", "subscriptions"})
public class Duty {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "name")
    private String name;

    @XmlElementWrapper(name = "subscriptions")
    @XmlElement(name = "user")
    private List<User> subscriptions;

    @XmlElementWrapper(name = "trails")
    @XmlElement(name = "Trail")
    private List<Trail> trails;

    public Duty() {

    }

    public Duty(Long id, String name, List<User> subscriptions, List<Trail> trails) {
        this.id = id;
        this.name = name;
        this.subscriptions = subscriptions;
        this.trails = trails;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public List<Trail> getTrails() {
        return trails;
    }

    public List<User> getSubscriptions() {
        return subscriptions;
    }
}