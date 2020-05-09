package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateUtils;

import com.example.lifeorganizer.Data.DatabaseClient;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.Data.Job;
import com.example.lifeorganizer.Data.Task;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class TaskManager {
    private Context mCtx;
    private static TaskManager mInstance;

    private TaskManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized TaskManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new TaskManager(mCtx);
        }
        return mInstance;
    }

    public void createTask(final Task task, final AfterCreateTask callback) {

        class CreateTask extends AsyncTask<Void, Void, Long> {
            @Override
            protected Long doInBackground(Void... voids) {
                // adding to database
                long id = DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .taskDao().insert(task);
                return id;
            }

            @Override
            protected void onPostExecute(Long id) {
                super.onPostExecute(id);
                task.setId(id.intValue());
                callback.afterCreateTask(task);
            }
        }

        CreateTask st = new CreateTask();
        st.execute();
    }

    public void editTask(final Task task, final AfterEditTask callback) {

        class EditTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .taskDao().update(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterEditTask();
            }
        }

        EditTask st = new EditTask();
        st.execute();
    }

    public void deleteTask(final Task task, final AfterDeleteTask callback) {

        class DeleteTask  extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .taskDao().delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterDeleteTask();
            }
        }

        DeleteTask st = new DeleteTask();
        st.execute();
    }

    public void getTasks(final AfterGetTasks callback) {
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                callback.afterGetTasks(tasks);
            }
        }

        GetTasks gh = new GetTasks();
        gh.execute();
    }

    public void getTasks(final Date date, final AfterGetTasks callback) {
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> dateTasks = new ArrayList<>();
                for(Task task : tasks){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(sdf.format(task.getDate()).equals(sdf.format(date)))
                        dateTasks.add(task);

                }

                callback.afterGetTasks(dateTasks);
                //callback.afterGetTasks(tasks);
            }
        }

        GetTasks gh = new GetTasks();
        gh.execute();
    }

    // month: 1-12
    public void getTasks(final int month,  final int year, final AfterGetTasks callback) {
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> dateTasks = new ArrayList<>();
                for(Task task : tasks){
                    Date date = task.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int task_year = cal.get(Calendar.YEAR);
                    int task_month = cal.get(Calendar.MONTH);
                    //int day = cal.get(Calendar.DAY_OF_MONTH);

                    if(task_month == month && task_year == year)
                        dateTasks.add(task);

                }

                callback.afterGetTasks(dateTasks);
                //callback.afterGetTasks(tasks);
            }
        }

        GetTasks gh = new GetTasks();
        gh.execute();
    }

    // Habit-related methods

    public void getTasksForHabit(final Habit habit, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(task.getHabitID() == habit.getId())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForAllHabits(final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(task.isHabitTask())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForHabitAndDate(final Habit habit, final Date date, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(sdf.format(task.getDate()).equals(sdf.format(date)) && task.getHabitID() == habit.getId())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForAllHabitsAndDate(final Date date, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(sdf.format(task.getDate()).equals(sdf.format(date)) && task.isHabitTask())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForHabitAndMonth(final Habit habit, final int month, final int year, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    Date date = task.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int task_year = cal.get(Calendar.YEAR);
                    int task_month = cal.get(Calendar.MONTH)-1;

                    if(task_year == year && task_month == month && task.getHabitID() == habit.getId())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForAllHabitsAndMonth(final int month, final int year, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    Date date = task.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int task_year = cal.get(Calendar.YEAR);
                    int task_month = cal.get(Calendar.MONTH)-1;

                    if(task_year == year && task_month == month && task.isHabitTask())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    // Job-related methods

    public void getTasksForJob(final Job job, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(task.getJobID() == job.getId())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForAllJobs(final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(task.isJobTask())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForJobAndDate(final Job job, final Date date, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(sdf.format(task.getDate()).equals(sdf.format(date)) && task.getJobID() == job.getId())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForAllJobsAndDate(final Date date, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(sdf.format(task.getDate()).equals(sdf.format(date)) && task.isJobTask())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForJobAndMonth(final Job job, final int month, final int year, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    Date date = task.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int task_year = cal.get(Calendar.YEAR);
                    int task_month = cal.get(Calendar.MONTH)-1;

                    if(task_year == year && task_month == month && task.getJobID() == job.getId())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getTasksForAllJobsAndMonth(final int month, final int year, final AfterGetTasks callback){
        class MyAsyncTask extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> tasks = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);

                List<Task> specificTasks = new ArrayList<>();
                for(Task task : tasks){
                    Date date = task.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int task_year = cal.get(Calendar.YEAR);
                    int task_month = cal.get(Calendar.MONTH)-1;

                    if(task_year == year && task_month == month && task.isJobTask())
                        specificTasks.add(task);
                }

                callback.afterGetTasks(specificTasks);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }
}
