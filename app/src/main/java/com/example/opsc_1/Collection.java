package com.example.opsc_1;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Collection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleOnOff;
    private NavigationView navigationView;

    private ListView lstvCollections;
    private List<String> collectionsList;


    private FirebaseUser user;
    private String userID;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference registerUsers = database.getReference("Jone's Locker");
    private FirebaseAuth mAuth;

    Intent intentItem;


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

        intentItem = new Intent(this, AddItem.class);

        mAuth = FirebaseAuth.getInstance();

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
        lstvCollections = (ListView) findViewById(R.id.lstv_collections_1);


        final ArrayAdapter<String> collectionAdapter = new ArrayAdapter<String>(Collection.this, R.layout.custom_list,R.id.text,collectionsList);
        lstvCollections.setAdapter(collectionAdapter);


        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("StringCollections").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            String value = snapshot.getValue(String.class);
            collectionsList.add(value);
            collectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                collectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
    @Override
    public void onItemClick(AdapterView<?> parent, View view,
        int position, long id) {
        Toast.makeText(getApplicationContext(),
            "Click ListItem Number " + position, Toast.LENGTH_LONG)
            .show();
    }
});
*/
        lstvCollections.setOnItemClickListener(listClick);




    }

    private AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView parent, View view, int i, long l) {
            String itemValue  = ((String) lstvCollections.getItemAtPosition(i)).trim();

            char[] charArray = itemValue.toCharArray();
            String result = ("");

            for (int x = 0; x < charArray.length; x++) {

                // Check if the specified character is not digit
                // then add this character into result variable
                if (!Character.isDigit(charArray[x])) {
                    result = result + charArray[x];
                }
            }

            char[] charArray1 = itemValue.toCharArray();
            String result1 = ("");

            for (int x = 0; x < charArray1.length; x++) {

                // Check if the specified character is not digit
                // then add this character into result variable
                if (Character.isDigit(charArray1[x])) {
                    result1 = result1 + charArray1[x];
                }
            }


                intentItem.putExtra("Collection selected", result);
                intentItem.putExtra("Collection selected Goal", result1);
                 startActivity(intentItem);

        }
    };
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

