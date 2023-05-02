package com.example.laborator2_2023;

import android.os.AsyncTask;

public class InsertUserOperation extends AsyncTask<User, Void, String> {
    UserOperations listener;

    public InsertUserOperation(UserOperations listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(User... users) {
        try {
            MyApplication.getmAppDatabase().userDao().insertUser(users);
        } catch (Exception e){
            return "error";
            }
        return "succes";
    }

    @Override
    protected void onPostExecute(String result) {
        listener.insertUsersFinished(result);
    }
}
