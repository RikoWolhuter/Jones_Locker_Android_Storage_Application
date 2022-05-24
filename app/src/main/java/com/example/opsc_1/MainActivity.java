package com.example.opsc_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId()){
            case R.id.nav_photo:
                IntentHelper.openIntent(this, AddPhoto.class);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        //returning true marks the item as selected
        return true;
    }

    private ImageView img_Sb1;
    private ImageView img_Sb2;
    private ImageView img_Sb3;
    private ImageView img_Sb4;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleOnOff;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggleOnOff = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggleOnOff);
        toggleOnOff.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        img_Sb1 = findViewById(R.id.imageView4);
        img_Sb2 = findViewById(R.id.imageView6);
        img_Sb3 = findViewById(R.id.imageView7);
        img_Sb4 = findViewById(R.id.imageView8);

        img_Sb1.setOnClickListener(this);
        img_Sb2.setOnClickListener(this);
        img_Sb3.setOnClickListener(this);
        img_Sb4.setOnClickListener(this);

    }

    public  void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.imageView4:
                IntentHelper.openIntent(this, Collection.class);
                break;
            case R.id.imageView6:
                IntentHelper.openIntent(this, AddPhoto.class);
                break;
            case R.id.imageView7:
                IntentHelper.openIntent(this, OrderDetailsActivity.class);//Class must be for Graph
                break;
            case R.id.imageView8:
                IntentHelper.openIntent(this, OrderDetailsActivity.class);//Class must be for Goals
                break;
        }

    }
    @Override
    public void onBackPressed(){
        //if the drawer is open, close it
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            //otherwise, let the super class handle it
            super.onBackPressed();
        }
    }


}