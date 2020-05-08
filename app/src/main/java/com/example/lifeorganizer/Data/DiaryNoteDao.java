package com.example.lifeorganizer.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DiaryNoteDao {
    @Query("SELECT * FROM diarynote")
    List<DiaryNote> getAll();

    @Query("SELECT * FROM diarynote WHERE id=:noteID")
    DiaryNote getDiaryNote(int noteID);

    @Insert
    void insert(DiaryNote diaryNote);

    @Delete
    void delete(DiaryNote diaryNote);

    @Update
    void update(DiaryNote diaryNote);
}
