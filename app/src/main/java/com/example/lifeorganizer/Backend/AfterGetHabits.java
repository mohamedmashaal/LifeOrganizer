package com.example.lifeorganizer.Backend;

import com.example.lifeorganizer.Data.Habit;

import java.util.List;

public interface AfterGetHabits {
    public void afterGetHabits(List<Habit> habits);
}
