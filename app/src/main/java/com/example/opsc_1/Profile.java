package com.example.opsc_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleOnOff;
    private NavigationView navigationView;
    private Button logout;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference registerUsers = FirebaseDatabase.getInstance().getReference("Users");
    private int Medal_lvl = 0;
    private ImageView imgMedalProfile;


    private DatabaseReference reference;
    private String userID;

    Registration.User userProfile;

    private String fullname;
    private String gmail;
    private String password;

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
                IntentHelper.openIntent(this, Graph.class);//Class must be for Graph
                break;
            case R.id.nav_progression:
                IntentHelper.openIntent(this, Goal.class);//Class must be for Goals
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
        setContentView(R.layout.profile_main);
        mAuth = FirebaseAuth.getInstance();



        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Medal_Level").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = dataSnapshot.getValue(String.class);
                Medal_lvl =Integer.parseInt(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
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

        logout = (Button) findViewById(R.id.LogOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Profile.this, Login.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        final TextView NameTextView = (TextView) findViewById(R.id.Name);
        final TextView PassTextView = (TextView) findViewById(R.id.Password);
        final TextView EmailTextView = (TextView) findViewById(R.id.Email);

        //reference.child(userID).

        registerUsers.child(userID).child("gmail_").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    EmailTextView.setText(dataSnapshot.getValue(String.class));
                }
                else{
                    EmailTextView.setText("Not found");
                }
                        //PassTextView.setText(password);
                        //EmailTextView.setText(gmail);



                    //Toast.makeText(Profile.this, "DataSnapShotError!", Toast.LENGTH_LONG).show();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        registerUsers.child(userID).child("password").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    PassTextView.setText(dataSnapshot.getValue(String.class));
                }
                else{
                    PassTextView.setText("Not found");
                }
                //PassTextView.setText(password);
                //EmailTextView.setText(gmail);



                //Toast.makeText(Profile.this, "DataSnapShotError!", Toast.LENGTH_LONG).show();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        registerUsers.child(userID).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    NameTextView.setText(dataSnapshot.getValue(String.class));
                }
                else{
                    NameTextView.setText("Not found");
                }
                //PassTextView.setText(password);
                //EmailTextView.setText(gmail);



                //Toast.makeText(Profile.this, "DataSnapShotError!", Toast.LENGTH_LONG).show();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
        imgMedalProfile = findViewById(R.id.MedalProfile);

        if(Medal_lvl >= 300){
            imgMedalProfile.setImageResource(R.drawable.gold__1_);

        }
        if(Medal_lvl >= 200 && Medal_lvl < 300){
            imgMedalProfile.setImageResource(R.drawable.silver__1_);

        }
        if(Medal_lvl >= 100 && Medal_lvl < 200){
            imgMedalProfile.setImageResource(R.drawable.bronze__1_);

        }
        else{
            imgMedalProfile.setImageResource(R.drawable.silver_faded);

        }


        imgMedalProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Profile.this, Medal.class));
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
    }

}
