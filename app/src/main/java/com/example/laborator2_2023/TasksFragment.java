package com.example.laborator2_2023;

import static com.example.laborator2_2023.AddTaskFragment.TASK;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment implements OnItemClickListener {

    public static List<TaskModel> taskModelList = new ArrayList<>();


    private Button goAddTask;

    public TasksFragment() { super(R.layout.fragment_tasks);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goAddTask = view.findViewById(R.id.go_add_task);
        if(goAddTask != null){
            // Button found
            goAddTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToAddTaskFragment();
                }
            });
        } else {
            // Button not found
            Log.e("TasksFragment", "Button go_add_task not found!");
        }


        initializeList();

        CustomAdapter adapter = new CustomAdapter(taskModelList, this);
        RecyclerView rv = view.findViewById(R.id.recycler_view);
        rv.setAdapter(adapter);

        //preiau datele din add task
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            TaskModel model = bundle.getParcelable(TASK);

            taskModelList.add(0,model);

        }
    }

    private void initializeList() { //iau datele din baza de date --TODO

        taskModelList.add(new TaskModel(
                "Spala vasele",
                "21 mai 2023",
                "Alexandra",
                true

        ));
        taskModelList.add(new TaskModel(
                "Arunca gunoiul",
                "22 mai 2023",
                "Alexandra",
                false

        ));

    }
    @Override
    public void onItemClick(TaskModel item) {

    }

    public void goToAddTaskFragment(){

        AddTaskFragment addTaskFragment = new AddTaskFragment();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, addTaskFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }
}
