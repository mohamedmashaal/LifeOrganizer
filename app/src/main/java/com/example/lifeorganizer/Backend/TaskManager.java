package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lifeorganizer.Data.DatabaseClient;
import com.example.lifeorganizer.Data.Task;

import java.util.List;

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

        class CreateTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                // adding to database
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .taskDao().insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterCreateTask();
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
}
