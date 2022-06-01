package com.example.opsc_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Login extends AppCompatActivity {
    private GetterAndSetters getterAndsetter;
    private TextView Username;
    private TextView Password;
    private Button log;
    private Button reg;
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

                if(!TextUtils.isEmpty(tempUsername) && !TextUtils.isEmpty(tempPassword)) {//&& (tempUsername).equals(getterAndsetter.getUsername()) && (tempPassword).equals(getterAndsetter.getPassword())
                    openMainPage();
                }
                else{
                    Toast.makeText(Login.this, "Please complete all the fields and make sure login details are correct", Toast.LENGTH_SHORT).show();
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

