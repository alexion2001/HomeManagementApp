package com.example.laborator2_2023;

import android.os.AsyncTask;

public class InsertTaskOperation extends AsyncTask<Task, Void, String> {

    TaskOperations listener;

    public InsertTaskOperation(TaskOperations listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Task... tasks) {
        try {
            MyApplication.getmAppDatabase().taskDao().insertTask(tasks);
        } catch (Exception e){
            return "error";
        }
        return "succes";
    }

    @Override
    protected void onPostExecute(String result) {
        listener.insertTaskFinished(result);
    }
}
