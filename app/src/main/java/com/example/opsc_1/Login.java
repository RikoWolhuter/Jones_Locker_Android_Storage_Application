package com.example.opsc_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button log;
        Button reg;


        log = (Button) findViewById(R.id.loginbtn);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPage();
            }
        });

        reg =(Button) findViewById(R.id.regbtn);

        reg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                openRegPage();
            }
        });


    }

    public void openMainPage() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }




    public void openRegPage() {
      Intent intent = new Intent(this,Login.class);
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

