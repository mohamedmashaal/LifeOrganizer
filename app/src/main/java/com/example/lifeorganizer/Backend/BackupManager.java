package com.example.lifeorganizer.Backend;

import android.content.Context;
import android.os.Environment;

import com.example.lifeorganizer.Data.AppDatabase;
import com.example.lifeorganizer.Data.DatabaseClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

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
        AppDatabase appDatabase = DatabaseClient.getInstance(mCtx).getAppDatabase();

        if (externalStorageDirectory.canWrite()) {
            String currentDBPath = appDatabase.getOpenHelper().getWritableDatabase().getPath();
            String backupDBPath = "life_orgranizer_dp.sqlite"; //you can modify the file type you need to export
            File currentDB = new File(currentDBPath);
            File backupDB = new File(externalStorageDirectory, backupDBPath);
            if (currentDB.exists()) {
                try {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importDatabase() {

    }
}
