package com.example.doan_ltddnc_appbantaphoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_ltddnc_appbantaphoa.Model.NavCategory;
import com.example.doan_ltddnc_appbantaphoa.NavCategoryActivity;
import com.example.doan_ltddnc_appbantaphoa.R;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder> {
    Context context;
    List<NavCategory> navCategoryList ;


    public NavCategoryAdapter(Context context, List<NavCategory> navCategoryList) {
        this.context = context;
        this.navCategoryList = navCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_nav,parent,false));

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NavCategory navCategory = navCategoryList.get(position);
        Glide.with(context).load(navCategory.getImg_url()).into(holder.imgNavCat);
        holder.tvNavDesCat.setText(navCategory.getDescription());
        holder.tvNavNameCat.setText(navCategory.getName());
        holder.tvNavDisCat.setText("Giảm giá: "+navCategory.getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context , NavCategoryActivity.class);
                intent.putExtra("type",navCategory.getType());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return navCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNavNameCat,tvNavDesCat ,tvNavDisCat;
        ImageView imgNavCat ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNavDesCat = itemView.findViewById(R.id.nav_cat_des);
            tvNavNameCat=itemView.findViewById(R.id.nav_cat_name);
            tvNavDisCat =itemView.findViewById(R.id.nav_cat_discount);
            imgNavCat = itemView.findViewById(R.id.cat_nav_img);
        }
    }
}
