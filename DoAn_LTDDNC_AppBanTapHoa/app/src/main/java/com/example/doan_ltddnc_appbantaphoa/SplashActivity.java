package com.example.doan_ltddnc_appbantaphoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.doan_ltddnc_appbantaphoa.Model.PlashSreenItem;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    ViewPager2Adapter adapter ;
    ArrayList<PlashSreenItem> plashSreenItems ;
    ViewPager2 viewPager2 ;
    LinearLayout layoutIndicator ;
    MaterialButton btnGetStart;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewPager2  =findViewById(R.id.viewPager);
        layoutIndicator =findViewById(R.id.line_indicator);
        btnGetStart =findViewById(R.id.btnGetStart);
        auth=FirebaseAuth.getInstance() ;
        plashSreenItems =new ArrayList<>() ;
        plashSreenItems.add(new PlashSreenItem(R.drawable.logo_1,"Empowering Artisans, Farmers & Micro Business"));
        plashSreenItems.add(new PlashSreenItem(R.drawable.logo_4,"Connecting NGOs, Social Enterprises with Communities"));
        plashSreenItems.add(new PlashSreenItem(R.drawable.logo_3,"Donate, Invest & Support infrastructure projects"));
        adapter =new ViewPager2Adapter(plashSreenItems);
        viewPager2.setAdapter(adapter);
        ImageView[] indicators =new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8 ,0,8,0);
        for(int i =0 ;i< indicators.length;i++){
            indicators[i]=new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.shape_indicator_unactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutIndicator.addView(indicators[i]);
        }
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIncaditor(position);
            }
        });
        btnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager2.getCurrentItem() +1 < adapter.getItemCount()){
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
                }
                else {
                    startActivity(new Intent(SplashActivity.this ,LoginActivity.class));
                    finish();
                }
            }
        });
    }
    private void setCurrentIncaditor(int index){
        int count =layoutIndicator.getChildCount();
        for(int i=0 ;i<count;i++){
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.shape_indicator_active));
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.shape_indicator_unactive));
            }
        }
        if(index ==adapter.getItemCount() -1){
            btnGetStart.setText("Bắt đầu");
        }else{
            btnGetStart.setText("Tiếp tục");
        }
    }
    @Override
    protected void onStart() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser !=null){
            startActivity(new Intent(SplashActivity.this ,MainActivity.class));
        }
        super.onStart();
    }
}