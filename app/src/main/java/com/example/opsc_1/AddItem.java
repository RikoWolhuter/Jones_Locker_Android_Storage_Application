package com.example.opsc_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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

    private String nameItem;
    private String goalItem;

    private String nameItem_1;
    private String goalItem_1;

    ListView lstvCollections2;
    private List<String> itemList;
    private ArrayAdapter<String> itemAdapter;

    //itemList = new ArrayList<>();
    //lstvItems = findViewById(R.id.lstv_items);

    ImageView ProfileImage;
    int SELECT_PICTURE = 200;
    private String name;
    private String description;


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
        ArrayAdapter_1();
/*
        TextView NameOfColl = findViewById(R.id.CollName);
        TextView NameofGoa = findViewById(R.id.GoalCollection);

        String TempnameItem = getIntent().getStringExtra("sendnameItem");
        String TempgoalItem = getIntent().getStringExtra("sendgoalItem");

        NameOfColl.setText(TempnameItem);
        NameofGoa.setText(TempgoalItem);
*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ImageButton add;
        ImageButton sort;

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


        //Image widget
        ProfileImage= findViewById(R.id.profileImage);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });


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

    public void ArrayAdapter_1(){


        nameItem = getIntent().getStringExtra("sendnameItem");
        goalItem = getIntent().getStringExtra("sendgoalItem");

        TextView NameColl = findViewById(R.id.CollName);
        TextView GoalColl = findViewById(R.id.GoalCollection);

        NameColl.setText(String.valueOf(nameItem));
        GoalColl.setText(String.valueOf(goalItem));

        nameItem_1 = getIntent().getStringExtra("sendnameOfItem");
        goalItem_1 = getIntent().getStringExtra("sendDescription");
        //String ListView_Item_1 = ;

        lstvCollections2 = (ListView) findViewById(R.id.lstv_items);

        ArrayList<String> arrayList1 = new ArrayList<>();

        arrayList1.add("home");

        ArrayAdapter<String> acAdp1 = new ArrayAdapter(this,R.layout.custom_list_1,
                R.id.text_item,arrayList1);

        lstvCollections2.setAdapter(acAdp1);



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
    //Allow User access to gallery
    void imageChooser(){
        //Creating instance of the intent of type image
        Intent i = new Intent();
        i.setType("image/");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),SELECT_PICTURE);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Compare resultCode with SELECT_Picture constant
            if (requestCode == SELECT_PICTURE) {
                //get Url of image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    //update the preview image in the layout
                    ProfileImage.setImageURI(selectedImageUri);
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
    }
}
