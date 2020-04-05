package com.example.lifeorganizer.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(indices = {@Index("habitID"), @Index("jobID")}
        /*,foreignKeys = {
            @ForeignKey(
                entity = Habit.class,
                parentColumns = "id",
                childColumns = "habitID",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
            ),
            @ForeignKey(
                entity = Job.class,
                parentColumns = "id",
                childColumns = "jobID",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
            )
        }*/
        )

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
    private int timeSpentInSeconds;

    // if the parent is a Job
    @ColumnInfo(name = "jobID")
    private int jobID;

    // if the parent is a Habit
    @ColumnInfo(name = "habitID")
    private int habitID;

    @ColumnInfo(name = "isJobTask")
    private boolean isJobTask;

    @ColumnInfo(name = "isHabitTask")
    private boolean isHabitTask;

    public Task(String title, Date date, boolean finished, int timeSpentInSeconds) {
        this.title = title;
        this.date = date;
        this.finished = finished;
        this.timeSpentInSeconds = timeSpentInSeconds;
    }

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

    public int getTimeSpentInSeconds() {
        return timeSpentInSeconds;
    }

    public void setTimeSpentInSeconds(int timeSpentInSeconds) {
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

    public boolean isJobTask() {
        return isJobTask;
    }

    public void setJobTask(boolean jobTask) {
        isJobTask = jobTask;
    }

    public boolean isHabitTask() {
        return isHabitTask;
    }

    public void setHabitTask(boolean habitTask) {
        isHabitTask = habitTask;
    }
}