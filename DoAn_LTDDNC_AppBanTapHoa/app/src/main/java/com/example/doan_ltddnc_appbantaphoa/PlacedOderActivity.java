package com.example.doan_ltddnc_appbantaphoa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.doan_ltddnc_appbantaphoa.Model.MyCart;
import com.example.doan_ltddnc_appbantaphoa.Model.MyOrder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlacedOderActivity extends AppCompatActivity {
    FirebaseAuth  auth ;
    FirebaseFirestore db ;
    AppCompatButton btnFinish ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_oder);
        btnFinish =findViewById(R.id.btnFinish);
        auth =FirebaseAuth.getInstance();
        db =FirebaseFirestore.getInstance();

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlacedOderActivity.this ,MainActivity.class));
                finish();

            }
        });

        List<MyCart> cartList = (ArrayList<MyCart>) getIntent().getSerializableExtra("itemcartlist");

        String address =getIntent().getStringExtra("address");
        String totalamout =getIntent().getStringExtra("totalamount");

        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        MyOrder myOrder =new MyOrder(totalamout,saveCurrentDate ,saveCurrentTime,address ,cartList,0) ;
        LoadingDialog loadingDialog =new LoadingDialog(PlacedOderActivity.this) ;
        loadingDialog.ShowDialog("Thêm giỏ hàng");



        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").add(myOrder).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isComplete()){


                            for(MyCart myCart :cartList){
                                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                        .collection("MyCart").document(myCart.getDocumentId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                            }
                                        });
                            }
                            loadingDialog.HideDialog();
                            Toast.makeText(PlacedOderActivity.this, "Hoàn tất đặt hàng", Toast.LENGTH_SHORT).show();


                        }else {
                            Toast.makeText(PlacedOderActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });




      /*  if(cartList!=null && cartList.size() >0){
            for(MyCart myCart :cartList){
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", myCart.getProductName());
                cartMap.put("productPrice", myCart.getProductPrice());
                cartMap.put("currentDate",  saveCurrentDate);
                cartMap.put("currentTime", saveCurrentTime);
                cartMap.put("totalQuantity",myCart.getTotalQuantity());
                cartMap.put("totalPrice", myCart.getTotalPrice());
                cartMap.put("address",address);

                LoadingDialog loadingDialog = new LoadingDialog(this);
                loadingDialog.ShowDialog("Thêm giỏ hàng");

                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isComplete()) {
                                    loadingDialog.HideDialog();
                                    Toast.makeText(PlacedOderActivity.this, "Hoàn tất đặt hàng", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        }*/

    }
}