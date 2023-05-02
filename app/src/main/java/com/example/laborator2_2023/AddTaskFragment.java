package com.example.laborator2_2023;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class AddTaskFragment extends Fragment implements TaskOperations {

    private AppCompatEditText editText; //task-ul
    private AppCompatEditText editDeadline; //data limita
    private AppCompatEditText editUser; //cine face task-ul
    private Button button; //salvare si redirectionare catre task-uri


    //result data
    private String editTextResult; //task-ul
    private String editDeadlineResult; //data limita
    private String editUserResult; //cine face task-ul
    private String buttonResult; //salvare si redirectionare catre task-uri

    public static String TASK_MSG = "task msg";
    public static String TASK_DEADLINE = "deadline";
    public static String TASK_MEMBER = "member";
    public static String TASK = "movie";

    public final static String PREFERENCES_KEY = "preferences key";

    public final static String PREFERENCES_ID_KEY = "preferences key id";


    public AddTaskFragment() {
        super(R.layout.fragment_add_task);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.editText);
        editDeadline = view.findViewById(R.id.editDeadline);
        editUser = view.findViewById(R.id.editUser);
        button = view.findViewById(R.id.button);

        //salvez in baza si redirect catre pagina de task-uri
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean fieldEmpty = false;

                String strTxt="";
                if(editText.getText() != null){
                    strTxt = editText.getText().toString();

                    if(strTxt.isEmpty()){
                        fieldEmpty = true;
                    }
                    else{
                        editTextResult = strTxt;
                    }
                }

                String strData="";
                if(editDeadline.getText() != null){
                    strData = editDeadline.getText().toString();

                    if(strData.isEmpty()){
                        fieldEmpty = true;
                    }
                    else{
                        editDeadlineResult = strData;
                    }
                }

                String strMember="";
                if(editUser.getText() != null){
                    strMember = editUser.getText().toString();

                    if(strMember.isEmpty()){
                        fieldEmpty = true;
                    }
                    else{
                        editUserResult = strMember;
                    }
                }

                if(fieldEmpty){
                    showErrorPopup();
                }
                else {
                    //create new task and send it to tfe tasks page
                    TaskModel item = new TaskModel(
                            0,
                            strTxt,
                            strData,
                            strMember,
                            false
                    );
                    insertUsers(strTxt,strData,strMember);
                    goToAddTasksFragment(item);
                }

            }
        });
    }

    private void showErrorPopup() {
        new AlertDialog.Builder(getContext())
                .setTitle("Error")
                .setMessage(R.string.error_msg)
                .setPositiveButton(android.R.string.yes, dismissPopup())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private DialogInterface.OnClickListener dismissPopup() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        };
    }

    public void goToAddTasksFragment(TaskModel item){

        Bundle bundle = new Bundle();

        bundle.putString(TASK_MSG, item.getTaskText());
        bundle.putString(TASK_DEADLINE, item.getDeadline());
        bundle.putString(TASK_MEMBER, item.getUsername());
        bundle.putParcelable(TASK, item);

        TasksFragment tasksFragment = new TasksFragment();
        tasksFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, tasksFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();


    }

    private void insertUsers(String task,String deadline, String user){

        Context context = getContext().getApplicationContext();//= getActivity();
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        String id_string = preferences.getString(PREFERENCES_ID_KEY,"1");
        int id = Integer.parseInt(id_string) + 1;

        Task task1 = new Task(id,task,deadline,user,false);
        Task[] tasks = new Task[]{task1};

        new InsertTaskOperation(this).execute(tasks);
    }

    @Override
    public void insertTaskFinished(String result) {
        if (result.equals("succes")){
            Toast.makeText(getContext(),"Task-ul a fost adaugat",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(),"Fail",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void findTasksFinished(List<Task> tasks) {

    }

    @Override
    public void deleteTasksFinished(String result) {

    }
}
