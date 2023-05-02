package com.example.laborator2_2023;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Task.class}, version = 2) //update la modificari in db
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract TaskDao taskDao();
}
