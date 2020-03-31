package com.example.lifeorganizer;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

public interface HabitDao {
    @Query("SELECT * FROM job")
    List<Job> getAll();

    @Insert
    void insert(Habit habit);

    @Delete
    void delete(Habit habit);

    @Update
    void update(Habit habit);

    @Transaction
    @Query("SELECT * FROM habit")
    public List<HabitWithTasks> getHabitWithTasks();
}
