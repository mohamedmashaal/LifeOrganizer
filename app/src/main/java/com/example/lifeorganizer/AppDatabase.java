package com.example.lifeorganizer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Task.class, Job.class, Habit.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract JobDao jobDao();
    public abstract HabitDao habitDao();
    public abstract HabitWithTasks habitWithTasksDao();
    public abstract JobWithTasks jobWithTasksDao();
}