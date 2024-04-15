package com.example.smart_agri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    //Declaring UI Variables
    EditText edUsername , edPassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.login_username);
        edPassword = findViewById(R.id.login_password);
        btn = findViewById(R.id.login_button);
        tv = findViewById(R.id.signupRedirectText);
        Database db = new Database(getApplicationContext(),"smartagri",null,1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Geting a username and password from user
                String username = edUsername.getText().toString();
                String passsword = edPassword.getText().toString();

                if (username.length() == 0 || passsword.length() == 0) {
                    Toast.makeText(login.this, "Please Fill Your All Details", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.login(username, passsword)==1) {
                        Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("username", username);
                        //To save our data with key and value.
                        editor.apply();
                        startActivity(new Intent(login.this, splash_2.class));
                    } else {
                        Toast.makeText(login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,registration.class));
            }
        });


    }
}