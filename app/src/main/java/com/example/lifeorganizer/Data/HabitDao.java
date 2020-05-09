package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface HabitDao {
    @Query("SELECT * FROM habit")
    List<Habit> getAll();

    @Query("SELECT * FROM habit WHERE id=:habitID")
    Habit getHabit(int habitID);

    @Insert
    long insert(Habit habit);

    @Delete
    void delete(Habit habit);

    @Update
    void update(Habit habit);
}
