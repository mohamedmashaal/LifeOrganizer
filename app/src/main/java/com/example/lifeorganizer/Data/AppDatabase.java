package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Task.class, Job.class, Habit.class}, version = 2, exportSchema = false)

@TypeConverters({DateConverter.class})

public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract JobDao jobDao();
    public abstract HabitDao habitDao();
}