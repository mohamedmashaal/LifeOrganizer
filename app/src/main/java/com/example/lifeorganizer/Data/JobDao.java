package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

public interface JobDao {
    @Query("SELECT * FROM job")
    List<Job> getAll();

    @Insert
    void insert(Job job);

    @Delete
    void delete(Job job);

    @Update
    void update(Job job);

    @Transaction
    @Query("SELECT * FROM job")
    public List<JobWithTasks> getJobWithTasks();
}
