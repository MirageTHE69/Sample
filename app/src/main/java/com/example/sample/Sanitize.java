package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Sanitize extends AppCompatActivity implements PaymentResultListener {

    Button py;

    RadioButton R1,R2;
    RadioGroup G1;

    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanitize);

        G1 = (RadioGroup) findViewById(R.id.G1);


        py = findViewById(R.id.py);

        String sAmount = "100";

        final int amount = Math.round(Float.parseFloat(sAmount)*100);

        py.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonID = G1.getCheckedRadioButtonId();

                RadioButton radioButton = (RadioButton) G1.findViewById(radioButtonID);

                String selectedText = (String) radioButton.getText();

                if (selectedText.equals("Online"))
                {



                Checkout checkout = new Checkout();

                 checkout.setKeyID("rzp_test_7S6EXXnFlAqdb8");

                JSONObject object = new JSONObject();
                try
                {
                    object.put("name","Tirth Shah");
                    object.put("description","Test payment");
                    object.put("theme.color","#0093DD");
                    object.put("currency","INR");
                    object.put("amount",amount);
                    object.put("prefill.contact","9081079695");
                    object.put("prefill.email","tirthshsh2123@gmail.com");
                    checkout.open(Sanitize.this,object);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
                else
                {

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