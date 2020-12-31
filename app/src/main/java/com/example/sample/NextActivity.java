package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NextActivity extends AppCompatActivity {
private FirebaseAuth mAuth;

    ProgressBar Progress;
    EditText Email_id,Password;
    Button Signin;
    TextView Forgot,Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        Email_id=findViewById(R.id.LogEmail);
        Password=findViewById(R.id.LogPass);
        Progress=findViewById(R.id.progressBar3);

        mAuth=FirebaseAuth.getInstance();


        Signin=findViewById(R.id.Home1);
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home_Page.class));

                String email = Email_id.getText().toString().trim();
                String password = Password.getText().toString().trim();

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

                Progress.setVisibility(View.VISIBLE);

                //Ahuthenticate the use

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(NextActivity.this, "Login Successfully. ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home_Page.class));
                        }else {
                            Toast.makeText(NextActivity.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        Signup=findViewById(R.id.Login);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        Forgot=findViewById(R.id.Tv1);
        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Forgot.class);

                startActivity(intent);
            }
        });

    }
}