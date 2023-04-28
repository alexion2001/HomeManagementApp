package com.example.laborator2_2023;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, HomeFragment.class, null)
                .commit();

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
                //actualizez numele casei la logare --TODO
            }
        });



    }


}
