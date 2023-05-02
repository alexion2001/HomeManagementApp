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
    void insertTask(Task task);
    @Update
    void upateTask(Task task);
    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

}
