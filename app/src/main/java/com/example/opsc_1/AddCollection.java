package com.example.opsc_1;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AddCollection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference registerUsers = database.getReference("Jone's Locker");
    private FirebaseAuth mAuth;

    private String tempName;
    private String tempGoal;

    private int Medal_lvl = 0;


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

        mAuth = FirebaseAuth.getInstance();

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
                tempName = name.getText().toString().trim();
                //Store Goal of Collection in tempGoal
                tempGoal = goal.getText().toString().trim();
                Boolean  bool = true;
                //save image to next view
                collectionImage.setDrawingCacheEnabled(true);
                Bitmap b = collectionImage.getDrawingCache();



                Intent intent = new Intent(AddCollection.this,Collection.class);
                //Send name & goal to Collection Class

                AddCollectionToDatabase();

                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Medal_Level").child("Medal_Level").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        //Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        Medal_lvl =Integer.parseInt(String.valueOf(value));

                        int Medal_lvl_Increase = Medal_lvl + 30;
                        String Medal_lvl_IncreaseString = Integer.toString(Medal_lvl_Increase);

                        HashMap hashMap = new HashMap();
                        hashMap.put("Medal_Level",Medal_lvl_IncreaseString);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Medal_Level")
                                .updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }
                });



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
    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        collectionImage.setImageBitmap(
                                selectedImageBitmap);
                    }
                }
            });
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

    public void AddCollectionToDatabase() {

        String StringForCollection = tempName + " " + tempGoal;

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("StringCollections").child(tempName)
                .setValue(StringForCollection).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
                else{
                    Toast.makeText(AddCollection.this, "Name has not been added", Toast.LENGTH_LONG).show();
                }
            }
        });


        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Collections").child(tempName).child("Name")
                .setValue(tempName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
                else{
                    Toast.makeText(AddCollection.this, "Name has not been added", Toast.LENGTH_LONG).show();
                }
            }
        });

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Collections").child(tempName).child("Goal")
                .setValue(tempGoal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
                else{
                    Toast.makeText(AddCollection.this, "Goal has not been added!", Toast.LENGTH_LONG).show();
                }
            }
        });
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




