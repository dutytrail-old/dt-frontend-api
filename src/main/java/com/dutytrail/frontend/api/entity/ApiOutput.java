package com.dutytrail.frontend.api.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "ApiOutput")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"message"})
public class ApiOutput {
    @XmlElement(name = "message") private String message;

    public ApiOutput(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}