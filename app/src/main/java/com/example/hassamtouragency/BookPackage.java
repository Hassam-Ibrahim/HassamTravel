package com.example.hassamtouragency;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.hassamtouragency.adapter.AllTopLocationAdapter;
import com.example.hassamtouragency.adapter.PackageAdapter;
import com.example.hassamtouragency.model.AllTopLocation;
import com.example.hassamtouragency.model.PackageModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BookPackage extends AppCompatActivity {

    RecyclerView package_recy;
    private List<PackageModel> packagelist = new ArrayList<>();
    PackageAdapter adapter;
    PackageModel packagemodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_package);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Packages");
        package_recy = (RecyclerView) findViewById(R.id.package_recy);

        DatabaseReference tpListReference = FirebaseDatabase.getInstance().getReference().child("packages");
        tpListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = (String) ds.getKey();
                    DatabaseReference alltopkeyRef= FirebaseDatabase.getInstance().getReference().child("packages").child(key);
                    alltopkeyRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                            String package_des = dataSnapshot.child("package_des").getValue(String.class);
                            String package_img = dataSnapshot.child("package_img").getValue(String.class);
                            String package_name = dataSnapshot.child("package_name").getValue(String.class);
                            String package_price = dataSnapshot.child("package_price").getValue(String.class);
                            //Log.d("checkdata",package_name);
                            packagemodel = new PackageModel(package_des,package_img,package_name,package_price);
                            packagelist.add(packagemodel);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("TAG", "Read failed");
                        }
                    });
                }
                adapter = new PackageAdapter(packagelist);
                package_recy.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "Read failed");
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        package_recy.setLayoutManager(linearLayoutManager);
        package_recy.setHasFixedSize(true);
    }
}