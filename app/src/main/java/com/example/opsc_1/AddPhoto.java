package com.example.opsc_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class AddPhoto extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

        private FloatingActionButton fab;
        private ImageView imgCameraImage;
        private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 100;
        private static final int REQUEST_IMAGE_CAPTURE = 0;

        private Toolbar toolbar;
        private DrawerLayout drawerLayout;
        private ActionBarDrawerToggle toggleOnOff;
        private NavigationView navigationView;

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
                IntentHelper.openIntent(this, OrderDetailsActivity.class);//Class must be for Graph
                break;
            case R.id.nav_progression:
                IntentHelper.openIntent(this, OrderDetailsActivity.class);//Class must be for Goals
                break;
            case R.id.nav_profile:
                IntentHelper.openIntent(this, OrderDetailsActivity.class);//Class must be for Profile
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

            fab= findViewById(R.id.floatingActionButton);
            imgCameraImage= findViewById(R.id.profileImage);
            fab.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    //check if we have camera permission
                    if(ActivityCompat.checkSelfPermission(AddPhoto.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED){
                        final String[] permissions ={Manifest.permission.CAMERA};
                        //Request permission- this is asynchronous
                        ActivityCompat.requestPermissions(AddPhoto.this, permissions, REQUEST_IMAGE_CAPTURE_PERMISSION);
                    }else
                    {
                        //we have permission, so take the photo
                        takePhoto();
                    }
                }
            });
        }
        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
            if(requestCode == REQUEST_IMAGE_CAPTURE_PERMISSION &&
                    ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                takePhoto();;
            }
        }

        @Override protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode , resultCode, data);
            //check if we are receiving the result from the right request.
            //Also check whether the data null, meaning the user may have cancelled.
            if(requestCode== REQUEST_IMAGE_CAPTURE && data !=null){
                Bitmap bitmap =(Bitmap) data.getExtras().get("data");
                imgCameraImage.setImageBitmap(bitmap);
            }
        }
        private void takePhoto(){
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i,REQUEST_IMAGE_CAPTURE);
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

    public  void onClick(View v)
    {

    }


    }

