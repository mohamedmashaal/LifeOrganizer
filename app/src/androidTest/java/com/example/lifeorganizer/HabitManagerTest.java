package com.example.lifeorganizer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.lifeorganizer.Backend.AfterGetHabits;
import com.example.lifeorganizer.Backend.HabitManager;
import com.example.lifeorganizer.Data.Habit;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
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
        mgr1.getHabits(new AfterGetHabits() {
            @Override
            public void afterGetHabits(List<Habit> habits) {
                habitList.addAll(habits);
            }
        });

        try {
            new CountDownLatch(3).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assert habitList.isEmpty();
    }
}