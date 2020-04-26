package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lifeorganizer.Data.DatabaseClient;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.Data.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HabitManager {
    private Context mCtx;
    private static HabitManager mInstance;

    private HabitManager(Context mCtx) {
        this.mCtx = mCtx;
    }
    private HabitManager() { }

    public static synchronized HabitManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new HabitManager(mCtx);
        }
        return mInstance;
    }

    public static synchronized HabitManager getInstance() {
        if (mInstance == null) {
            mInstance = new HabitManager();
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
                TaskManager.getInstance(mCtx).getTasksForHabit(habit, new AfterGetTasks() {
                    @Override
                    public void afterGetTasks(List<Task> tasks) {

                        for(Task task : tasks){
                            TaskManager.getInstance(mCtx).deleteTask(task, new AfterDeleteTask() {
                                @Override
                                public void afterDeleteTask() {}
                            });
                        }
                    }
                });

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

    public void getHabit(final int habitID, final AfterGetHabit callback) {
        class MyAsyncTask extends AsyncTask<Void, Void, Habit> {
            @Override
            protected Habit doInBackground(Void... voids) {
                Habit habit = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .habitDao()
                        .getHabit(habitID);

                return habit;
            }

            @Override
            protected void onPostExecute(Habit habit) {
                super.onPostExecute(habit);
                callback.afterGetHabit(habit);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void createTasksFromHabits(final Date date, final int day, final AfterCreateTasksFromHabits callback){
        //final List<Task> finalTasks = new ArrayList<>();
        getHabits(new AfterGetHabits() {
            @Override
            public void afterGetHabits(List<Habit> habits) {
                for(final Habit habit : habits){
                    String daysMask = habit.getDaysMask();
                    if(daysMask.charAt(day) == '1'){
                        TaskManager.getInstance(mCtx).getTasksForHabitAndDate(habit, date, new AfterGetTasks() {
                            @Override
                            public void afterGetTasks(List<Task> tasks) {
                                if(tasks.isEmpty()){
                                    Task task = new Task(habit.getTitle(), date, false, 0);
                                    task.setHabitID(habit.getId());
                                    task.setHabitTask(true);
                                    task.setJobTask(false);

                                    //finalTasks.add(task);

                                    TaskManager.getInstance(mCtx).createTask(task, new AfterCreateTask() {
                                        @Override
                                        public void afterCreateTask() {}
                                    });
                                }
                                /*else{
                                    finalTasks.addAll(tasks);
                                }*/
                            }
                        });
                    }
                }

                callback.afterCreateTasksFromHabits();
            }
        });
    }
}
