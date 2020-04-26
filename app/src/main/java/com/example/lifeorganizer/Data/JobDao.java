package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface JobDao {
    @Query("SELECT * FROM job")
    List<Job> getAll();

    @Query("SELECT * FROM job WHERE id=:jobID")
    Job getJob(int jobID);

    @Insert
    long insert(Job job);

    @Delete
    void delete(Job job);

    @Update
    void update(Job job);
}
