package com.example.hassamtouragency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingForm extends AppCompatActivity {

    TextInputEditText package_name,package_price,name,phone,email,message;
    DatabaseReference databaseReference;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Package Booking");

        Intent intent = getIntent();
        String in_package_name = intent.getStringExtra("package_name");
        String in_package_price = intent.getStringExtra("package_price");

        package_name= (TextInputEditText) findViewById(R.id.package_name);
        package_price= (TextInputEditText) findViewById(R.id.package_price);
        name= (TextInputEditText) findViewById(R.id.name);
        phone= (TextInputEditText) findViewById(R.id.phone);
        email= (TextInputEditText) findViewById(R.id.email);
        message= (TextInputEditText) findViewById(R.id.message);
        submit= (Button) findViewById(R.id.submit);

        package_name.setText(in_package_name);
        package_price.setText(in_package_price);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("booking");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_phone = phone.getText().toString().trim();
                String s_email = email.getText().toString().trim();
                String s_name = name.getText().toString().trim();
                String s_package_name = package_name.getText().toString().trim();
                String s_package_price = package_price.getText().toString().trim();
                String s_message = message.getText().toString().trim();

                if(s_name.isEmpty()){
                    name.setError("Please Enter Your Name");
                    name.requestFocus();
                    return;
                }else if(s_phone.isEmpty()){
                    phone.setError("Please Enter Your Phone Number");
                    phone.requestFocus();
                    return;
                }else if(s_email.isEmpty()){
                    email.setError("Please Enter Email Address");
                    email.requestFocus();
                    return;
                }else{
                    Toast.makeText(getApplicationContext(),"Please wait Your Request is in Queue",Toast.LENGTH_SHORT).show();
                    BookingFormModel booking = new BookingFormModel(s_package_name,s_package_price,s_name,s_email,s_phone,s_message);
                    databaseReference.push().setValue(booking);
                    Toast.makeText(getApplicationContext(),"Your booking has been done we will contact you soon !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}