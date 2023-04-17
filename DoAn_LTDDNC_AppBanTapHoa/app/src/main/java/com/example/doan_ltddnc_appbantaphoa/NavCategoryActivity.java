package com.example.doan_ltddnc_appbantaphoa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_ltddnc_appbantaphoa.Model.NavCategoryDetailed;
import com.example.doan_ltddnc_appbantaphoa.adapter.NavCategoryDetailAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {
    Toolbar toolbar ;
    RecyclerView rcNavCatDetail ;
    NavCategoryDetailAdapter navCategoryDetailAdapter ;
    FirebaseFirestore db ;
    List<NavCategoryDetailed> navCategoryDetailedList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);
        rcNavCatDetail=findViewById(R.id.nav_cat_detail);

        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent =getIntent() ;
        String type =intent.getStringExtra("type");

        navCategoryDetailedList =new ArrayList<>() ;
        navCategoryDetailAdapter =new NavCategoryDetailAdapter(this ,navCategoryDetailedList);
        rcNavCatDetail.setAdapter(navCategoryDetailAdapter);
        rcNavCatDetail.setLayoutManager(new LinearLayoutManager(this ,LinearLayoutManager.VERTICAL ,false));
        db =FirebaseFirestore.getInstance();
        if(type !=null ){
            db.collection("NavCategoryDetailed").whereEqualTo("type",type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                            NavCategoryDetailed navCategoryDetailed =documentSnapshot.toObject(NavCategoryDetailed.class);
                            Log.d("CCC", navCategoryDetailed.toString());
                            navCategoryDetailedList.add(navCategoryDetailed);

                        }
                        navCategoryDetailAdapter.notifyDataSetChanged();
                }
            });
        }
        else {

            return;
        }

    }
}