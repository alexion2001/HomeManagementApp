package com.example.laborator2_2023;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class LoginModel implements Parcelable {

    private String username; //task-ul
    private String password; //data limita


    public LoginModel( String deadline, String username) {

        this.username = username;
        this.password = deadline;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeStringArray(new String[] {  this.username,this.password});
    }

    public LoginModel(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);

        this.username = data[0];
        this.password = data[1];

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
