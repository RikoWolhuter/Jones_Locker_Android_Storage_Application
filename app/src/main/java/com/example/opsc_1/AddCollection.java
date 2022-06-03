package com.example.opsc_1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.awt.font.TextAttribute;


public class AddCollection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleOnOff;
    private NavigationView navigationView;
    int SELECT_PICTURE = 200;
    ImageView collectionImage;


    //created int variable to capture the goal J-L
    int goal;

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
        setContentView(R.layout.add_collection);

        //Remove night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //add navigation
        toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggleOnOff = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggleOnOff);
        toggleOnOff.syncState();

        //navigation settings
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        //Collection Name added by user
        EditText name = findViewById(R.id.ETName);
        //Goal of the Collection added by user
        EditText goal = findViewById(R.id.ETGoal);


        //ImageView clickable to change picture
        collectionImage = findViewById(R.id.imageView6);
        //change picture by calling ImageChooser(), when clicked
        collectionImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageChooser();
            }
        });
        //Add button to add new Collection to list
        Button add = findViewById(R.id.addcollectionbtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Store Name of Collection in tempName
                String tempName = name.getText().toString();
                //Store Goal of Collection in tempGoal
                String tempGoal = goal.getText().toString();
                Boolean  bool = true;
                //save image to next view
                collectionImage.setDrawingCacheEnabled(true);
                Bitmap b = collectionImage.getDrawingCache();



                Intent intent = new Intent(AddCollection.this,Collection.class);
                //Send name & goal to Collection Class
                intent.putExtra("sendname",tempName);
                intent.putExtra("sendgoal",tempGoal);
                intent.putExtra("clicked",bool);

                startActivity(intent);

                Intent intent2 = new Intent(AddCollection.this,AddItem.class);
                intent2.putExtra("Bitmap",b);

                finish();

            }
        });
        /*
         Button add = findViewById(R.id.addcollectionbtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Store Name of Collection in tempName
                String tempName = name.getText().toString();
                //Store Goal of Collection in tempGoal
                String tempGoal = goal.getText().toString();

                Intent intent = new Intent(AddCollection.this,Collection.class);
                //Send name & goal to Collection Class
                intent.putExtra("sendname",tempName);
                intent.putExtra("sendgoal",tempGoal);
                startActivity(intent);
                finish();

            }
        });
         */

    }
    //Allow User access to gallery
    void imageChooser(){
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");

        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    collectionImage.setImageURI(selectedImageUri);
                }
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

        /*
           //Trying to capture the goal the user has set for the collection

           addcollectionbtn = (Button) findViewById(R.id.addcollectionbtn);
           addcollectionbtn = setOnClickListener(new View.OnClickListener())
    {

        public void OnClick (View view)
        {
            EditText goal = findViewById(R.id.ETGoal);
            ETGoal = integer.valueOf(ETGoal.getText().toString())

            //if statement that if the user reaches their goal it gives them a diamond icon (I am unsure of how to do this J-L)

            if (ETGoal >= counter)
            {
            message.setText("Goal reached")
            }
            else if (ETGoal < counter)
            {
            message.setText("Goal is not reached not")
            }
            }


        }
*/

        }

    }




