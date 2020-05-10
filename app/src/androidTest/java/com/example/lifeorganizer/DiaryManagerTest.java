package com.example.lifeorganizer;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.lifeorganizer.Backend.AfterDeleteDiaryNote;
import com.example.lifeorganizer.Backend.AfterGetDiaryNotes;
import com.example.lifeorganizer.Backend.AfterGetHabits;
import com.example.lifeorganizer.Backend.DiaryManager;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Data.DiaryNote;
import com.example.lifeorganizer.Data.Habit;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DiaryManagerTest {
    boolean x = true;
    @After
    public void clearDiaryTable(){
        final DiaryManager mgr1 = DiaryManager.getInstance(InstrumentationRegistry.getTargetContext());
        mgr1.getDiaryNotes(new AfterGetDiaryNotes() {
            @Override
            public void afterGetDiaryNotes(List<DiaryNote> diaryNotes) {
                for (int i = 0; i < diaryNotes.size(); i++) {
                    mgr1.deleteDiaryNote(diaryNotes.get(i), new AfterDeleteDiaryNote() {
                        @Override
                        public void afterDeleteDiaryNote() {
                        }
                    });
                }
            }
        });
    }

    @Test
    public void testGetNotesWhenEmpty() {
        DiaryManager mgr1 = DiaryManager.getInstance(InstrumentationRegistry.getTargetContext());
        //Log.d("Test2", "===============\nhabits length\n==============" );
        x = true;
        boolean u = true;
        while (x) {
            if(u) {
                u =false;
                mgr1.getDiaryNotes(new AfterGetDiaryNotes() {
                    @Override
                    public void afterGetDiaryNotes(List<DiaryNote> notes) {
                        x = false;
                        //Log.i("habits length", "===============\n" + notes.size() + "\n===============");
                        assertEquals(true, notes.isEmpty());
                    }
                });
            }
        }
    }
    @Test
    public void testAddHabit(){

    }
    @Test
    public void testEditHabit(){

    }
    @Test
    public void testDeleteHabit(){

    }
    @Test
    public void testHabitDetails(){

    }
    @Test
    public void testHabitList(){

    }


    /*@Test
    public void testGetHabitsAfterAddingOneHabit() {
        HabitManager mgr1 = HabitManager.getInstance(InstrumentationRegistry.getTargetContext());
        Habit habit = new Habit("Habit1", "This is habit one", "0010100", 5, new Date());
        mgr1.createHabit(habit, new AfterCreateHabit() {
            @Override
            public void afterCreateHabit() {
                final List<Habit> habitList = new ArrayList<>();
                mgr1.getHabits(new AfterGetHabits() {
                    @Override
                    public void afterGetHabits(List<Habit> habits) {
                        habitList.addAll(habits);
                    }
                });
            }
        });

        assert habitList.isEmpty() && ;
    }*/
}