package com.example.laborator2_2023;

import static com.example.laborator2_2023.AddTaskFragment.TASK;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    private CheckBox checkBox;

    private Button goAddTask;

    public RecyclerView rv;

    public TasksFragment() { super(R.layout.fragment_tasks);}



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        searchtasks();

        CustomAdapter adapter = new CustomAdapter(taskModelList, this);
        rv = view.findViewById(R.id.recycler_view);
        rv.setAdapter(adapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //remove done tasks


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



        //preiau datele din add task
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            TaskModel model = bundle.getParcelable(TASK);

            taskModelList.add(0,model);

        }
    }

    private void initializeList() { //iau datele din baza de date -TODO

        taskModelList.add(new TaskModel(
                1,
                "Spala vasele",
                "21 mai 2023",
                "Alexandra",
                true

        ));
        taskModelList.add(new TaskModel(
                2,
                "Arunca gunoiul",
                "22 mai 2023",
                "Alexandra",
                false

        ));

    }
    @Override
    public void onItemClick(TaskModel item) {

        for (TaskModel task : taskModelList) {
            if(task.getId()==item.getId()){
                task.setStatus(true);
            }

        }
        CustomAdapter adapter = new CustomAdapter(taskModelList, this);
        rv.setAdapter(adapter);

        deleteTask(item.getId(), item.getTaskText(), item.getDeadline(),
                item.getUsername(), item.getStatus());

    }

    public void goToAddTaskFragment(){

        AddTaskFragment addTaskFragment = new AddTaskFragment();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction
                .setCustomAnimations(R.anim.animation_right_left,R.anim.exit_animation_right_left
                        ,R.anim.animation_left_right,R.anim.exit_animation_left_right)
                .replace(R.id.fragment_container, addTaskFragment)
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

            taskModelList.clear(); //clear the list
            int lastId = 0;
            for (Task task : tasks) { //get the raimain items from database
                taskModelList.add(new TaskModel(
                        task.id,
                        task.task,
                        task.deadline,
                        task.username,
                        task.status
                ));
                lastId = task.id;
            }

            int length = taskModelList.size();


            makePreferences(String.valueOf(lastId));

            CustomAdapter adapter = new CustomAdapter(taskModelList, this);
            RecyclerView rv = getView().findViewById(R.id.recycler_view);
            rv.setAdapter(adapter);

        }
        else{
            Toast.makeText(getContext(),"Nu exista task-uri",Toast.LENGTH_LONG).show();
        }
    }

    private void deleteTask(int id,String task,String deadline, String user, Boolean status){

        Task task1 = new Task(id,task,deadline,user,status);
        Task[] tasks = new Task[]{task1};

        new DeleteTaskOperation(this).execute(tasks);
    }

    @Override
    public void deleteTasksFinished(String result) {
        if (result.equals("succes")){
            Toast.makeText(getContext(),"Task finalizat",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(),"Fail",Toast.LENGTH_LONG).show();
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


