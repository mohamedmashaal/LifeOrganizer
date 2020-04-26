package com.example.lifeorganizer.fragments;

import android.support.annotation.NonNull;

import java.util.Date;

public class TaskHolder {
    public String taskName;
    public Date taskDeadline;

    public TaskHolder(String taskName, Date taskDeadline){
        this.taskName = taskName;
        this.taskDeadline = taskDeadline;
    }

    public TaskHolder() {

    }

    @NonNull
    @Override
    public String toString() {
        return taskName + ": " + taskDeadline.toString();
    }
}
