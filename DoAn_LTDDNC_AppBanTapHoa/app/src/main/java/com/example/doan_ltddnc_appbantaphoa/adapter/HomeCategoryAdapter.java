package com.example.doan_ltddnc_appbantaphoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_ltddnc_appbantaphoa.Model.HomeCategory;
import com.example.doan_ltddnc_appbantaphoa.R;
import com.example.doan_ltddnc_appbantaphoa.ViewAllActivity;

import java.util.List;

public class HomeCategoryAdapter  extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    Context context ;
    List<HomeCategory> homeCategoryList ;

    public HomeCategoryAdapter(Context context, List<HomeCategory> homeCategoryList) {
        this.context = context;
        this.homeCategoryList = homeCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeCategory homeCategory = homeCategoryList.get(position);
        holder.tvNameCategory.setText(homeCategory.getName());
        Glide.with(context).load(homeCategory.getImg_url()).into(holder.imgHomeCategory);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",homeCategory.getType());
                Log.d("ACD", homeCategory.getType());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return homeCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameCategory ;
        ImageView imgHomeCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCategory=itemView.findViewById(R.id.tv_name_category);
            imgHomeCategory=itemView.findViewById(R.id.img_home_category);
        }
    }
}
