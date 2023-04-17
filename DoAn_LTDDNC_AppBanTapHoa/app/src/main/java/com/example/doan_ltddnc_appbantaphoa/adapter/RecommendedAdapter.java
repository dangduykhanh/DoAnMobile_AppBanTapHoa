package com.example.doan_ltddnc_appbantaphoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_ltddnc_appbantaphoa.Model.Recommended;
import com.example.doan_ltddnc_appbantaphoa.R;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {
    List<Recommended> recommendedList;
    Context context;


    public RecommendedAdapter(List<Recommended> recommendedList, Context context) {
        this.recommendedList = recommendedList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommened ,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recommended recommended =recommendedList.get(position) ;
        Glide.with(context).load(recommended.getImg_url()).into(holder.imgRec);
        holder.tvDes_rec.setText(recommended.getDescription());
        holder.tvName_rec.setText(recommended.getName());
        holder.tvRate_rec.setText(recommended.getRating());

    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName_rec ,tvDes_rec ,tvRate_rec ;
        ImageView imgRec ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             tvName_rec =itemView.findViewById(R.id.tv_rec_name);
             tvDes_rec =itemView.findViewById(R.id.tv_rec_des);
             tvRate_rec =itemView.findViewById(R.id.tv_rating_dec);
             imgRec =itemView.findViewById(R.id.img_rec);
        }
    }
}
