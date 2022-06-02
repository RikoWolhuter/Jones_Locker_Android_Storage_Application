package com.example.opsc_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AddItemDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleOnOff;
    private NavigationView navigationView;
    int SELECT_PICTURE = 200;
    ImageView ImageGallery;

    //variable to capture the amount of times a user has created an item, counter starts at 0, J-L
    int counter = 0;

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
        setContentView(R.layout.add_item_details);

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

        ImageGallery =findViewById(R.id.imageView6);
        //Choose Image button to trigger image chooser
        ImageGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageChooser();
            }
        });
        //convert to bitmap to be passed to next page
        ImageGallery.buildDrawingCache();
        Bitmap bitmap = ImageGallery.getDrawingCache();
        Intent intent = new Intent(this, ItemDetailsDescription.class);
        intent.putExtra("BitmapImage", bitmap);

        Button continueToItems =findViewById(R.id.addItemcollectionbtn);
        //Choose Image button to trigger image chooser
        continueToItems.setOnClickListener(new View.OnClickListener(){
                                               @Override
                                               public  void onClick(View v) {
                                                   EditText name = findViewById(R.id.NameOfItem);
                                                   //Goal of the Collection added by user
                                                   EditText Description = findViewById(R.id.Description);


                                                   String tempName = name.getText().toString();
                                                   //Store Goal of Collection in tempGoal
                                                   String tempDescription = Description.getText().toString();

                                                   Intent intent = new Intent(AddItemDetails.this, AddItem.class);

                                                   intent.putExtra("sendnameOfItem",tempName);
                                                   intent.putExtra("sendDescription",tempDescription);

                                                   startActivity(intent);
                                                   finish();
                                               }
                                               });
    }

    //Allow User access to gallery
    void imageChooser(){
        //Creating instance of the intent of type image
        Intent i = new Intent();
        i.setType("image/");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),SELECT_PICTURE);

    }
    //Image choosing
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Compare resultCode with SELECT_Picture constant
            if (requestCode == SELECT_PICTURE) {
                //get Url of image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    //update the preview image in the layout
                    ImageGallery.setImageURI(selectedImageUri);

                }
/*
//Each click of creating an item will increase the counter J-L

public void additemcollectionbtn (View view)
{
counter++
//I am unsure of how to show the users physically progression with diamond icon
if (ETGoal >= counter)
{
message.setText("Goal reached")
}
else if (ETGoal < counter)
{
message.setText("Goal is not reached")
}
}

                }*/
            }
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
