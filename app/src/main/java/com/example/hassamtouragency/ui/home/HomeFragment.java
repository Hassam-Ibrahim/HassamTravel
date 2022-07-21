package com.example.hassamtouragency.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.hassamtouragency.BookPackage;
import com.example.hassamtouragency.NearBy;
import com.example.hassamtouragency.R;
import com.example.hassamtouragency.TopPlaces;
import com.example.hassamtouragency.WorldWidePlace;
import com.example.hassamtouragency.adapter.TopLocationAdapter;
import com.example.hassamtouragency.model.TopLocation;
import com.example.hassamtouragency.settings.VolleyConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    FirebaseAuth mAuth;
    String TAG = "tag";
    RecyclerView home_recy;
    CardView tour_pack,top_place,world_tour,near_by;
    private List<TopLocation> toplocationlist = new ArrayList<>();
    SliderLayout imagelist_sl;
    String imageJsonApi = "https://techcrunch.com/wp-json/wp/v2/posts?per_page=10";
    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        home_recy = (RecyclerView) root.findViewById(R.id.home_recy);
        tour_pack = (CardView) root.findViewById(R.id.tour_pack);
        world_tour = (CardView) root.findViewById(R.id.world_tour);
        near_by = (CardView) root.findViewById(R.id.near_by);
        top_place = (CardView) root.findViewById(R.id.top_place);
        imagelist_sl = (SliderLayout) root.findViewById(R.id.imagelist_sl);
        tour_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BookPackage.class));
            }
        });

        world_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorldWidePlace.class));
            }
        });

        near_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NearBy.class));
            }
        });

        top_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TopPlaces.class));
            }
        });
        getImageList();
        TopLocationList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        home_recy.setLayoutManager(linearLayoutManager);
        home_recy.setHasFixedSize(true);
        mAuth = FirebaseAuth.getInstance();
        return root;
    }


    private void TopLocationList() {
        DatabaseReference familyListReference = FirebaseDatabase.getInstance().getReference().child("toplocation");
        familyListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = (String) ds.getKey();
                    DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference().child("toplocation").child(key);
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
                            TopLocation topLocation = new TopLocation(description,img_url,location,name,3.0,short_des, lat, lng);
                            toplocationlist.add(topLocation);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "Read failed");
                        }
                    });
                }
                TopLocationAdapter adapter = new TopLocationAdapter(toplocationlist);
                home_recy.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Read failed");
            }
        });
    }

    private void getImageList() {
        final ArrayList arraylist = new ArrayList<HashMap<String, String>>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, imageJsonApi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray DATA = new JSONArray(response);
                            JSONObject jsonObject;
                            for(int i=0;i<DATA.length();i++){
                                jsonObject = DATA.getJSONObject(i);
                                String urltoimage = jsonObject.getString("jetpack_featured_media_url");
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(String.valueOf(i), urltoimage);
                                arraylist.add(map);
                                for(String name : map.keySet()){
                                    DefaultSliderView textSliderView = new DefaultSliderView (getContext());
                                    textSliderView.description(name);
                                    textSliderView.image(map.get(name));
                                    textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                                    imagelist_sl.addSlider(textSliderView);
                                }
                            }
                            imagelist_sl.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            imagelist_sl.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            imagelist_sl.setCustomAnimation(new DescriptionAnimation());
                            imagelist_sl.setDuration(3000);
                            imagelist_sl.setMinimumWidth(300);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = null;
                        message = "Cannot connect to Internet...Please check your connection!";
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }) {
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        VolleyConfig.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


}