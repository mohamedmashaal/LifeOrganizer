package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class HabitWithTasks {
    @Embedded
    public Habit habit;
    @Relation(
            parentColumn = "id",
            entityColumn = "habitID"
    )
    public List<Task> tasks;
}
