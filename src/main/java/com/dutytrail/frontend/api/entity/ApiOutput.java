package com.dutytrail.frontend.api.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "ApiOutput")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"content"})
public class ApiOutput {
    @XmlElement(name = "content") private Object content;

    public ApiOutput(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }
}