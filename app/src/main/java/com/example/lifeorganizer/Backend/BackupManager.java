package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.os.Environment;

import com.example.lifeorganizer.Data.AppDatabase;
import com.example.lifeorganizer.Data.DatabaseClient;

import java.io.File;

public class BackupManager {
    private Context mCtx;
    private static BackupManager mInstance;

    private BackupManager(Context mCtx) {
        this.mCtx = mCtx;
    }
    private BackupManager() { }

    public static synchronized BackupManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new BackupManager(mCtx);
        }
        return mInstance;
    }

    public static synchronized BackupManager getInstance() {
        if (mInstance == null) {
            mInstance = new BackupManager();
        }
        return mInstance;
    }

    public void exportDatabase(){
        File externalStorageDirectory = Environment.getExternalStorageDirectory();

        // Get the Room database storage path using SupportSQLiteOpenHelper
        DatabaseClient.getInstance(mCtx).getAppDatabase().getOpenHelper().getWritableDatabase().getPath();
/*
        if (externalStorageDirectory.canWrite()) {
            val currentDBPath = AppDatabase.getDatabase(applicationContext)!!.openHelper.writableDatabase.path
            val backupDBPath = "mydb.sqlite"      //you can modify the file type you need to export
            val currentDB = File(currentDBPath)
            val backupDB = File(sd, backupDBPath)
            if (currentDB.exists()) {
                try {
                    val src = FileInputStream(currentDB).channel
                    val dst = FileOutputStream(backupDB).channel
                    dst.transferFrom(src, 0, src.size())
                    src.close()
                    dst.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }*/
    }
}
