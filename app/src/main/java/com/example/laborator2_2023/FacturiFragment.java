package com.example.laborator2_2023;

import static com.example.laborator2_2023.LoginFragment.LOGIN;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public final static String PREFERENCES_KEY = "preferences key";

    public final static String PREFERENCES_CURENT_KEY = "preferences key curent";
    public final static String PREFERENCES_GAZE_KEY = "preferences key gaze";
    public final static String PREFERENCES_INTRETINERE_KEY = "preferences key intretinere";
    public final static String PREFERENCES_CABLU_KEY = "preferences key cablu";



    public FacturiFragment() {
      //  super(R.layout.fragment_facturi);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;

        // Check the orientation of the device
        int orientation = getResources().getConfiguration().orientation;

        // If the device is in landscape mode, load the landscape layout
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rootView = inflater.inflate(R.layout.fragment_facturi_landscape, container, false);
        }
        else{
            rootView = inflater.inflate(R.layout.fragment_facturi, container, false);
        }


        return rootView;
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

        //date facturi
        curentCost = view.findViewById(R.id.curentCost);
        gazeCost = view.findViewById(R.id.gazeCost);
        intretinereCost = view.findViewById(R.id.intretinereCost);
        cabluCost = view.findViewById(R.id.cabluCost);
        totalCost = view.findViewById(R.id.totalCost);
        button2 = view.findViewById(R.id.button2);

        //iau datele din shared pref
        Context context = getContext().getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        String gaze = preferences.getString(PREFERENCES_GAZE_KEY,"0.0");
        String curent = preferences.getString(PREFERENCES_CURENT_KEY,"0.0");
        String cablu = preferences.getString(PREFERENCES_CABLU_KEY,"0.0");
        String intretinere = preferences.getString(PREFERENCES_INTRETINERE_KEY,"0.0");


    // setez datele in aplicatie
        gazeCost.setText(gaze);
        curentCost.setText(curent);
        cabluCost.setText(cablu);
        intretinereCost.setText( intretinere);

        //calculez costul
        double total = Double.parseDouble(curentCost.getText().toString()) +
                Double.parseDouble(gazeCost.getText().toString()) +
                Double.parseDouble(intretinereCost.getText().toString()) +
                Double.parseDouble(cabluCost.getText().toString());
        //o adaug la suma totala

        totalCost.setText(String.format(Locale.getDefault(), "%.2f lei", total));

        //modificare si salvare date, cost total
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

                    //salvez valorile in shared pref si le resetez pe 1 in fiecare luna
                    makePreferences(gazeCost.getText().toString(),curentCost.getText().toString(),
                            intretinereCost.getText().toString(), cabluCost.getText().toString());
                }
            });
        }


    }

    private void makePreferences(String gaze, String curent, String intretinere, String cablu) {
        Context context = getContext().getApplicationContext();

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCES_GAZE_KEY, gaze);
        editor.putString(PREFERENCES_CURENT_KEY, curent);
        editor.putString(PREFERENCES_INTRETINERE_KEY, intretinere);
        editor.putString(PREFERENCES_CABLU_KEY, cablu);

        editor.apply();



    }

    public void sendNotificationWhenMonthChange() {
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if (dayOfMonth == 1) {
            makePreferences("0.0", "0.0", "0.0", "0.0");
        }
    }


}


