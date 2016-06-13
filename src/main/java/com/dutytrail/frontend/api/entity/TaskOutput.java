package com.dutytrail.frontend.api.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "TaskOutput")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"task"})
public class TaskOutput{
    @XmlElement(name = "task") private String task;

    public TaskOutput(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }
}