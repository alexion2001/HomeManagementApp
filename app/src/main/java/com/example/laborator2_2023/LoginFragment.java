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

public class LoginFragment extends Fragment implements UserOperations {

    private AppCompatEditText enterUsername;
    private AppCompatEditText enterPassword;
    private Button buttonLog;

    private String enterUsernameResult;
    private String enterPasswordResult;

    public static String LOGIN_USERNAME = "login username";
    public static String LOGIN_PASSWORD = "login password";
    public static String LOGIN = "login";


    //result data
    private String editTextResult;
    private String buttonResult;

    public final static String PREFERENCES_KEY = "preferences key";

    public final static String PREFERENCES_NAME_KEY = "preferences key name";



    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enterUsername = view.findViewById(R.id.enterUsername);
        enterPassword = view.findViewById(R.id.enterPassword);
        buttonLog = view.findViewById(R.id.buttonLog);

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean fieldEmpty = false;

                String strTxt="";
                if(enterUsername.getText() != null){
                    strTxt = enterUsername.getText().toString();

                    if(strTxt.isEmpty()){
                        fieldEmpty = true;
                    }
                    else{
                        enterUsernameResult = strTxt;
                    }
                }

                String strPass="";
                if(enterPassword.getText() != null){
                    strPass = enterPassword.getText().toString();

                    if(strPass.isEmpty()){
                        fieldEmpty = true;
                    }
                    else{
                        enterPasswordResult = strPass;
                    }
                }


                if(fieldEmpty){
                    showErrorPopup();
                }
                else {
                  //caut daca exista user-ul
                    searchUser(strTxt, strPass);

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

    public void goHome(LoginModel item){


        Bundle bundle = new Bundle();

        bundle.putString(LOGIN_PASSWORD,item.getPassword());
        bundle.putString(LOGIN_USERNAME, item.getUsername());
        bundle.putParcelable(LOGIN, item);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, homeFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();


    }

    private void makePreferences(String username) {
        Context context = getContext().getApplicationContext();

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCES_NAME_KEY, username);
        editor.apply();

    }

    private void  searchUser(String username,String pass){
        new FindUserOperation(this).execute(username,pass);
    }

    @Override
    public void insertUsersFinished(String result) {

    }

    @Override
    public void findUserFinished(User user) {
        if (user!= null){
            Toast.makeText(getContext(),user.username,Toast.LENGTH_LONG).show();
            makePreferences(user.username);
            LoginModel item = new LoginModel(
                    user.username,
                    user.password
            );
            goHome(item);
        }
        else{
            Toast.makeText(getContext(),"User inexistent",Toast.LENGTH_LONG).show();
        }
    }
}
