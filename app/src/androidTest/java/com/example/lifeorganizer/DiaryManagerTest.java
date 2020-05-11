package com.example.lifeorganizer;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.lifeorganizer.Backend.AfterCreateDiaryNote;
import com.example.lifeorganizer.Backend.AfterDeleteDiaryNote;
import com.example.lifeorganizer.Backend.AfterEditDiaryNote;
import com.example.lifeorganizer.Backend.AfterGetDiaryNotes;
import com.example.lifeorganizer.Backend.AfterGetHabits;
import com.example.lifeorganizer.Backend.DiaryManager;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Data.DiaryNote;
import com.example.lifeorganizer.Data.Habit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DiaryManagerTest {
    boolean x1 = true;
    boolean x2 = true;
    boolean x3 = true;
    boolean x4 = true;
    boolean x5 = true;
    boolean x6 = true;
    int counter;
    DiaryNote note1 = new DiaryNote("note 1", "the content of note 1", new Date());
    DiaryNote note2 = new DiaryNote("note 2", "the content of note 2", getNextDay(new Date()));


    private Date getNextDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        return calendar.getTime();
    }
    private Date getPrevDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }
    //@Before
    public void clearDiaryTable(){
        final DiaryManager mgr1 = DiaryManager.getInstance(InstrumentationRegistry.getTargetContext());
        x1 = true;
        boolean u = true;
        while (x1) {
            if (u) {
                u = false;
                counter = 5000;
                mgr1.getDiaryNotes(new AfterGetDiaryNotes() {
                    @Override
                    public void afterGetDiaryNotes(List<DiaryNote> diaryNotes) {
                        counter = diaryNotes.size();
                        for (int i = 0; i < diaryNotes.size(); i++) {
                            mgr1.deleteDiaryNote(diaryNotes.get(i), new AfterDeleteDiaryNote() {
                                @Override
                                public void afterDeleteDiaryNote() {
                                    counter--;
                                }
                            });
                        }
                    }
                });
            }
            if(counter == 0){
                x1 = false;
            }
        }
    }

    @Test
    public void testGetNotesWhenEmpty() {
        Log.i("start empty test","----\n---");
        clearDiaryTable();
        DiaryManager mgr1 = DiaryManager.getInstance(InstrumentationRegistry.getTargetContext());
        x2 = true;
        boolean u = true;
        while (x2) {
            if(u) {
                u =false;
                mgr1.getDiaryNotes(new AfterGetDiaryNotes() {
                    @Override
                    public void afterGetDiaryNotes(List<DiaryNote> notes) {
                        x2 = false;
                        assertEquals(true, notes.isEmpty());
                    }
                });
            }
        }
        Log.i("end empty test","----\n---");
    }

    @Test
    public void testAddNote_View_AllList(){
        AddNote();
    }
    public void AddNote(){
        final DiaryManager mgr1 = DiaryManager.getInstance(InstrumentationRegistry.getTargetContext());
        clearDiaryTable();
        x3 = true;
        boolean u = true;
        Log.i("start add test","----\n---");
        while (x3) {
            if(u) {
                u =false;
                mgr1.createDiaryNote(note1, new AfterCreateDiaryNote() {
                    @Override
                    public void afterCreateDiaryNote(DiaryNote diaryNote) {
                        note1 = diaryNote;
                        mgr1.getDiaryNotes(new AfterGetDiaryNotes() {
                            @Override
                            public void afterGetDiaryNotes(List<DiaryNote> notes) {
                                assertEquals(1, notes.size());
                                DiaryNote note = notes.get(0);
                                assertEquals(note1.getId(), note.getId());
                                assertEquals(note1.getTitle(), note.getTitle());
                                assertEquals(note1.getBody(), note.getBody());
                                assertEquals(note1.getCreatedAtDate(), note.getCreatedAtDate());
                                x3 = false;
                            }
                        });
                    }
                });
            }
        }
        Log.i("in add test","----\n---");
        x3 = true;
        u = true;
        while (x3) {
            if(u) {
                u =false;
                mgr1.createDiaryNote(note2, new AfterCreateDiaryNote() {
                    @Override
                    public void afterCreateDiaryNote(final DiaryNote diaryNote) {
                        note2 = diaryNote;
                        mgr1.getDiaryNotes(new AfterGetDiaryNotes() {
                            @Override
                            public void afterGetDiaryNotes(List<DiaryNote> notes) {
                                assertEquals(2, notes.size());
                                DiaryNote note = null;
                                for(DiaryNote diaryNote1: notes){
                                    if(diaryNote1. getId() == note2.getId()){
                                        note = diaryNote1;
                                        break;
                                    }
                                }
                                assertEquals(true,note != null);
                                assertEquals(note2.getTitle(), note.getTitle());
                                assertEquals(note2.getBody(), note.getBody());
                                assertEquals(note2.getCreatedAtDate(), note.getCreatedAtDate());
                                x3 = false;
                            }
                        });
                    }
                });
            }
        }
        Log.i("end add test","----\n---");
    }


    @Test
    public void testEditNote(){
        clearDiaryTable();
        AddNote();
        final DiaryManager mgr1 = DiaryManager.getInstance(InstrumentationRegistry.getTargetContext());
        x4 = true;
        boolean u = true;
        while (x4) {
            if(u) {
                u =false;
                note2.setTitle("note 2+");
                note2.setTitle("the content of note 2+");
                note2.setCreatedAtDate(getNextDay(new Date()));
                mgr1.editDiaryNote(note2, new AfterEditDiaryNote() {
                    @Override
                    public void afterEditDiaryNote() {
                        mgr1.getDiaryNotes(new AfterGetDiaryNotes() {
                            @Override
                            public void afterGetDiaryNotes(List<DiaryNote> notes) {
                                assertEquals(2, notes.size());
                                DiaryNote note = null;
                                for(DiaryNote diaryNote1: notes){
                                    if(diaryNote1. getId() == note2.getId()){
                                        note = diaryNote1;
                                        break;
                                    }
                                }
                                assertEquals(true,note != null);
                                assertEquals(note2.getTitle(), note.getTitle());
                                assertEquals(note2.getBody(), note.getBody());
                                assertEquals(note2.getCreatedAtDate(), note.getCreatedAtDate());
                                x4 = false;
                            }
                        });
                    }
                });
            }
        }
    }
    @Test
    public void testDeleteNote(){
        clearDiaryTable();
        AddNote();
        final DiaryManager mgr1 = DiaryManager.getInstance(InstrumentationRegistry.getTargetContext());
        x5 = true;
        boolean u = true;
        while (x5) {
            if(u) {
                u =false;
                mgr1.deleteDiaryNote(note1, new AfterDeleteDiaryNote() {
                    @Override
                    public void afterDeleteDiaryNote() {
                        mgr1.getDiaryNotes(new AfterGetDiaryNotes() {
                            @Override
                            public void afterGetDiaryNotes(List<DiaryNote> notes) {
                                assertEquals(1, notes.size());
                                DiaryNote note = null;
                                for(DiaryNote diaryNote1: notes){
                                    if(diaryNote1. getId() == note2.getId()){
                                        note = diaryNote1;
                                        break;
                                    }
                                }
                                assertEquals(true,note != null);
                                assertEquals(note2.getTitle(), note.getTitle());
                                assertEquals(note2.getBody(), note.getBody());
                                assertEquals(note2.getCreatedAtDate(), note.getCreatedAtDate());
                                x5 = false;
                            }
                        });
                    }
                });
            }
        }
    }

    @Test
    public void testDayList(){
        final DiaryManager mgr1 = DiaryManager.getInstance(InstrumentationRegistry.getTargetContext());
        clearDiaryTable();
        AddNote();
        x6 = true;
        boolean u = true;
        Log.i("start add test","----\n---");
        while (x6) {
            if(u) {
                u =false;
                mgr1.getDiaryNotes(new Date(),new AfterGetDiaryNotes() {
                    @Override
                    public void afterGetDiaryNotes(List<DiaryNote> notes) {
                        assertEquals(1, notes.size());
                        DiaryNote note = notes.get(0);
                        assertEquals(note1.getId(), note.getId());
                        assertEquals(note1.getTitle(), note.getTitle());
                        assertEquals(note1.getBody(), note.getBody());
                        assertEquals(note1.getCreatedAtDate(), note.getCreatedAtDate());
                        x6 = false;
                    }
                });
            }
        }
        x6 = true;
        u = true;
        Log.i("start add test","----\n---");
        while (x6) {
            if(u) {
                u =false;
                mgr1.getDiaryNotes(getNextDay(new Date()),new AfterGetDiaryNotes() {
                    @Override
                    public void afterGetDiaryNotes(List<DiaryNote> notes) {
                        assertEquals(1, notes.size());
                        DiaryNote note = notes.get(0);
                        assertEquals(note2.getId(), note.getId());
                        assertEquals(note2.getTitle(), note.getTitle());
                        assertEquals(note2.getBody(), note.getBody());
                        assertEquals(note2.getCreatedAtDate(), note.getCreatedAtDate());
                        x6 = false;
                    }
                });
            }
        }
    }

}