package com.example.lifeorganizer.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "finished")
    private boolean finished;

    @ColumnInfo(name = "timeSpentInSeconds")
    private boolean timeSpentInSeconds;

    // if the parent is a Job
    @ColumnInfo(name = "jobID")
    private int jobID;

    // if the parent is a Habit
    @ColumnInfo(name = "habitID")
    private int habitID;

    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isTimeSpentInSeconds() {
        return timeSpentInSeconds;
    }

    public void setTimeSpentInSeconds(boolean timeSpentInSeconds) {
        this.timeSpentInSeconds = timeSpentInSeconds;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getHabitID() {
        return habitID;
    }

    public void setHabitID(int habitID) {
        this.habitID = habitID;
    }
}