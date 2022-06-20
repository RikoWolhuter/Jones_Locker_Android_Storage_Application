package com.example.opsc_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference registerUsers = database.getReference("Jone's Locker");

    private GetterAndSetters getterAndsetter;
    private TextView Username;
    private TextView Password;
    private Button log;
    private Button reg;
    private Registration.User storedClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getterAndsetter = new GetterAndSetters();

        Username = findViewById(R.id.Username_input_Login);
        Password =  findViewById(R.id.Password_input_Login);


        log = findViewById(R.id.loginbtn);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempUsername = Username.getText().toString();
                String tempPassword = Password.getText().toString();
                Query loginQuery = registerUsers.child("users").orderByChild("userName").equalTo(tempUsername);

                if(!TextUtils.isEmpty(tempUsername) && !TextUtils.isEmpty(tempPassword)) {
                    loginQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                storedClass = user.getValue(Registration.User.class);
                                if (tempPassword.equals(storedClass.getPassword())) {
                                    Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
                                    openMainPage();
                                } else {
                                    Toast.makeText(Login.this, "password incorrect", Toast.LENGTH_SHORT).show();
                                }

                            }




                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(Login.this, "Make sure username is correct", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(Login.this, "Please complete all the fields", Toast.LENGTH_SHORT).show();
                }





            }
        });

        reg = findViewById(R.id.reg);

        reg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });


    }

    public void openMainPage() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }








/*
    String encrypted = "ANY_ENCRYPTED_STRING_HERE";//Insert encrypted string here
    String decrypted = "";
    try {
        decrypted = Registration.decrypt(encrypted);
        Log.d("TEST", "decrypted:" + decrypted);
    } catch (Exception e) {
        e.printStackTrace();
    }
*/

}

