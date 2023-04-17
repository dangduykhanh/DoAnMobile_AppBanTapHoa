package com.example.doan_ltddnc_appbantaphoa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_ltddnc_appbantaphoa.Model.ViewAllProduct;
import com.example.doan_ltddnc_appbantaphoa.adapter.ViewAllAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    RecyclerView rcAllproduct ;
    FirebaseFirestore db ;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllProduct> viewAllProductList ;
    Toolbar toolbar ;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        toolbar =findViewById(R.id.toolbar);
        ImageView imgnull =findViewById(R.id.imgnull);
        TextView tvnull =findViewById(R.id.tvnull);
        Intent intent =getIntent();
        type=intent.getStringExtra("type");

        viewAllProductList =new ArrayList<>();
        rcAllproduct =findViewById(R.id.rcViewAll);
        db =FirebaseFirestore.getInstance();
        viewAllAdapter =new ViewAllAdapter(this ,viewAllProductList);
        rcAllproduct.setAdapter(viewAllAdapter);
        rcAllproduct.setLayoutManager(new GridLayoutManager(this ,2,GridLayoutManager.VERTICAL,false));

        if(type !=null ){
            switch (type){
                case "meat":toolbar.setTitle("Thịt các loại");break;
                case "vegetable":toolbar.setTitle("Rau củ tươi");break;
                case "fruit":toolbar.setTitle("Trái cây");break;
            }
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            db.collection("AllProduct").whereEqualTo("type",type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isComplete() && task.getResult().getDocuments().size()>0){
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                            ViewAllProduct viewAllProduct =documentSnapshot.toObject(ViewAllProduct.class);
                            viewAllProductList.add(viewAllProduct);
                        }
                        viewAllAdapter.notifyDataSetChanged();

                    }else{
                        tvnull.setVisibility(View.VISIBLE);
                        imgnull.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        else {

            return;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_filter,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id ==R.id.sort_price){
           Collections.sort(viewAllProductList);
           viewAllAdapter.notifyDataSetChanged();

        }else if(id ==R.id.sort_dropprice){
            viewAllProductList =new ArrayList<>();
            db.collection("AllProduct").whereEqualTo("type",type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isComplete() && task.getResult().getDocuments().size()>0){
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                            ViewAllProduct viewAllProduct =documentSnapshot.toObject(ViewAllProduct.class);
                            if(viewAllProduct.getPrice_1() >0){
                                viewAllProductList.add(viewAllProduct);
                            }
                            viewAllAdapter.notifyDataSetChanged();
                        }


                    }
                }
            });

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true ;
    }
}