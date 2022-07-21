package com.example.hassamtouragency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    ImageView mlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlogo =(ImageView) findViewById(R.id.mlogo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(MainActivity.this, SignIn.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, 5000);
    }
}