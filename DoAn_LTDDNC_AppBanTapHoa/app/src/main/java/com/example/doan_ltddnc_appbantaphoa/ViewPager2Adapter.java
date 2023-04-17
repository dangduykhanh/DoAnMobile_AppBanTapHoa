package com.example.doan_ltddnc_appbantaphoa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_ltddnc_appbantaphoa.Model.PlashSreenItem;

import java.util.List;

public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.PlashViewHolder> {
    private List<PlashSreenItem> listlashSreenItem;

    public ViewPager2Adapter(List<PlashSreenItem> listlashSreenItem) {
        this.listlashSreenItem = listlashSreenItem;
    }

    @NonNull
    @Override
    public PlashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new PlashViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_viewpager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlashViewHolder holder, int position) {
        holder.setOnPlashScreenData(listlashSreenItem.get(position));

    }

    @Override
    public int getItemCount() {
        return listlashSreenItem.size();
    }

    public class PlashViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView tvTittle;

        public PlashViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgHinh);
            tvTittle = itemView.findViewById(R.id.tvTittle);
        }

        void setOnPlashScreenData(PlashSreenItem plashSreenItem) {
            imgLogo.setImageResource(plashSreenItem.getImage());
            tvTittle.setText(plashSreenItem.getTittle());

        }
    }
}
