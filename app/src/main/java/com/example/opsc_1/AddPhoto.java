package com.example.opsc_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddPhoto extends AppCompatActivity{

        private FloatingActionButton fab;
        private ImageView imgCameraImage;
        private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 100;
        private static final int REQUEST_IMAGE_CAPTURE = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_photo);

            fab= findViewById(R.id.floatingActionButton);
            imgCameraImage= findViewById(R.id.profileImage);
            fab.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    //check if we have camera permission
                    if(ActivityCompat.checkSelfPermission(AddPhoto.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED){
                        final String[] permissions ={Manifest.permission.CAMERA};
                        //Request permission- this is asynchronous
                        ActivityCompat.requestPermissions(AddPhoto.this, permissions, REQUEST_IMAGE_CAPTURE_PERMISSION);
                    }else
                    {
                        //we have permission, so take the photo
                        takePhoto();
                    }
                }
            });
        }
        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
            if(requestCode == REQUEST_IMAGE_CAPTURE_PERMISSION &&
                    ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                takePhoto();;
            }
        }

        @Override protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode , resultCode, data);
            //check if we are receiving the result from the right request.
            //Also check whether the data null, meaning the user may have cancelled.
            if(requestCode== REQUEST_IMAGE_CAPTURE && data !=null){
                Bitmap bitmap =(Bitmap) data.getExtras().get("data");
                imgCameraImage.setImageBitmap(bitmap);
            }
        }
        private void takePhoto(){
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i,REQUEST_IMAGE_CAPTURE);
        }


    }

