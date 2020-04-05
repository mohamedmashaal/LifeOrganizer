package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE date=date(:dateObj)")
    List<Task> getAll(Date dateObj);
    Date date = new Date();

    @Query("SELECT * FROM task WHERE jobID=:jobId")
    List<Task> getForSpecificJob(final int jobId);

    @Query("SELECT * FROM task WHERE habitID=:habitId")
    List<Task> getForSpecificHabit(final int habitId);

    @Query("SELECT * FROM task WHERE habitID=:habitId AND date=date(:dateObj)")
    List<Task> getForSpecificHabitِAndDate(final int habitId, Date dateObj);

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);
}
