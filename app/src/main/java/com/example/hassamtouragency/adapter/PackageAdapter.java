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

import com.example.hassamtouragency.BookingForm;
import com.example.hassamtouragency.R;
import com.example.hassamtouragency.TopLocationDetails;
import com.example.hassamtouragency.TopLocationMap;
import com.example.hassamtouragency.model.PackageModel;
import com.example.hassamtouragency.model.TopLocation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyViewHolder> {
    private List<PackageModel> packagelist;
    private Context mContext;
    private View parent_view;
    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parentLayout;
        TextView package_name,package_des,package_price;
        ImageView p_img_url;
        Button explore;
        MyViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            package_name = (TextView) view.findViewById(R.id.package_name);
            package_price = (TextView) view.findViewById(R.id.package_price);
            package_des = (TextView) view.findViewById(R.id.package_des);
            p_img_url = (ImageView) view.findViewById(R.id.p_img_url);
            explore = (Button) view.findViewById(R.id.explore);
        }
    }

    public PackageAdapter(List<PackageModel> packagelist) {
        this.packagelist = packagelist;
    }

    @NonNull
    @Override
    public PackageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_item, parent, false);
        return new PackageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PackageAdapter.MyViewHolder holder, int position) {
        final PackageModel packages = packagelist.get(position);
        final String s_name = packages.getPackage_name();
        final String s_price = packages.getPackage_price();
        final String s_description = packages.getPackage_des();
        final String s_img_url = packages.getPackage_img();
        holder.package_name.setText(s_name);
        holder.package_des.setText(s_description);
        holder.package_price.setText("£ "+s_price);
        holder.explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BookingForm.class);
                intent.putExtra("package_name",s_name);
                intent.putExtra("package_price",s_price);
                view.getContext().startActivity(intent);
            }
        });

        Picasso.with(mContext).load(s_img_url).into(holder.p_img_url);
    }

    @Override
    public int getItemCount() {
        return packagelist.size();
    }
}
