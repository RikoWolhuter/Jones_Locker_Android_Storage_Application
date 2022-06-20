package com.example.opsc_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Registration extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference registerUsers = database.getReference("Jone's Locker");

    private GetterAndSetters getterAndsetter;
    private TextView Username;
    private TextView Password;
    private TextView confirmPassword;
    private TextView gmail;

    private Register_Class register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        register = new Register_Class();

        getterAndsetter = new GetterAndSetters();

        Username = findViewById(R.id.username_input);
        Password =  findViewById(R.id.password_input);
        confirmPassword = findViewById(R.id.confirmPassword_input);
        gmail = findViewById(R.id.gmail_input);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Button log;
        Button reg;

        log = (Button) findViewById(R.id.goBackToLogin);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPage();
            }
        });

        reg = (Button) findViewById(R.id.register);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = "username";
                String passWord = "password";
                String Gmail = "gmail";


                String tempUsername = Username.getText().toString();
                String tempPassword = Password.getText().toString();
                String tempconfirmPassword = confirmPassword.getText().toString();
                String tempgmail = gmail.getText().toString();


                if(!TextUtils.isEmpty(tempUsername) && !TextUtils.isEmpty(tempPassword) &&
                        !TextUtils.isEmpty(tempconfirmPassword) &&
                        !TextUtils.isEmpty(tempgmail) && tempPassword.equals(tempconfirmPassword)) {



                    Intent intent = new Intent(Registration.this,Login.class);

                    tempUsername = Username.getText().toString();
                    tempPassword = Password.getText().toString();
                    tempgmail = gmail.getText().toString();
                    //intent.putExtra("sendUsername",tempUsername);
                    //intent.putExtra("sendPassword",tempPassword);

                    //register.setC_userName(tempUsername);
                    //register.setC_Password_1(tempPassword);
                    //registerUsers.push().setValue(register);
                    registerUsers.child("users").child(Gmail).setValue(tempgmail);
                    registerUsers.child("users").child(userName).setValue(tempUsername);
                    registerUsers.child("users").child(passWord).setValue(tempPassword);



                    startActivity(intent);

                }
                else{
                    Toast.makeText(Registration.this, "Please complete all the fields", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void openLoginPage() {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public class User{
        public User(){

        }

        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getGmail() {
            return gmail;
        }

        public void setGmail(String gmail) {
            this.gmail = gmail;
        }

        private String password;
        private String gmail;
    }
    public class Register_Class {
        public Register_Class() {

        }

        public String getC_userName() {
            return C_userName;
        }

        public void setC_userName(String c_userName) {
            C_userName = c_userName;
        }

        public String getC_Password_1() {
            return C_Password_1;
        }

        public void setC_Password_1(String c_Password_1) {
            C_Password_1 = c_Password_1;
        }

        private String C_userName;
        private String C_Password_1;
    }






/*
    private static final byte[] keyValue =
            new byte[]{'c', 'o', 'd', 'i', 'n', 'g', 'a', 'f', 'f', 'a', 'i', 'r', 's', 'c', 'o', 'm'};


    public static String encrypt(String cleartext)
            throws Exception {
        byte[] rawKey = getRawKey();
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    public static String decrypt(String encrypted)
            throws Exception {

        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(enc);
        return new String(result);
    }

    private static byte[] getRawKey() throws Exception {
        SecretKey key = new SecretKeySpec(keyValue, "AES");
        byte[] raw = key.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKey skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] encrypted)
            throws Exception {
        SecretKey skeySpec = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    String encrypted = "";
    String sourceStr = "This is any source string";//Insert password string into here
        try {
        encrypted = Registration.encrypt(sourceStr);
        Log.d("TEST", "encrypted:" + encrypted);
    } catch {

    }
*/

}
