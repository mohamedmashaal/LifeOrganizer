package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lifeorganizer.Data.DatabaseClient;
import com.example.lifeorganizer.Data.Job;
import com.example.lifeorganizer.Data.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobManager {
    private Context mCtx;
    private static JobManager mInstance;

    private JobManager(Context mCtx) {
        this.mCtx = mCtx;
    }
    private JobManager() { }

    public static synchronized JobManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new JobManager(mCtx);
        }
        return mInstance;
    }

    public static synchronized JobManager getInstance() {
        if (mInstance == null) {
            mInstance = new JobManager();
        }
        return mInstance;
    }

    public void createJob(final Job job, final List<Task> subtasks, final AfterCreateJob callback) {

        class MyTask extends AsyncTask<Void, Void, Long> {
            @Override
            protected Long doInBackground(Void... voids) {
                // adding to database
                long id = DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .jobDao().insert(job);
                return id;
            }

            @Override
            protected void onPostExecute(Long id) {
                super.onPostExecute(id);

                long newId = id;

                JobManager.getInstance(mCtx).getJob((int) newId, new AfterGetJob() {
                    @Override
                    public void afterGetJob(Job job) {
                        Job newjob = job;

                        for(Task subtask : subtasks){
                            subtask.setJobID(newjob.getId());
                            subtask.setHabitTask(false);
                            subtask.setJobTask(true);

                            TaskManager.getInstance(mCtx).createTask(subtask, new AfterCreateTask() {
                                @Override
                                public void afterCreateTask() {}
                            });
                        }

                        callback.afterCreateJob(newjob);
                    }
                });

            }
        }

        MyTask st = new MyTask();
        st.execute();
    }

    public void editJob(final Job job, final AfterEditJob callback) {

        class MyTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .jobDao().update(job);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterEditJob();
            }
        }

        MyTask st = new MyTask();
        st.execute();
    }

    public void deleteJob(final Job job, final AfterDeleteJob callback) {

        class MyTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                TaskManager.getInstance(mCtx).getTasksForJob(job, new AfterGetTasks() {
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
                        .jobDao().delete(job);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterDeleteJob();
            }
        }

        MyTask st = new MyTask();
        st.execute();
    }

    public void getJobs(final AfterGetJobs callback) {
        class MyTask extends AsyncTask<Void, Void, List<Job>> {
            @Override
            protected List<Job> doInBackground(Void... voids) {
                List<Job> jobs = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .jobDao()
                        .getAll();

                return jobs;
            }

            @Override
            protected void onPostExecute(List<Job> jobs) {
                super.onPostExecute(jobs);
                callback.afterGetJobs(jobs);
            }
        }

        MyTask gh = new MyTask();
        gh.execute();
    }

    public void getJob(final int jobID, final AfterGetJob callback) {
        class MyAsyncTask extends AsyncTask<Void, Void, Job> {
            @Override
            protected Job doInBackground(Void... voids) {
                Job job = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .jobDao()
                        .getJob(jobID);

                return job;
            }

            @Override
            protected void onPostExecute(Job job) {
                super.onPostExecute(job);
                callback.afterGetJob(job);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

}
