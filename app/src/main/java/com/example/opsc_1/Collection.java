package com.example.opsc_1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.navigation.NavigationView;

public class Collection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleOnOff;
    private NavigationView navigationView;

    private ListView lstvCollections;
    private List<String> collectionsList;
    private ArrayAdapter<String> collectionAdapter;

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
        setContentView(R.layout.collection_main);
        ImageButton add;
        ImageButton sort;
        String name;

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

        collectionsList = new ArrayList<>();
        lstvCollections = findViewById(R.id.lstv_collections);

        add = (ImageButton) findViewById(R.id.Addbtn);
        sort = (ImageButton) findViewById(R.id.Sortbtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddCollectionPage();
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSortListviewPage();
            }
        });

        //Here is some onclick listener code i found on the internet so that a user can view the collection item selected, just needs to be adapted to our app
        /*
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = prestListView.getItemAtPosition(position);
                prestationEco str = (prestationEco)o; //As you are using Default String Adapter

                Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();

                openAddItemMainPage();
            }
        });*/
    }
/*
    public void openAddItemMainPage() {
        Intent intent = new Intent(Collection.this,AddItem.class);
        startActivity(intent);
    }
    */
    public void openAddCollectionPage() {
        Intent intent = new Intent(Collection.this,AddCollection.class);
        startActivity(intent);
    }
    public void openSortListviewPage() {
        Intent intent = new Intent(Collection.this,sortListviewCollection.class);//sort listview class for collection
        startActivity(intent);
    }
/*
    TextView displayName = findViewById(R.id.collectionName);
    Bundle bn = getIntent().getExtras();
    String name = bn.getString("sendname");
    //displayName.setText(String.valueOf(name));
    TextView displayGoal = findViewById(R.id.collectionGoal);
    String goal = bn.getString("sendgoal");
    //displayName.setText(String.valueOf(goal));
*/

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

