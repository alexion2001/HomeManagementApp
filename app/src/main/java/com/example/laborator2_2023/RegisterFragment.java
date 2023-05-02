package com.example.laborator2_2023;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

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
    private int id = 100;
    private static String NOTIFICATION_CHANEL_ID = "my notfication chanel";


    //result data
    private String editTextResult;
    private String buttonResult;


    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //permisiuni aplicatie

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        createNotificationChanel();
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
                boolean match = true;
                if(confirmPassword.getText() != null){
                    strPass2 = confirmPassword.getText().toString();

                    if(strPass2.isEmpty()){
                        fieldEmpty = true;
                    }
                    else{

                        if(!strPass2.equals(strPass)){
                            match = false;

                        }
                    }
                }




                if(fieldEmpty){
                    showErrorPopup("Camp necompletat");
                } else if (!match) {
                    showErrorPopup("Parolele nu se potrivesc");
                } else {
                    //create new task and send it to tfe tasks page

                    LoginModel item = new LoginModel(
                            strTxt,
                            strPass
                    );

                    addNotification("Nu uita sa te loghezi in aplicatie!");
                    //goLogin(item);
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


    private void addNotification(String text) {
        // Create a PendingIntent for the notification - deschidere la o pagina specifica
        int requestCode = 123;
        Intent intent = new Intent(getContext(), MainActivity.class);

        // Modify the intent to open the LoginFragment fragment
        intent.setClass(getContext(), MainActivity.class);
        intent.putExtra("FRAGMENT_TO_OPEN", "LoginFragment");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

        // Create the notification

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getContext(),NOTIFICATION_CHANEL_ID)
                        .setSmallIcon(R.drawable.ic_facturi)
                        .setContentTitle("Home Managament")
                        .setContentText(text)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        //send notiication
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            id++;

            notificationManagerCompat.notify(id, builder.build());
            Log.d("SUMAMEA", "sendIn"+id);
            return;
        }
        id++;

        notificationManagerCompat.notify(id, builder.build());


    }

    private void createNotificationChanel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Log.d("SUMAMEA", "channel");
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANEL_ID, "chanel name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("chanel description");

            NotificationManagerCompat notificationManagerCompat =
                    NotificationManagerCompat.from(getActivity().getApplicationContext());
            notificationManagerCompat.createNotificationChannel(channel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, send the notification
            addNotification("Nu uita sa te loghezi in aplicatie");
        }
    }



}
