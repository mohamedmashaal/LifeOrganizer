package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Query("SELECT * FROM event where id = :id")
    Event get(int id);

    @Insert
    void insert(Event event);

    @Delete
    void delete(Event event);

    @Update
    void update(Event event);
}
