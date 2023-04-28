package com.example.laborator2_2023;

import static com.example.laborator2_2023.LoginFragment.LOGIN;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FacturiFragment extends Fragment {

    private AppCompatEditText editText;
    private Button button2;



    //result data
    private EditText curentCost;
    private EditText gazeCost;
    private EditText intretinereCost;
    private EditText cabluCost;
    private TextView totalCost;
    private TextView textView; //luna





    public static String HOUSE_NAME = "house name";
    public static String HOUSE = "house";

    public FacturiFragment() {
        super(R.layout.fragment_facturi);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Get the current month
        Calendar calendar = Calendar.getInstance();
        String monthName = new SimpleDateFormat("MMMM", new Locale("ro")).format(calendar.getTime());

        textView = view.findViewById(R.id.month);
        String month_msg = getString(R.string.monthName) + " " + monthName;
        textView.setText(month_msg);

        //costul total
        curentCost = view.findViewById(R.id.curentCost);
        gazeCost = view.findViewById(R.id.gazeCost);
        intretinereCost = view.findViewById(R.id.intretinereCost);
        cabluCost = view.findViewById(R.id.cabluCost);
        totalCost = view.findViewById(R.id.totalCost);
        button2 = view.findViewById(R.id.button2);



        if (button2 != null) {
            // Button found
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //apas butonul si fac suma input-urilor
                    double total = Double.parseDouble(curentCost.getText().toString()) +
                            Double.parseDouble(gazeCost.getText().toString()) +
                            Double.parseDouble(intretinereCost.getText().toString()) +
                            Double.parseDouble(cabluCost.getText().toString());
                    //o adaug la suma totala

                    totalCost.setText(String.format(Locale.getDefault(), "%.2f lei", total));

                    //salvez noile valori in baza de date si le incarc in pagina -TODO
                }
            });
        }


    }


}


