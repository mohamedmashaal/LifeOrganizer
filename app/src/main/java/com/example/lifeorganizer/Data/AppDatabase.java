package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

@Database(entities = {Task.class, Job.class, Habit.class, Event.class, DiaryNote.class}, version = 1, exportSchema = false)

@TypeConverters({DateConverter.class})

public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract JobDao jobDao();
    public abstract HabitDao habitDao();
    public abstract EventDao eventDao();
    public abstract DiaryNoteDao diaryNoteDao();

    @Override
    public void init(@NonNull DatabaseConfiguration configuration) {
        super.init(configuration);
    }
}