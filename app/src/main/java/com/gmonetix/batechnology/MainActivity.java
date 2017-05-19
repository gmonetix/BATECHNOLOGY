package com.gmonetix.batechnology;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

import com.gmonetix.batechnology.helper.Utils;
import com.gmonetix.batechnology.service.Services;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ContactUs contactUs;
    private Services services;
    private Home home;

    private boolean doubleBackToExitPressedOnce = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,home).commit();
                    return true;
                case R.id.navigation_services:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,services).commit();
                    return true;
                case R.id.navigation_contact_us:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,contactUs).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher);

        Utils.applyFontForToolbarTitle(MainActivity.this);

        home = new Home();
        contactUs = new ContactUs();
        services = new Services();

        if (getSupportFragmentManager().findFragmentById(R.id.frame_container) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,home).commit();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_main_about_us:
                startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
                break;

            case R.id.menu_main_settings:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
