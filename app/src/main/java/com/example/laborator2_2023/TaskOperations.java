package com.example.laborator2_2023;

import java.util.List;

public interface TaskOperations {

    void insertTaskFinished(String result);
    void findTasksFinished(List<Task> tasks);
    void deleteTasksFinished(String result);
}
