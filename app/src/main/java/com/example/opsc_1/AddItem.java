package com.example.opsc_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleOnOff;
    private NavigationView navigationView;

    private ListView lstvItems;
    private List<String> itemList;
    private ArrayAdapter<String> itemAdapter;

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
        setContentView(R.layout.add_item_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ImageButton add;
        ImageButton sort;

        toolbar = findViewById(R.id.nav_toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggleOnOff = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggleOnOff);
        toggleOnOff.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        itemList = new ArrayList<>();
        lstvItems = findViewById(R.id.lstv_items);

        add = (ImageButton) findViewById(R.id.Addbtn_items);
        sort = (ImageButton) findViewById(R.id.Sortbtn_items);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddItemPage();
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSortListview_Item_Page();
            }
        });

        //Here is some onclick listener code i found on the internet so that a user can view the item item selected, just needs to be adapted to our app
        /*
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = prestListView.getItemAtPosition(position);
                prestationEco str = (prestationEco)o; //As you are using Default String Adapter

                Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();

                openItemDetailsDescriptionPage();
            }
        });*/
    }
    /*
    public void openItemDetailsDescriptionPage() {
        Intent intent = new Intent(AddItem.this,ItemDetailsDescription.class);
        startActivity(intent);
    }
     */


    public void openAddItemPage() {
        Intent intent = new Intent(AddItem.this,AddItemDetails.class);
        startActivity(intent);
    }
    public void openSortListview_Item_Page() {
        Intent intent = new Intent(AddItem.this,sortListviewItem.class);//sort listview class for Items
        startActivity(intent);
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
