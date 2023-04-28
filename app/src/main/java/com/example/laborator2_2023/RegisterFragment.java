package com.example.laborator2_2023;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegisterFragment extends Fragment {

    private AppCompatEditText enterUsername;
    private AppCompatEditText enterPassword;
    private AppCompatEditText confirmPassword;
    private Button buttonLog;

    private String enterUsernameResult;
    private String enterPasswordResult;

    public static String LOGIN_USERNAME = "login username";
    public static String LOGIN_PASSWORD = "login password";
    public static String LOGIN = "login";


    //result data
    private String editTextResult;
    private String buttonResult;


    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enterUsername = view.findViewById(R.id.createUsername);
        enterPassword = view.findViewById(R.id.createPassword);
        confirmPassword = view.findViewById(R.id.createHouse);
        buttonLog = view.findViewById(R.id.buttonReg);

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

                String strPass2="";
                Boolean match = true;
                if(confirmPassword.getText() != null){
                    strPass = confirmPassword.getText().toString();

                    if(strPass.isEmpty()){
                        fieldEmpty = true;
                    }
                    else{
                        if(strPass2 != strPass){
                            match = false;

                        }
                    }
                }




                if(fieldEmpty){
                    showErrorPopup("Camp necompletat");
                } else if (match == false) {
                    showErrorPopup("Parolele nu se potrivesc");
                } else {
                    //create new task and send it to tfe tasks page

                    LoginModel item = new LoginModel(
                            strTxt,
                            strPass
                    );
                    goLogin(item);
                }

            }
        });
    }

    private void showErrorPopup(String mesaj) {
        new AlertDialog.Builder(getContext())
                .setTitle("Error")
                .setMessage(mesaj)
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

    public void goLogin(LoginModel item){


        Bundle bundle = new Bundle();

        bundle.putString(LOGIN_PASSWORD,item.getPassword());
        bundle.putString(LOGIN_USERNAME, item.getUsername());
        bundle.putParcelable(LOGIN, item);

        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, loginFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();


    }

}
