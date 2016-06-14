package com.dutytrail.frontend.api.entity;

public class DutyInput {
    private String name;

    public DutyInput() {

    }

    public DutyInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}