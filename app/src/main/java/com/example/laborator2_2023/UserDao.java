package com.example.laborator2_2023;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User... users);
    @Update
    void upateUser(User... users);
    @Delete
    void deleteUser(User... users);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE username LIKE :name AND password LIKE :pass  LIMIT 1")
    User getUserByNameAndPass(String name, String pass);

}
