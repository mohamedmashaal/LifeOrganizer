package com.example.lifeorganizer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.lifeorganizer.Backend.AfterCreateHabit;
import com.example.lifeorganizer.Backend.AfterGetHabits;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Data.Habit;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HabitManagerTest {
    boolean x = true;
    @Test
    public void testSingletonClass() {
        HabitManager mgr1 = HabitManager.getInstance(InstrumentationRegistry.getTargetContext());
        HabitManager mgr2 = HabitManager.getInstance(InstrumentationRegistry.getTargetContext());

        assert mgr1.equals(mgr2);
    }

    @Test
    public void testGetHabitsWhenEmpty() {
        HabitManager mgr1 = HabitManager.getInstance(InstrumentationRegistry.getTargetContext());
        final List<Habit> habitList = new ArrayList<>();
        Log.d("Test2", "===============\nhabits length\n==============" );
        x = true;
        boolean u = true;
        while (x) {
            if(u) {
                u =false;
                mgr1.getHabits(new AfterGetHabits() {
                    @Override
                    public void afterGetHabits(List<Habit> habits) {
                        x = false;
                        habitList.addAll(habits);
                        Log.i("habits length", "===============\n" + habits.size() + "\n===============");
                        assertEquals(true, habits.isEmpty());
                    }
                });
            }
        }
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