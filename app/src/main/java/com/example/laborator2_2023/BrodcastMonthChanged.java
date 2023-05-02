package com.example.laborator2_2023;

import static com.google.android.material.internal.ContextUtils.getActivity;
import static java.security.AccessController.getContext;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class BrodcastMonthChanged extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) { //ACTION_TIME_TICK
//            Calendar calendar = Calendar.getInstance();
//            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//            if (dayOfMonth == 3) {
                Toast.makeText(context, "O noua luna a inceput", Toast.LENGTH_LONG).show();
            //}
        }

    }


}
