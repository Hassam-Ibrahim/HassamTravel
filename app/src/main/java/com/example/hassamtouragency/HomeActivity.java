package com.example.hassamtouragency;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.hassamtouragency.settings.SharedPreference;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    TextView uname,emailid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View hView =  navigationView.getHeaderView(0);
        uname = (TextView) hView.findViewById(R.id.uname);
        emailid = (TextView) hView.findViewById(R.id.emailid);
        if (SharedPreference.getDataLogin(this)) {
            if (SharedPreference.getDataAs(this).equals("user")) {
                Log.d("username",SharedPreference.getDataFullname(getApplicationContext()));
                uname.setText(SharedPreference.getDataFullname(getApplicationContext()));
                emailid.setText(SharedPreference.getDataEmail(getApplicationContext()));
            }
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(HomeActivity.this, BookPackage.class));
                        return true;
                    case R.id.nav_gallery:
                        startActivity(new Intent(HomeActivity.this, TopPlaces.class));
                        return true;
                    case R.id.nav_slideshow:
                        startActivity(new Intent(HomeActivity.this, WorldWidePlace.class));
                        return true;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        SharedPreference.clearData(HomeActivity.this);
                        startActivity(new Intent(HomeActivity.this, SignIn.class));
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}