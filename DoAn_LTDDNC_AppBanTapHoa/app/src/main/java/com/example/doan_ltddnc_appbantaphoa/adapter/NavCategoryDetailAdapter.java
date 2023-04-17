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
import com.example.doan_ltddnc_appbantaphoa.Model.NavCategoryDetailed;
import com.example.doan_ltddnc_appbantaphoa.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class NavCategoryDetailAdapter extends RecyclerView.Adapter<NavCategoryDetailAdapter.ViewHolder> {
    Context context ;
    List<NavCategoryDetailed> navCategoryDetaileds ;

    public NavCategoryDetailAdapter(Context context, List<NavCategoryDetailed> navCategoryDetaileds) {
        this.context = context;
        this.navCategoryDetaileds = navCategoryDetaileds;
    }

    @NonNull
    @Override
    public NavCategoryDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_detail_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavCategoryDetailAdapter.ViewHolder holder, int position) {
        NavCategoryDetailed navCategoryDetailed = navCategoryDetaileds.get(position);

        Glide.with(context).load(navCategoryDetailed.getImg_url()).into(holder.imgProduct);
        holder.tvNameProduct.setText(navCategoryDetailed.getName());
        Locale locale =new Locale("vi","VN");
        NumberFormat numberFormat =NumberFormat.getNumberInstance(locale);
        holder.tvPrice.setText(numberFormat.format(navCategoryDetailed.getPrice())+"đ");
    }

    @Override
    public int getItemCount() {
        return navCategoryDetaileds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvNameProduct,tvPrice ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.img_nav_cartegory_product);
            tvNameProduct=itemView.findViewById(R.id.nav_cat_name_product);
            tvPrice=itemView.findViewById(R.id.nav_cat_price_product);
        }
    }
}
