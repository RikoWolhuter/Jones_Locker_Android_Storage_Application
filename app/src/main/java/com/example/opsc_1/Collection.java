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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.navigation.NavigationView;

public class
Collection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleOnOff;
    private NavigationView navigationView;

    ListView lstvCollections;
    private List<String> collectionsList;
    private ArrayAdapter<String> collectionAdapter;

    private ArrayAdapter<String>acAdp;
    private String name;
    private String goal;


    Button buttonForClickEvent;
    /*
    ListView lstvCollections;
    private List<String> collectionsList;
    private ArrayAdapter<String> collectionAdapter;

    private ArrayAdapter<String>acAdp;
    private String name;
    private String goal;
     */

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

        ArrayAdapter();
        //ArrayAdapter();


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

    public void ArrayAdapter(){

        //buttonForClickEvent = (Button)findViewById(R.id.addcollectionbtn);
        // TextView displayName = findViewById(R.id.ETName);



        //Boolean click_OnContinue = getIntent().getBooleanExtra("clicked",true);
        name = getIntent().getStringExtra("sendname");
        goal = getIntent().getStringExtra("sendgoal");
        //displayName.setText(String.valueOf(name));
        //TextView displayGoal = findViewById(R.id.ETGoal);
        String ListView_Item = name +"               "+ goal;

        //displayName.setText(String.valueOf(goal));
        lstvCollections = (ListView) findViewById(R.id.lstv_collections_1);

        ArrayList<String> arrayList = new ArrayList<>();

        //while(click_OnContinue = true) {
        arrayList.add(ListView_Item);
        //click_OnContinue = false;
        //}






        ArrayAdapter<String> acAdp = new ArrayAdapter(this,R.layout.custom_list,
                R.id.text,arrayList);

        lstvCollections.setAdapter(acAdp);

        lstvCollections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String CollectionItem = acAdp.getItem(position);

                Intent intent = new Intent(Collection.this,AddItem.class);//sort listview class for collection
                intent.putExtra("sendnameItem",name);
                intent.putExtra("sendgoalItem",goal);
                startActivity(intent);
            }
        });



    }

    /*
        public void ArrayAdapter(){


        // TextView displayName = findViewById(R.id.ETName);


        String name = getIntent().getStringExtra("sendname");
        String goal = getIntent().getStringExtra("sendgoal");
        //displayName.setText(String.valueOf(name));
        //TextView displayGoal = findViewById(R.id.ETGoal);
        String ListView_Item = name +"         goal: "+ goal;

        //displayName.setText(String.valueOf(goal));
        lstvCollections = (ListView) findViewById(R.id.lstv_collections_1);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(ListView_Item);



        ArrayAdapter acAdp = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);

        lstvCollections.setAdapter(acAdp);

    }
     */




/*
    public void AddingCollections(){
       // ac = new ArrayList<>();
        EditText N = findViewById(R.id.ETName);
        EditText G = findViewById(R.id.ETGoal);
        ac.add(N);
        ac.add(G);
 //       ArrayAdapter acAdp = new ArrayAdapter<String>(this,R.layout.add_collection,N.getId());
        ListView coll = (ListView) findViewById(R.id.lstv_collections);
        coll.setAdapter(acAdp);




    }
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

