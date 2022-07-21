package com.example.hassamtouragency.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hassamtouragency.R;
import com.example.hassamtouragency.TopLocationDetails;
import com.example.hassamtouragency.TopLocationMap;
import com.example.hassamtouragency.model.AllTopLocation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllTopLocationAdapter extends RecyclerView.Adapter<AllTopLocationAdapter.MyViewHolder> {
    private List<AllTopLocation> alltoplocationlist;
    private Context mContext;
    private View parent_view;
    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parentLayout;
        TextView place_name,short_des;
        ImageView img_url;
        Button viewmap,explore;
        MyViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            parent_view = view.findViewById(android.R.id.content);
            place_name = (TextView) view.findViewById(R.id.place_name);
            short_des = (TextView) view.findViewById(R.id.short_des);
            img_url = (ImageView) view.findViewById(R.id.img_url);
            viewmap = (Button) view.findViewById(R.id.viewmap);
            explore = (Button) view.findViewById(R.id.explore);
        }
    }

    public AllTopLocationAdapter(List<AllTopLocation> alltoplocationlist) {
        this.alltoplocationlist = alltoplocationlist;
    }

    @NonNull
    @Override
    public AllTopLocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_top_location_item, parent, false);
        return new AllTopLocationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllTopLocationAdapter.MyViewHolder holder, int position) {
        final AllTopLocation topLocation = alltoplocationlist.get(position);
        final String s_name = topLocation.getName();
        final String s_short_des = topLocation.getShort_des();
        final String s_description = topLocation.getDescription();
        final String s_img_url = topLocation.getImg_url();
        final Double d_lat = topLocation.getLat();
        final Double d_lng = topLocation.getLng();
        holder.place_name.setText(s_name);
        holder.short_des.setText(String.valueOf(s_short_des));
        holder.viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TopLocationMap.class);
                intent.putExtra("place_name",s_name);
                intent.putExtra("lat",d_lat);
                intent.putExtra("lng",d_lng);
                view.getContext().startActivity(intent);
                //Toast.makeText(mContext, "Clicked Laugh Vote", Toast.LENGTH_SHORT).show();
            }
        });

        holder.explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TopLocationDetails.class);
                intent.putExtra("place_name",s_name);
                intent.putExtra("lat",d_lat);
                intent.putExtra("lng",d_lng);
                intent.putExtra("description",s_description);
                intent.putExtra("short_des",s_short_des);
                intent.putExtra("img_url",s_img_url);
                view.getContext().startActivity(intent);
                //Toast.makeText(mContext, "Clicked Laugh Vote", Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.with(mContext).load(s_img_url).into(holder.img_url);
    }

    @Override
    public int getItemCount() {
        return alltoplocationlist.size();
    }
}
