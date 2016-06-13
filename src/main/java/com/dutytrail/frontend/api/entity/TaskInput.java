package com.dutytrail.frontend.api.entity;

public class TaskInput{
    private String task;

    public TaskInput() {

    }

    public TaskInput(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}