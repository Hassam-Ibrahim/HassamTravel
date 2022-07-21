package com.example.hassamtouragency;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.hassamtouragency.adapter.AllTopLocationAdapter;
import com.example.hassamtouragency.model.AllTopLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TopPlaces extends AppCompatActivity {

    RecyclerView toplocation_recy;
    private List<AllTopLocation> toplocationlist = new ArrayList<>();
    AllTopLocationAdapter adapter;
    AllTopLocation topLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_places);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Top Place");
        toplocation_recy = (RecyclerView) findViewById(R.id.toplocation_recy);

        DatabaseReference tpListReference = FirebaseDatabase.getInstance().getReference().child("alltoplocation");
        tpListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = (String) ds.getKey();
                    DatabaseReference alltopkeyRef= FirebaseDatabase.getInstance().getReference().child("alltoplocation").child(key);
                    alltopkeyRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue(String.class);
                            String description = dataSnapshot.child("description").getValue(String.class);
                            String short_des = dataSnapshot.child("short_des").getValue(String.class);
                            String location = dataSnapshot.child("location").getValue(String.class);
                            String img_url = dataSnapshot.child("img_url").getValue(String.class);
                            Double lat = (Double) dataSnapshot.child("lat").getValue();
                            Double lng = (Double) dataSnapshot.child("lng").getValue();
                            Log.d("checkdata",name);
                            topLocation = new AllTopLocation(description,img_url,location,name,3.0,short_des, lat, lng);
                            toplocationlist.add(topLocation);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("TAG", "Read failed");
                        }
                    });
                }
                adapter = new AllTopLocationAdapter(toplocationlist);
                toplocation_recy.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "Read failed");
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        toplocation_recy.setLayoutManager(linearLayoutManager);
        toplocation_recy.setHasFixedSize(true);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }
}