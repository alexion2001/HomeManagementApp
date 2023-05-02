package com.example.laborator2_2023;

import android.os.AsyncTask;

import java.util.List;

public class FindTaskOperation extends AsyncTask<String, Void, List<Task>> {

    TaskOperations listener;

    public FindTaskOperation(TaskOperations listener) {
        this.listener = listener;
    }

    @Override
    protected List<Task> doInBackground(String... strings) {
//        String task = strings[0];
//        String deadline = strings[1];
//        String user = strings[2];
        return MyApplication.getmAppDatabase().taskDao().getAllTasks();
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        listener.findTasksFinished(tasks);
    }
}
