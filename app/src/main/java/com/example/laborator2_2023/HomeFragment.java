package com.example.laborator2_2023;

import static android.content.Intent.getIntent;
import static com.example.laborator2_2023.AddTaskFragment.TASK;
import static com.example.laborator2_2023.LoginFragment.LOGIN;
import static com.example.laborator2_2023.LoginFragment.LOGIN_USERNAME;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

public class HomeFragment extends Fragment implements OnItemClickListener {

    private AppCompatEditText editText;
    private Button button;


    //result data
    private Button buttonLogin;
    private Button buttonRegister;
    private TextView textView;
    private int id = 0;
    private static String NOTIFICATION_CHANEL_ID = "my notfication chanel";

    public static String HOUSE_NAME = "house name";
    public static String HOUSE = "house";

    public final static String PREFERENCES_KEY = "preferences key";

    public final static String PREFERENCES_NAME_KEY = "preferences key name";


    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;

        // Check the orientation of the device
        int orientation = getResources().getConfiguration().orientation;

        // If the device is in landscape mode, load the landscape layout
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rootView = inflater.inflate(R.layout.fragment_home_landscape, container, false);
        }
        else{
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
        }


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        //permisiuni aplicatie
//
//        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
//        }


        createNotificationChanel();
        sendNotificationWhenMonthChange();


        buttonLogin = view.findViewById(R.id.button_login);
        if(buttonLogin != null){
            // Button found
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToLogin();
                }
            });
        } else {
            // Button not found
            Log.e("TasksFragment", "Button  not found!");
        }

        buttonRegister = view.findViewById(R.id.button_register);
        if(buttonRegister != null){
            // Button found
            buttonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToRegister();
                }
            });
        } else {
            // Button not found
            Log.e("TasksFragment", "Button not found!");
        }

        Context context = getContext().getApplicationContext();//= getActivity();
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        String user = preferences.getString(PREFERENCES_NAME_KEY,"user");
        textView = view.findViewById(R.id.hello);
        String hello_msg =getString(R.string.hello) + " " + user;
        textView.setText(hello_msg);


//        //get data from login
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//
//
//
//            LoginModel model = bundle.getParcelable(LOGIN);
//            Log.e("ERRORSYAAD", model.getUsername());
//            textView = view.findViewById(R.id.hello);
//            String hello_msg =getString(R.string.hello) + " " + model.getPassword();
//            textView.setText(hello_msg);
//
//
//        }

    }
    @Override
    public void onItemClick(TaskModel item) {

    }

    public void goToLogin(){

        LoginFragment loginFragment = new LoginFragment();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, loginFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void goToRegister(){

        RegisterFragment registerFragment = new RegisterFragment();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, registerFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addNotification(String text) {
        // Create a PendingIntent for the notification - deschidere la o pagina specifica
        int requestCode = 123;
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("FRAGMENT_TO_OPEN", "FacturiFragment");
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
            addNotification("Nu uita sa iti adaugi facturile pentru aceasta luna.");
        }
    }

    public void sendNotificationWhenMonthChange() {
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfMonth == 1) {
            addNotification("Nu uita sa iti adaugi facturile pentru aceasta luna.");
        }
    }


}

