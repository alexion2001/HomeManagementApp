package com.example.laborator2_2023;

import android.os.AsyncTask;

import java.util.List;

public class DeleteTaskOperation extends AsyncTask<Task, Void, String>  {

    TaskOperations listener;

    public DeleteTaskOperation(TaskOperations listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Task... tasks) {
        try {
            MyApplication.getmAppDatabase().taskDao().deleteTask(tasks);
        } catch (Exception e){
            return "error";
        }
        return "succes";
    }
    @Override
    protected void onPostExecute(String result) {
        listener.deleteTasksFinished(result);
    }


}
