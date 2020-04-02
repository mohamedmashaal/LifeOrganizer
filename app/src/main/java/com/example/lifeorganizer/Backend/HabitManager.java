package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lifeorganizer.Data.DatabaseClient;
import com.example.lifeorganizer.Data.Habit;

import java.util.List;

public class HabitManager {
    private Context mCtx;
    private static HabitManager mInstance;

    private HabitManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized HabitManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new HabitManager(mCtx);
        }
        return mInstance;
    }

    public void createHabit(final Habit habit, final AfterCreateHabit callback) {

        class CreateHabit extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                // adding to database
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .habitDao().insert(habit);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterCreateHabit();
            }
        }

        CreateHabit st = new CreateHabit();
        st.execute();
    }

    public void editHabit(final Habit habit, final AfterEditHabit callback) {

        class EditHabit extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .habitDao().update(habit);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterEditHabit();
            }
        }

        EditHabit st = new EditHabit();
        st.execute();
    }

    public void deleteHabit(final Habit habit, final AfterDeleteHabit callback) {

        class DeleteHabit  extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .habitDao().delete(habit);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterDeleteHabit();
            }
        }

        DeleteHabit st = new DeleteHabit();
        st.execute();
    }

    public void getHabits(final AfterGetHabits callback) {
        class GetHabits extends AsyncTask<Void, Void, List<Habit>> {
            @Override
            protected List<Habit> doInBackground(Void... voids) {
                List<Habit> habits = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .habitDao()
                        .getAll();
                return habits;
            }

            @Override
            protected void onPostExecute(List<Habit> habits) {
                super.onPostExecute(habits);
                callback.afterGetHabits(habits);
            }
        }

        GetHabits gh = new GetHabits();
        gh.execute();
    }
}
