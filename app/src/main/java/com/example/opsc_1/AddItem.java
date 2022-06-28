package com.example.opsc_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference registerUsers = database.getReference("Jone's Locker");
    private FirebaseAuth mAuth;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggleOnOff;
    private NavigationView navigationView;

    TextView NameOfColl;
    TextView GoalColl;

    String CollectionForStringDatabase = NameOfColl.getText().toString();

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

    Intent intentItem1;
    String CollectionItemClicked;
    String CollectionItemClicked1;
    String CollectionItemClicked2;
    String CollectionItemClicked3;

    private FirebaseUser user;
    private String userID;








    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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

        intentItem1 = new Intent(this, AddItemDetails.class);

        Intent SecondIntent = getIntent();
        Intent ThirdIntent = getIntent();

        CollectionItemClicked = SecondIntent.getStringExtra("Collection selected");
        NameOfColl = (TextView) findViewById(R.id.CollName);
        NameOfColl.setText(CollectionItemClicked);

        CollectionItemClicked1 = SecondIntent.getStringExtra("Collection selected Goal");
        GoalColl = (TextView) findViewById(R.id.GoalCollection);
        GoalColl.setText(CollectionItemClicked1);
        if(NameOfColl.equals(null)) {
            CollectionItemClicked2 = ThirdIntent.getStringExtra("Collection show");
            NameOfColl = (TextView) findViewById(R.id.CollName);
            NameOfColl.setText(CollectionItemClicked2);
        }
        if(NameOfColl.equals(null)) {
            CollectionItemClicked3 = ThirdIntent.getStringExtra("CollectionGoal show");
            GoalColl = (TextView) findViewById(R.id.GoalCollection);
            GoalColl.setText(CollectionItemClicked3);
        }
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
        toggleOnOff = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggleOnOff);
        toggleOnOff.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);


        //Image widget
        ProfileImage = findViewById(R.id.profileImage);
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

                intentItem1.putExtra("CollectionForItem", CollectionItemClicked);
                intentItem1.putExtra("CollectionGoalForCollItem", CollectionItemClicked1);
                startActivity(intentItem1);

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

    public void ArrayAdapter_1() {

        itemList = new ArrayList<>();
        lstvCollections2 = (ListView) findViewById(R.id.lstv_collections_1);


        final ArrayAdapter<String> collectionAdapter = new ArrayAdapter<String>(AddItem.this, R.layout.custom_list_1,R.id.text_item,itemList);
        lstvCollections2.setAdapter(collectionAdapter);


        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("StringItems").child(CollectionForStringDatabase).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                itemList.add(value);
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







    }

    /*
    public void openItemDetailsDescriptionPage() {
        Intent intent = new Intent(AddItem.this,ItemDetailsDescription.class);
        startActivity(intent);
    }
     */




    public void openSortListview_Item_Page() {
        Intent intent = new Intent(AddItem.this, sortListviewItem.class);//sort listview class for Items
        startActivity(intent);
    }

    //Allow User access to gallery
    private void imageChooser() {
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
                            ProfileImage.setImageBitmap(
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
