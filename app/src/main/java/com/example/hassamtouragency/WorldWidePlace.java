package com.example.hassamtouragency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.hassamtouragency.adapter.WorldWideAdapter;
import com.example.hassamtouragency.model.WorldWideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorldWidePlace extends AppCompatActivity {

    RecyclerView worldwide_recy;
    private List<WorldWideModel> wwlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_wide_place);
        worldwide_recy = (RecyclerView) findViewById(R.id.worldwide_recy);
        worldWideList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        worldwide_recy.setLayoutManager(linearLayoutManager);
        worldwide_recy.setHasFixedSize(true);
    }

    private void worldWideList() {
        DatabaseReference wwpListReference = FirebaseDatabase.getInstance().getReference().child("worldwidelocation");
        wwpListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = (String) ds.getKey();
                    DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference().child("worldwidelocation").child(key);
                    keyReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue(String.class);
                            String description = dataSnapshot.child("description").getValue(String.class);
                            String short_des = dataSnapshot.child("short_des").getValue(String.class);
                            String location = dataSnapshot.child("location").getValue(String.class);
                            String img_url = dataSnapshot.child("img_url").getValue(String.class);
                            Double lat = (Double) dataSnapshot.child("lat").getValue();
                            Double lng = (Double) dataSnapshot.child("lng").getValue();
                            Log.d("checkdata",name);
                            WorldWideModel wwLocation = new WorldWideModel(description,img_url,location,name,3.0,short_des, lat, lng);
                            wwlist.add(wwLocation);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("TAG", "Read failed");
                        }
                    });
                }
                WorldWideAdapter adapter = new WorldWideAdapter(wwlist);
                adapter.notifyDataSetChanged();
                worldwide_recy.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "Read failed");
            }
        });
    }
}