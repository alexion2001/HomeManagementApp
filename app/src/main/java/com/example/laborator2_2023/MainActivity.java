package com.example.laborator2_2023;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.Manifest;
import android.app.Activity;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private BrodcastMonthChanged brodcastMonthChanged;

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 123;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    TextView textView;
    public final static String PREFERENCES_KEY = "preferences key";
    public final static String PREFERENCES_HOUSE_KEY = "preferences key house";
    public final static String PREFERENCES_NAME_KEY = "preferences key name";


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(brodcastMonthChanged);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String fragmentToOpen = intent.getStringExtra("FRAGMENT_TO_OPEN");

        if (fragmentToOpen != null) {
            // Replace the default fragment with the LoginFragment
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container, LoginFragment.class, null)
                    .commit();

         } else {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, HomeFragment.class, null)
                            .commit();
                }

        //permisiune brodcast
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity,
//                    new String[]{Manifest.permission.READ_PHONE_STATE},
//                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
//        }


        brodcastMonthChanged = new BrodcastMonthChanged();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(brodcastMonthChanged, filter);

        //meniu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.menu_open,R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:

                        HomeFragment homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, homeFragment)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                       break;
                    case R.id.nav_facuri:

                        FacturiFragment facuriFragment = new FacturiFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, facuriFragment)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.nav_tasks:

                        TasksFragment tasksFragment = new TasksFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, tasksFragment)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    default:
                        Log.d("MAINACTIVITYCLICK", "id negasit");
                }
                return true;

            }
        });



    }


}
