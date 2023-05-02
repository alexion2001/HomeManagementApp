package com.example.laborator2_2023;

import android.os.AsyncTask;

public class FindUserOperation extends AsyncTask<String, Void, User> {

    UserOperations listener;

    public FindUserOperation(UserOperations listener) {
        this.listener = listener;
    }

    @Override
    protected User doInBackground(String... strings) {
       String username = strings[0];
       String pass = strings[1];
       return MyApplication.getmAppDatabase().userDao().getUserByNameAndPass(username,pass);
    }

    @Override
    protected void onPostExecute(User user) {
       listener.findUserFinished(user);
    }
}
