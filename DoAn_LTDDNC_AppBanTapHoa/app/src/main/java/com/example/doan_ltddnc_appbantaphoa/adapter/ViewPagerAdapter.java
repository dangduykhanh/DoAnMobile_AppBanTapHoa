package com.example.doan_ltddnc_appbantaphoa.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doan_ltddnc_appbantaphoa.fragment_tablayout.descriptionFragment;
import com.example.doan_ltddnc_appbantaphoa.fragment_tablayout.description_source_Fragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new descriptionFragment();
            case 1: return new description_source_Fragment();
            default: return new descriptionFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
