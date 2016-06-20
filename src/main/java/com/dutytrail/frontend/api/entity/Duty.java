package com.dutytrail.frontend.api.entity;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Duty")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id","name"})
public class Duty {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "name")
    private String name;

    @XmlElementWrapper(name = "Trails")
    @XmlElement(name = "Trail")
    private List<Trail> trails;

    public Duty() {

    }

    public Duty(Long id, String name, List<Trail> trails) {
        this.id = id;
        this.name = name;
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
}