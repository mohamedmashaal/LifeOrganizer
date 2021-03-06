package com.example.lifeorganizer.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Habit implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    // days encoded as an integer
    @ColumnInfo(name = "daysMask")
    private String daysMask;

    @ColumnInfo(name = "hrsPerWeek")
    private int hrsPerWeek;

    @ColumnInfo(name = "startDate")
    private Date startDate;

    public Habit(String title, String description, String daysMask, int hrsPerWeek, Date startDate) {
        this.title = title;
        this.description = description;
        this.daysMask = daysMask;
        this.hrsPerWeek = hrsPerWeek;
        this.startDate = startDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDaysMask() {
        return daysMask;
    }

    public void setDaysMask(String daysMask) {
        this.daysMask = daysMask;
    }

    public int getHrsPerWeek() {
        return hrsPerWeek;
    }

    public void setHrsPerWeek(int hrsPerWeek) {
        this.hrsPerWeek = hrsPerWeek;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}