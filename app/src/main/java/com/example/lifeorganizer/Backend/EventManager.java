package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lifeorganizer.Data.DatabaseClient;
import com.example.lifeorganizer.Data.Event;
import com.example.lifeorganizer.Data.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventManager {
    private Context mCtx;
    private static EventManager mInstance;

    private EventManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized EventManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new EventManager(mCtx);
        }
        return mInstance;
    }

    public void createEvent(final Event event, final AfterCreateEvent callback) {

        class MyTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                // adding to database
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .eventDao().insert(event);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterCreateEvent();
            }
        }

        MyTask st = new MyTask();
        st.execute();
    }

    public void editEvent(final Event event, final AfterEditEvent callback) {

        class MyTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .eventDao().update(event);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterEditEvent();
            }
        }

        MyTask st = new MyTask();
        st.execute();
    }

    public void deleteEvent(final Event event, final AfterDeleteEvent callback) {

        class MyTask  extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .eventDao().delete(event);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterDeleteEvent();
            }
        }

        MyTask st = new MyTask();
        st.execute();
    }

    public void getEvents(final AfterGetEvents callback) {

        class MyTask extends AsyncTask<Void, Void, List<Event>> {
            @Override
            protected List<Event> doInBackground(Void... voids) {
                List<Event> events = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .eventDao()
                        .getAll();
                return events;
            }

            @Override
            protected void onPostExecute(List<Event> events) {
                super.onPostExecute(events);
                callback.afterGetEvents(events);
            }
        }

        MyTask gh = new MyTask();
        gh.execute();
    }

    public void getEvents(final Date date, final AfterGetEvents callback) {
        class MyTask extends AsyncTask<Void, Void, List<Event>> {
            @Override
            protected List<Event> doInBackground(Void... voids) {
                List<Event> events = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .eventDao()
                        .getAll();
                return events;
            }

            @Override
            protected void onPostExecute(List<Event> events) {
                super.onPostExecute(events);

                List<Event> dateEvents = new ArrayList<>();
                for(Event event : events){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(sdf.format(event.getStartDate()).equals(sdf.format(date)))
                        dateEvents.add(event);

                }

                callback.afterGetEvents(dateEvents);
                //callback.afterGetTasks(tasks);
            }
        }

        MyTask gh = new MyTask();
        gh.execute();
    }

    public void getEvent(final int id, final AfterGetEvent callback) {

        class MyTask extends AsyncTask<Void, Void, Event> {
            @Override
            protected Event doInBackground(Void... voids) {
                Event event = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .eventDao()
                        .get(id);
                return event;
            }

            @Override
            protected void onPostExecute(Event event) {
                super.onPostExecute(event);
                callback.afterGetEvent(event);
            }
        }

        MyTask gh = new MyTask();
        gh.execute();
    }


}
