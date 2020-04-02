package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.lifeorganizer.Data.DatabaseClient;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.MainActivity;

import java.util.List;

public class HabitManager {
    private Context mCtx;
    private static HabitManager mInstance;
    private HabitManagerDelegate delegate;

    private HabitManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized HabitManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new HabitManager(mCtx);
        }
        return mInstance;
    }

    public void setDelegate(HabitManagerDelegate delegate) {
        this.delegate = delegate;
    }

    public void createHabit(final Habit habit) {

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
                delegate.afterCreateHabit();
                //finish();
                //startActivity(new Intent(mCtx, MainActivity.class));
                Toast.makeText(mCtx, "Habit Created", Toast.LENGTH_LONG).show();
            }
        }

        CreateHabit st = new CreateHabit();
        st.execute();
    }

    public void editHabit(final Habit habit) {

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
                delegate.afterEditHabit();
                Toast.makeText(mCtx, "Habit Updated", Toast.LENGTH_LONG).show();
            }
        }

        EditHabit st = new EditHabit();
        st.execute();
    }

    public void deleteHabit(final Habit habit) {

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
                delegate.afterDeleteHabit();
                Toast.makeText(mCtx, "Habit Deleted", Toast.LENGTH_LONG).show();
            }
        }

        DeleteHabit st = new DeleteHabit();
        st.execute();
    }

    public void getHabits() {
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
                //TasksAdapter tasksAdapter = new TasksAdapter(MainActivity.this, tasks);
                //recyclerView.setAdapter(tasksAdapter);
            }
        }

        GetHabits gh = new GetHabits();
        gh.execute();
    }
}
