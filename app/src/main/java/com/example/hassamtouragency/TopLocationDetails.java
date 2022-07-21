package com.example.hassamtouragency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TopLocationDetails extends AppCompatActivity {

    private String placename,description,shortdes,imgurl;
    private Double lat,lng;
    ImageView iv_img_url;
    TextView tv_place_name,tv_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_location_details);
        tv_place_name = (TextView) findViewById(R.id.tv_place_name);
        tv_description = (TextView) findViewById(R.id.tv_description);
        iv_img_url = (ImageView) findViewById(R.id.iv_img_url);

        Intent intent = getIntent();
        placename = intent.getStringExtra("place_name");
        description = intent.getStringExtra("description");
        shortdes = intent.getStringExtra("short_des");
        imgurl = intent.getStringExtra("img_url");
        lat = intent.getDoubleExtra("lat",0.0);
        lng = intent.getDoubleExtra("lng",0.0);
        tv_place_name.setText(placename);
        tv_description.setText(description);
        Picasso.with(getApplicationContext()).load(imgurl).into(iv_img_url);
    }
}