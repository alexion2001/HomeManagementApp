package com.example.laborator2_2023;

import static com.example.laborator2_2023.AddTaskFragment.TASK;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment implements OnItemClickListener, TaskOperations {

    public static List<TaskModel> taskModelList = new ArrayList<>();

    public final static String PREFERENCES_KEY = "preferences key";

    public final static String PREFERENCES_ID_KEY = "preferences key id";


    private Button goAddTask;

    public TasksFragment() { super(R.layout.fragment_tasks);}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        searchtasks();

        CustomAdapter adapter = new CustomAdapter(taskModelList, this);
        RecyclerView rv = view.findViewById(R.id.recycler_view);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        //initializarea de task-uri (le iau din baza de date);
//        searchtasks();


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



//        CustomAdapter adapter = new CustomAdapter(taskModelList, this);
//        RecyclerView rv = view.findViewById(R.id.recycler_view);
//        rv.setAdapter(adapter);

        //preiau datele din add task
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            TaskModel model = bundle.getParcelable(TASK);

            taskModelList.add(0,model);

        }
    }

    private void initializeList() { //iau datele din baza de date -TODO

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

    @Override
    public void insertTaskFinished(String result) {

    }

    private void  searchtasks(){
        new FindTaskOperation(this).execute();
    }
    @Override
    public void findTasksFinished(List<Task> tasks) {
        if (tasks!= null){
            taskModelList.clear();
            for (Task task : tasks) {
                taskModelList.add(new TaskModel(
                        task.task,
                        task.deadline,
                        task.username,
                        task.status
                ));
            }

            int length = taskModelList.size();
            Log.d("TasksFragment2", String.valueOf(length));
            makePreferences(String.valueOf(length));

            CustomAdapter adapter = new CustomAdapter(taskModelList, this);
            RecyclerView rv = getView().findViewById(R.id.recycler_view);
            rv.setAdapter(adapter);

        }
        else{
            Toast.makeText(getContext(),"Nu exista task-uri",Toast.LENGTH_LONG).show();
        }
    }

    private void makePreferences(String id) {
        Context context = getContext().getApplicationContext();

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCES_ID_KEY, id);
        editor.apply();

    }
}


