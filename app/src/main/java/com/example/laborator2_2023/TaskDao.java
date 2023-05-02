package com.example.laborator2_2023;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task... tasks);
    @Update
    void upateTask(Task... tasks);
    @Delete
    void deleteTask(Task... tasks);

    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

}
