package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
private FirebaseAuth mAuth;

     EditText FullName,PhoneNo,Email_id,Password,CPassword;
     Button SignUp;
     TextView Login;
     ProgressBar Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FullName=findViewById(R.id.editTextName);
        PhoneNo=findViewById(R.id.editTextPhone);
        Email_id=findViewById(R.id.editTextTextEmailAddress);
        Password=findViewById(R.id.editText);
        CPassword=findViewById(R.id.editText2);
        mAuth = FirebaseAuth.getInstance();
        Progress=findViewById(R.id.progressBar2);

        SignUp=findViewById(R.id.Home2);


        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Home_Page.class));
            finish();
        }

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = FullName.getText().toString().trim();
                String phoneno = PhoneNo.getText().toString().trim();
                String email = Email_id.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String cpassword =CPassword.getText().toString().trim();


                if(TextUtils.isEmpty(name)){
                    FullName.setError("Name Is Required. ");
                    return;
                }

                if(TextUtils.isEmpty(phoneno)){
                    PhoneNo.setError("Phone No Is Required. ");
                    return;
                }

                if(phoneno.length() > 10){
                    PhoneNo.setError("Enter A Valid Phone No. ");
                    return;
                }

                if(phoneno.length() <10){
                    PhoneNo.setError("Enter A Valid Phone No. ");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Email_id.setError("Email Is Required. ");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Password.setError("Password Is Required. ");
                    return;
                }

                if(password.length() < 8){
                    Password.setError("Must Have Eight Or More Character. ");
                    return;
                }

                /*if(cpassword.equals(password)){
                    CPassword.setError("Your Password Is Not Same ");
                    return;
                }*/

                Progress.setVisibility(View.VISIBLE);

                //Register User In FireBase

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "Registration Successfull. ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home_Page.class));

                        }else{
                            Toast.makeText(Register.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        Login=findViewById(R.id.Login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NextActivity.class));
            }
        });

        
    }
}