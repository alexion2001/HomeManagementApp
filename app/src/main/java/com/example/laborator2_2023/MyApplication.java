package com.example.laborator2_2023;

import android.app.Application;

import androidx.room.Room;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static AppDatabase mAppDatabase;
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
       mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "home-management-database").build();
    }

    public static MyApplication getmInstance() {
        return mInstance;
    }

    public static AppDatabase getmAppDatabase() {
        return mAppDatabase;
    }
}
