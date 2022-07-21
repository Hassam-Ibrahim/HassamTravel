package com.example.hassamtouragency;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class NearBy extends AppCompatActivity implements OnMapReadyCallback, LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    FirebaseAuth mAuth;
    LocationManager locationManager;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private static final int REQUEST_LOCATION = 1;
    double mycurrentlat = 0.0;
    double mycurrentlong = 0.0;
    String TAG = "tag";
    Button searchLocation;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by);
        searchLocation = (Button) findViewById(R.id.searchLocation);
        mAuth = FirebaseAuth.getInstance();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager)  getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            if (ActivityCompat.checkSelfPermission(
                    NearBy.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    NearBy.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationGPS != null) {
                    mycurrentlat = locationGPS.getLatitude();
                    mycurrentlong = locationGPS.getLongitude();
                } else {
                    Toast.makeText(NearBy.this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        searchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText locationSearch = (EditText) findViewById(R.id.editText);
                String location = locationSearch.getText().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(NearBy.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    Toast.makeText(getApplicationContext(),address.getLatitude()+" "+address.getLongitude(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        DatabaseReference familyListReference = FirebaseDatabase.getInstance().getReference().child("nearbylocation");
        familyListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = (String) ds.getKey();
                    DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference().child("nearbylocation").child(key);
                    keyReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String place_name = dataSnapshot.child("place_name").getValue(String.class);
                            String description = dataSnapshot.child("description").getValue(String.class);
                            String img_url = dataSnapshot.child("img_url").getValue(String.class);
                            Double lat = (Double)dataSnapshot.child("lat").getValue();
                            Double lng = (Double) dataSnapshot.child("lng").getValue();
                            Log.d("Lat Long", "LatLong "+lng+"-"+lat);
                            LatLng Brisbane = new LatLng(lat,lng);
                            mMap.addMarker(new MarkerOptions().position(Brisbane).title(place_name).icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("travelmap",82,74))));
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "Read failed");
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Read failed");
            }
        });
        LatLng MycurrentLocation = new LatLng(mycurrentlat, mycurrentlong);
        mMap.addMarker(new MarkerOptions().position(MycurrentLocation).title("I am here").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("pin",82,74))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mycurrentlat, mycurrentlong), 6.0f));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    public Bitmap resizeBitmap(String drawableName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(drawableName, "drawable", getPackageName()));
        return Bitmap.createScaledBitmap(imageBitmap, 82, 74, false);
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}