package com.example.doan_ltddnc_appbantaphoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_ltddnc_appbantaphoa.Model.MyCart;
import com.example.doan_ltddnc_appbantaphoa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    List<MyCart> cartList ;
    Context context ;
   /* int totalPrice =0;*/
    private OnCartItemClickListener mListener ;
    FirebaseFirestore db ;
    FirebaseAuth auth ;

    public MyCartAdapter(List<MyCart> cartList, Context context, OnCartItemClickListener mListener) {
        this.cartList = cartList;
        this.context = context;
        this.mListener = mListener;
        db=FirebaseFirestore.getInstance();
        auth =FirebaseAuth.getInstance();
    }


    public interface OnCartItemClickListener{
        void OnCartItemClick (MyCart myCart);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyCart myCart = cartList.get(position);
        Locale locale =new Locale("vi","VN");
        NumberFormat numberFormat =NumberFormat.getNumberInstance(locale);
        Glide.with(context).load(myCart.getImg_url()).into(holder.imgProduct);
        holder.tvProductName.setText(myCart.getProductName());
        holder.tvPrice.setText("Đơn giá: "+numberFormat.format(myCart.getProductPrice())+"đ");
        holder.tvtotalQuantity.setText("Số lượng: "+myCart.getTotalQuantity());
        holder.tvtotalPrice.setText(numberFormat.format(myCart.getTotalPrice())+"đ");
        holder.tvCurrentDate.setText("Ngày: "+myCart.getCurrentDate());
        holder.tvCurrentTime.setText(" "+myCart.getCurrentTime());
        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnCartItemClick(myCart);
            }
        });
        //pass total aout to mycart fragment

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName ,tvtotalQuantity,tvtotalPrice,tvPrice ,tvCurrentDate ,tvCurrentTime;
        ImageButton imgRemove ;
        ImageView imgProduct ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName =itemView.findViewById(R.id.tvProductName_cart);
            tvtotalQuantity =itemView.findViewById(R.id.tvTotalQuantity_Cart);
            tvtotalPrice =itemView.findViewById(R.id.tvtotalPrice_cart);
            tvPrice =itemView.findViewById(R.id.tvPrice_cart);
            tvCurrentDate =itemView.findViewById(R.id.tvDate);
            tvCurrentTime=itemView.findViewById(R.id.tvTime);
            imgRemove=itemView.findViewById(R.id.imgbtn_delete);
            imgProduct=itemView.findViewById(R.id.img_cart);

        }
    }

}

