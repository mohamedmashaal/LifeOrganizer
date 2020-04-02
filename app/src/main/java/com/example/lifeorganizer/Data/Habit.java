package com.example.lifeorganizer.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
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
    private int daysMask;

    @ColumnInfo(name = "hrsPerWeek")
    private int hrsPerWeek;

    @ColumnInfo(name = "startDate")
    private String startDate;

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

    public int getDaysMask() {
        return daysMask;
    }

    public void setDaysMask(int daysMask) {
        this.daysMask = daysMask;
    }

    public int getHrsPerWeek() {
        return hrsPerWeek;
    }

    public void setHrsPerWeek(int hrsPerWeek) {
        this.hrsPerWeek = hrsPerWeek;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}