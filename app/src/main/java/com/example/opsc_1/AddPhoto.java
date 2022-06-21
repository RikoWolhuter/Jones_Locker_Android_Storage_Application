package com.example.opsc_1;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddPhoto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

        private FloatingActionButton fab;
        private ImageView imgCameraImage;
        private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 100;
        private static final int REQUEST_IMAGE_CAPTURE = 1;
    // Uri indicates, where the image will be picked from
        private Uri filePath;


        private Toolbar toolbar;
        private DrawerLayout drawerLayout;
        private ActionBarDrawerToggle toggleOnOff;
        private NavigationView navigationView;


//Navigation
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
            setContentView(R.layout.add_photo);
            //night mode off
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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

            //Camera floating Action Button to take image
            fab = findViewById(R.id.floatingActionButton);
            //Img will be replaced with Image User takes
            imgCameraImage = findViewById(R.id.profileImage);
            //Old Camera code
           /* fab.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    //check if we have camera permission
                    if(ActivityCompat.checkSelfPermission(AddPhoto.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED){
                        final String[] permissions ={Manifest.permission.CAMERA};
                        //Request permission- this is asynchronous
                        ActivityCompat.requestPermissions(AddPhoto.this,
                                permissions, REQUEST_IMAGE_CAPTURE_PERMISSION);
                    }else
                    {
                        //we have permission, so take the photo
                        takePhoto();

                    }
                }
            });*/


            // get the Firebase  storage reference
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            // on pressing btnSelect SelectImage() is called
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectImage();
                    uploadImage();
                }
            });

        }
    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imgCameraImage.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(AddPhoto.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddPhoto.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }
            //Old Camera  code
            /*
        //Camera Permissions
        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
            if(requestCode == REQUEST_IMAGE_CAPTURE_PERMISSION &&
                    ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_GRANTED){
                takePhoto();
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode,@Nullable  Intent data){
        super.onActivityResult(requestCode , resultCode, data);
            //check if we are receiving the result from the right request.
            //Also check whether the data null, meaning the user may have cancelled.
            if(requestCode== REQUEST_IMAGE_CAPTURE && data !=null){
               Bitmap bitmap =(Bitmap) data.getExtras().get("data");
                imgCameraImage.setImageBitmap(bitmap);

                }

            //}
        }

        private void takePhoto(){
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i,REQUEST_IMAGE_CAPTURE);
        }*/



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

