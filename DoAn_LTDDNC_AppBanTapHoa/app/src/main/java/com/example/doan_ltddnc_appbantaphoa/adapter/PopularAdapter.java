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
import com.bumptech.glide.request.RequestOptions;
import com.example.doan_ltddnc_appbantaphoa.Model.Populars;
import com.example.doan_ltddnc_appbantaphoa.R;
import com.example.doan_ltddnc_appbantaphoa.ViewAllActivity;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private Context context ;
    private List<Populars> popularsList ;

    public PopularAdapter(Context context, List<Populars> popularsList) {
        this.context = context;
        this.popularsList = popularsList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Populars populars = popularsList.get(position);
        Glide.with(context).load(populars.getImg_url()).apply(new RequestOptions().placeholder(R.drawable.popimage)).into(holder.popImage);
        holder.tvname.setText(populars.getName());
        holder.tvdescription.setText(populars.getDescription());
        holder.tvrating.setText(populars.getRating());
        holder.tvdiscount.setText(populars.getDiscount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, ViewAllActivity.class) ;
                intent.putExtra("type",populars.getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return popularsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImage;
        TextView tvname,tvdescription,tvrating,tvdiscount ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popImage =itemView.findViewById(R.id.img_pop);
            tvname=itemView.findViewById(R.id.tv_pop_name);
            tvdescription=itemView.findViewById(R.id.tv_pop_des);
            tvrating=itemView.findViewById(R.id.tv_rating);
            tvdiscount=itemView.findViewById(R.id.tvDiscount);

        }
    }
}
