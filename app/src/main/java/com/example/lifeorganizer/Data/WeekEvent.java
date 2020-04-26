package com.example.lifeorganizer.Data;

import android.graphics.Color;
import android.graphics.ColorSpace;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class WeekEvent extends WeekViewEvent {

    private Random random = new Random();
    private int[] colors = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA};

    public WeekEvent() {
        super();
    }

    public WeekEvent(long id, String name, int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute) {
        super(id, name, startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute);
        setColor(colors[(int)(Math.random() * colors.length)]);
    }

    public WeekEvent(long id, String name, String location, Calendar startTime, Calendar endTime) {
        super(id, name, location, startTime, endTime);
    }

    public WeekEvent(long id, String name, Calendar startTime, Calendar endTime) {
        super(id, name, startTime, endTime);
    }

    @Override
    public Calendar getStartTime() {
        return super.getStartTime();
    }

    @Override
    public void setStartTime(Calendar startTime) {
        super.setStartTime(startTime);
    }

    @Override
    public Calendar getEndTime() {
        return super.getEndTime();
    }

    @Override
    public void setEndTime(Calendar endTime) {
        super.setEndTime(endTime);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getLocation() {
        return super.getLocation();
    }

    @Override
    public void setLocation(String location) {
        super.setLocation(location);
    }

    @Override
    public int getColor() {
        return super.getColor();
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
    }

    @Override
    public long getId() {
        return super.getId();
    }
}
