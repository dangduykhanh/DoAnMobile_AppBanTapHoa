package com.example.doan_ltddnc_appbantaphoa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.doan_ltddnc_appbantaphoa.Model.ViewAllProduct;
import com.example.doan_ltddnc_appbantaphoa.adapter.ViewPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class DetailedActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    ImageView imgDetail, imgPlus, imgMinus;
    TextView tvTittle, tvPrice_1, tvPrice, tvRate, tv1, tvQuantity;
    Toolbar toolbar;
    ViewAllProduct viewAllProduct = null;
    Button btnAddcart;
    int totalQuantity = 1;
    int totalPrice = 0;
    FirebaseAuth auth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewPager2);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        imgDetail = findViewById(R.id.img_detail);
        imgMinus = findViewById(R.id.img_minus);
        imgPlus = findViewById(R.id.img_plus);
        tvTittle = findViewById(R.id.tvTittle);
        tvPrice = findViewById(R.id.tv_Price_detail);
        tvPrice_1 = findViewById(R.id.tv_Price1_detail);
        tvRate = findViewById(R.id.tvRate_detail);
        toolbar = findViewById(R.id.toolbar);
        btnAddcart = findViewById(R.id.btnAddcart);
        tvQuantity = findViewById(R.id.tvTotalQuantity);
        tv1 = findViewById(R.id.tv1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    tvQuantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllProduct.getPrice() * totalQuantity;
                }
            }
        });
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity >1) {
                    totalQuantity--;
                    tvQuantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllProduct.getPrice() * totalQuantity;
                }
            }
        });
        //Add cart
        btnAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addToCart();
            }
        });
        //format
        Locale locale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        Intent intent = getIntent();
        final Object object = intent.getSerializableExtra("detail");
        if (object instanceof ViewAllProduct) {
            viewAllProduct = (ViewAllProduct) object;
        }
        if (viewAllProduct != null) {
            Glide.with(this).load(viewAllProduct.getImg_url()).into(imgDetail);
            tvTittle.setText(viewAllProduct.getName());
            tvRate.setText(viewAllProduct.getRating());
            totalPrice=viewAllProduct.getPrice()*totalQuantity;

            if (viewAllProduct.getPrice_1() == 0) {
                tvPrice.setText(numberFormat.format(viewAllProduct.getPrice()) + "đ");
                tvPrice_1.setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
            } else {
                tvPrice.setText(numberFormat.format(viewAllProduct.getPrice()) + "đ");
                tvPrice_1.setText(numberFormat.format(viewAllProduct.getPrice_1()) + "đ");
            }
        } else {
            return;
        }
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Mô tả");
                        break;
                    case 1:
                        tab.setText("Xuất xứ");
                        break;
                }
            }
        }).attach();
    }
    private void addToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", viewAllProduct.getName());
        cartMap.put("productPrice", viewAllProduct.getPrice());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity", totalQuantity);
        cartMap.put("totalPrice", totalPrice);
        cartMap.put("img_url",viewAllProduct.getImg_url());
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.ShowDialog("Thêm giỏ hàng");

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isComplete()) {
                            loadingDialog.HideDialog();
                            Toast.makeText(DetailedActivity.this, "Đã thêm giỏ hàng", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(DetailedActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true ;
    }
}