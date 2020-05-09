package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.os.AsyncTask;

import com.example.lifeorganizer.Data.DatabaseClient;
import com.example.lifeorganizer.Data.DiaryNote;
import com.example.lifeorganizer.Data.Habit;
import com.example.lifeorganizer.Data.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaryManager {
    private Context mCtx;
    private static DiaryManager mInstance;

    private DiaryManager(Context mCtx) {
        this.mCtx = mCtx;
    }
    private DiaryManager() { }

    public static synchronized DiaryManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DiaryManager(mCtx);
        }
        return mInstance;
    }

    public static synchronized DiaryManager getInstance() {
        if (mInstance == null) {
            mInstance = new DiaryManager();
        }
        return mInstance;
    }

    public void createDiaryNote(final DiaryNote diaryNote, final AfterCreateDiaryNote callback) {

        class MyTask extends AsyncTask<Void, Void, Long> {
            @Override
            protected Long doInBackground(Void... voids) {
                // adding to database
                long id = DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .diaryNoteDao().insert(diaryNote);
                return id;
            }

            @Override
            protected void onPostExecute(Long id) {
                super.onPostExecute(id);
                diaryNote.setId(id.intValue());
                callback.afterCreateDiaryNote(diaryNote);
            }
        }

        MyTask st = new MyTask();
        st.execute();
    }

    public void editDiaryNote(final DiaryNote diaryNote, final AfterEditDiaryNote callback) {

        class MyTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .diaryNoteDao().update(diaryNote);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterEditDiaryNote();
            }
        }

        MyTask st = new MyTask();
        st.execute();
    }

    public void deleteDiaryNote(final DiaryNote diaryNote, final AfterDeleteDiaryNote callback) {

        class MyTask  extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(mCtx).getAppDatabase()
                        .diaryNoteDao().delete(diaryNote);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.afterDeleteDiaryNote();
            }
        }

        MyTask st = new MyTask();
        st.execute();
    }

    public void getDiaryNotes(final AfterGetDiaryNotes callback) {
        class MyTask extends AsyncTask<Void, Void, List<DiaryNote>> {
            @Override
            protected List<DiaryNote> doInBackground(Void... voids) {
                List<DiaryNote> diaryNotes = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .diaryNoteDao()
                        .getAll();

                return diaryNotes;
            }

            @Override
            protected void onPostExecute(List<DiaryNote> diaryNotes) {
                super.onPostExecute(diaryNotes);
                callback.afterGetDiaryNotes(diaryNotes);
            }
        }

        MyTask gh = new MyTask();
        gh.execute();
    }

    public void getDiaryNote(final int noteID, final AfterGetDiaryNote callback) {
        class MyAsyncTask extends AsyncTask<Void, Void, DiaryNote> {
            @Override
            protected DiaryNote doInBackground(Void... voids) {
                DiaryNote diaryNote = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .diaryNoteDao()
                        .getDiaryNote(noteID);

                return diaryNote;
            }

            @Override
            protected void onPostExecute(DiaryNote diaryNote) {
                super.onPostExecute(diaryNote);
                callback.afterGetDiaryNote(diaryNote);
            }
        }

        MyAsyncTask gh = new MyAsyncTask();
        gh.execute();
    }

    public void getDiaryNotes(final Date date, final AfterGetDiaryNotes callback) {
        class MyTask extends AsyncTask<Void, Void, List<DiaryNote>> {
            @Override
            protected List<DiaryNote> doInBackground(Void... voids) {
                List<DiaryNote> diaryNotes = DatabaseClient.getInstance(mCtx)
                        .getAppDatabase()
                        .diaryNoteDao()
                        .getAll();
                return diaryNotes;
            }

            @Override
            protected void onPostExecute(List<DiaryNote> diaryNotes) {
                super.onPostExecute(diaryNotes);

                List<DiaryNote> dateDiaryNotes = new ArrayList<>();
                for(DiaryNote diaryNote : diaryNotes){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    if(sdf.format(diaryNote.getCreatedAtDate()).equals(sdf.format(date)))
                        dateDiaryNotes.add(diaryNote);

                }

                callback.afterGetDiaryNotes(dateDiaryNotes);
            }
        }

        MyTask gh = new MyTask();
        gh.execute();
    }
}
