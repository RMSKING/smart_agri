package com.example.smart_agri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class registration extends AppCompatActivity {

    EditText edusername,edmail,edpassword,edconfirm;
    Button btn;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edusername = findViewById(R.id.sign_username);
        edmail = findViewById(R.id.sign_mail);
        edpassword = findViewById(R.id.sign_password);
        edconfirm = findViewById(R.id.sign_cpass);
        btn = findViewById(R.id.signup_button);
        tv = findViewById(R.id.loginRedirectText);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(registration.this, login.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ask a input from user
                String username = edusername.getText().toString();
                String email = edmail.getText().toString();
                String password = edpassword.getText().toString();
                String confirm = edconfirm.getText().toString();
                Database db = new Database(getApplicationContext(),"smartagri",null,1);

                if (username.length() == 0 || email.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                    Toast.makeText(registration.this, "Fill out the form below", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.compareTo(confirm) == 0) {
                        if (isValid(password))
                        {
                            db.register(username,email,password);
                            Toast.makeText(registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(registration.this, login.class));
                        } else {
                            Toast.makeText(registration.this, "Password must contain at least a characters, having letter digit and special characters ", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(registration.this, "Password and Confirm Password didn't Match", Toast.LENGTH_SHORT).show();
                    }
                    if (isValidEmail(email)){
                        Toast.makeText(registration.this, "Valid Email", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(registration.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }





    public static boolean isValid(String passwordhere){
        int f1=0,f2=0,f3=0;
        if(passwordhere.length() < 0){
            return false;
        }else {
            for(int p=0; p < passwordhere.length(); p++){
                if(Character.isLetter(passwordhere.charAt(p))){
                    f1=1;
                }
            }
            for (int r = 0; r < passwordhere.length(); r++){

                if (Character.isDigit(passwordhere.charAt(r))){
                    f2=1;
                }
            }
            for(int s = 0; s < passwordhere.length(); s++){
                char c = passwordhere.charAt(s);
                if(c>=33&&c<=46||c==64){
                    f3=1;
                }
            }
            if(f1==1 && f2==1 && f3==1)
                return true;
            return  false;
        }
    }
            public boolean isValidEmail(String email){
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }


}