package com.example.laborator2_2023;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey
    public int id;

    @NonNull
    public String task; //task-ul
    @NonNull
    public String deadline; //data limita
    @NonNull
    public String username; //cine face task-ul
    public Boolean status; //statusul

    public Task(int id, @NonNull String task, @NonNull String deadline, @NonNull String username) {
        this.id = id;
        this.task = task;
        this.deadline = deadline;
        this.username = username;
        this.status = false;
    }
}
