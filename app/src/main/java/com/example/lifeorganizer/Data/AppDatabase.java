package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Task.class, Job.class, Habit.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract JobDao jobDao();
    public abstract HabitDao habitDao();
    //public abstract HabitWithTasks habitWithTasksDao();
    //public abstract JobWithTasks jobWithTasksDao();
}