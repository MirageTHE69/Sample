package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sanitize extends AppCompatActivity implements PaymentResultListener {

    Button py;
    EditText UserName,PhoneNo,EnterDate,Address,City,PinCode;
    RadioButton R1,R2;
    RadioGroup G1;
    FirebaseFirestore fstore;
    String TAG = "Sanitize";

    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanitize);

        UserName = findViewById(R.id.editTextTextPersonName3);
        PhoneNo =  findViewById(R.id.editTextNumberDecimal);
        EnterDate =findViewById(R.id.editTextDate);
        Address =  findViewById(R.id.editTextTextPostalAddress);
        City =     findViewById(R.id.editTextTextPersonName);
        PinCode =  findViewById(R.id.editTextNumber);
        fstore =   FirebaseFirestore.getInstance();

        R1=findViewById(R.id.radio1);
        R2=findViewById(R.id.radio2);

        G1 = (RadioGroup) findViewById(R.id.G1);

        py = findViewById(R.id.py);
        String sAmount = "100";
        final int amount = Math.round(Float.parseFloat(sAmount)*100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }

        py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioButtonID = G1.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) G1.findViewById(radioButtonID);
                String selectedText = (String) radioButton.getText();

                if (selectedText.equals("Online")) {

                    Checkout checkout = new Checkout();

                    checkout.setKeyID("rzp_test_7S6EXXnFlAqdb8");

                    JSONObject object = new JSONObject();
                    try {
                        object.put("name", "Tirth Shah");
                        object.put("description", "Test payment");
                        object.put("theme.color", "#0093DD");
                        object.put("currency", "INR");
                        object.put("amount", amount);
                        object.put("prefill.contact", "9081079695");
                        object.put("prefill.email", "tirthshsh2123@gmail.com");
                        checkout.open(Sanitize.this, object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                    {
                        String phone = PhoneNo.getText().toString().trim();
                        String full = "Your Request For Sanitization Is Done.." +phone;


                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone, null, full, null, null);
                        Log.d("message", "sms:msg forword ");



                        String name = UserName.getText().toString().trim();
                        String phoneno = PhoneNo.getText().toString().trim();
                        String date = EnterDate.getText().toString().trim();
                        String address = Address.getText().toString().trim();
                        String city = City.getText().toString().trim();
                        String pincode = PinCode.getText().toString().trim();

                        Map<String, Object> user = new HashMap<>();

                        user.put("fName",name);
                        user.put("phoneno",phoneno);
                        user.put("date",date);
                        user.put("address",address);
                        user.put("city",city);
                        user.put("pincode",pincode);

                        fstore.collection("users")
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                        startActivity(new Intent(getApplicationContext(),Home_Page.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "Error adding document", e);
                                    }
                                });

                    }
                    catch (Exception e)
                    {

                        e.printStackTrace();

                    }


            }
            }

        });
    }


    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext()
                ,s,Toast.LENGTH_SHORT).show();


    }
}