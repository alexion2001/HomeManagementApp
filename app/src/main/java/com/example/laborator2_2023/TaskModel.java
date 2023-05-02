package com.example.laborator2_2023;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TaskModel implements Parcelable {

    private int id;
    private String taskText; //task-ul
    private String deadline; //data limita
    private String username; //cine face task-ul
    private Boolean status; //statusul

    public TaskModel(int id, String taskText, String deadline, String username, Boolean status) {
        this.id = id;
        this.taskText = taskText;
        this.deadline = deadline;
        this.username = username;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }


    public String getTaskText() {
        return taskText;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getUsername() {
        return username;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeStringArray(new String[] {this.taskText, this.deadline, this.username, this.status.toString()});
    }

    public TaskModel(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);

        this.taskText = data[0];
        this.deadline = data[1];
        this.username = data[2];
        this.status = Boolean.valueOf(data[3]);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new TaskModel[size];
        }
    };
}
