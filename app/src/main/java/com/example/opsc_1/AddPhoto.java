package com.example.opsc_1;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class AddPhoto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

        private FloatingActionButton fab;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


        private Toolbar toolbar;
        private DrawerLayout drawerLayout;
        private ActionBarDrawerToggle toggleOnOff;
        private NavigationView navigationView;


//Navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId()){
            case R.id.nav_photo:
                IntentHelper.openIntent(this, AddPhoto.class);
                break;
            case R.id.nav_collection:
                IntentHelper.openIntent(this, Collection.class);
                break;
            case R.id.nav_graph:
                IntentHelper.openIntent(this, Graph.class);
                break;
            case R.id.nav_progression:
                IntentHelper.openIntent(this, Goal.class);
                break;
            case R.id.nav_profile:
                IntentHelper.openIntent(this, Profile.class);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        //returning true marks the item as selected
        return true;
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_photo);
            //night mode off
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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

            //Camera floating Action Button to take image
            fab= findViewById(R.id.floatingActionButton);
            //Img will be replaced with Image User takes
            imageView= findViewById(R.id.profileImage);



            fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            });

        }
        //Camera Permissions
        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == MY_CAMERA_PERMISSION_CODE)
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else
                {
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode,@Nullable  Intent data){
        super.onActivityResult(requestCode , resultCode, data);
            if (requestCode == CAMERA_REQUEST && resultCode == AddPhoto.RESULT_OK)
            {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
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

