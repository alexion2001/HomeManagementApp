package com.example.laborator2_2023;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {


    @PrimaryKey
    @NonNull
    public String username;

    @NonNull
    public String password;

    public User(String username,@NonNull String password) {

        this.username = username;
        this.password = password;
    }


    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
