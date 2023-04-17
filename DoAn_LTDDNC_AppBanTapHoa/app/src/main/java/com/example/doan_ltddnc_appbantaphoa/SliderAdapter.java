package com.example.doan_ltddnc_appbantaphoa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.doan_ltddnc_appbantaphoa.Model.Slider;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private List<Slider> sliderList;

    public SliderAdapter(List<Slider> sliderList) {
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout,container,false);
        ImageView banner = view.findViewById(R.id.slider);
        banner.setImageResource(sliderList.get(position).getBanner());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), position+"", Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view, 0);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return sliderList.size();
    }
}
