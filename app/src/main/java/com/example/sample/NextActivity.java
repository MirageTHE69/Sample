package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NextActivity extends AppCompatActivity {

    SQLiteDatabase Login_Db;
    EditText User_Name,Password;
    Button Signin;
    TextView Forgot,Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        Login_Db = new Login_Db(this).getWritableDatabase();

        User_Name=findViewById(R.id.LogUser);
        Password=findViewById(R.id.LogPass);

        Signin=findViewById(R.id.Home1);
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namestr = User_Name.getText().toString();
                String Passwordstr = Password.getText().toString();

                ContentValues values = new ContentValues();
                values.put("User_Name",namestr);
                values.put("Password",Passwordstr);
                Login_Db.insert("Login",null, values);
                Toast.makeText(getApplicationContext(),"Data Inserted into database", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),Home_Page.class));
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