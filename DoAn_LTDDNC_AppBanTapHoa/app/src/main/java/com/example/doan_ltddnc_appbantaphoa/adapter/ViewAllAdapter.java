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
import com.example.doan_ltddnc_appbantaphoa.DetailedActivity;
import com.example.doan_ltddnc_appbantaphoa.Model.ViewAllProduct;
import com.example.doan_ltddnc_appbantaphoa.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    Context context ;
    List<ViewAllProduct>viewAllProductList ;

    public ViewAllAdapter(Context context, List<ViewAllProduct> viewAllProductList) {
        this.context = context;
        this.viewAllProductList = viewAllProductList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_all,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewAllProduct viewAllProduct =viewAllProductList.get(position);
        holder.tvName.setText(viewAllProduct.getName());
        holder.tvRate.setText(viewAllProduct.getRating());
        Glide.with(context).load(viewAllProduct.getImg_url()).into(holder.imgAllProduct);

        //format currency
        Locale locale =new Locale("vi","VN");
        NumberFormat numberFormat =NumberFormat.getNumberInstance(locale);
        if (viewAllProduct.getPrice_1()==0){
            holder.tvPrice_1.setVisibility(View.GONE);
            holder.tvPrice.setText(numberFormat.format(viewAllProduct.getPrice())+"đ");
        }else {
            holder.tvPrice_1.setText(numberFormat.format(viewAllProduct.getPrice_1())+"đ");
            holder.tvPrice.setText(numberFormat.format(viewAllProduct.getPrice())+"đ");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(context , DetailedActivity.class);
                intent.putExtra("detail",viewAllProduct);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return viewAllProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName ,tvDes,tvRate,tvPrice_1,tvPrice;
        ImageView imgAllProduct ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName =itemView.findViewById(R.id.tvAllProductName);
            tvRate =itemView.findViewById(R.id.tv_rating_all_product);
            tvRate =itemView.findViewById(R.id.tv_rating_all_product);
            tvPrice_1 =itemView.findViewById(R.id.price_1_allProduct);
            tvPrice =itemView.findViewById(R.id.price_allProduct);
            imgAllProduct=itemView.findViewById(R.id.imgAllProduct);
        }
    }
}
