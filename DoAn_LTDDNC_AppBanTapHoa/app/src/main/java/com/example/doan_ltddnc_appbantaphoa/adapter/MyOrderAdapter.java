package com.example.doan_ltddnc_appbantaphoa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_ltddnc_appbantaphoa.Model.MyOrder;
import com.example.doan_ltddnc_appbantaphoa.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    List<MyOrder> myOrderList ;

    public MyOrderAdapter(List<MyOrder> myOrderList) {
        this.myOrderList = myOrderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_order,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyOrder myOrder = myOrderList.get(position);
        holder.tvOrderId.setText("Mã: "+myOrder.getOrder_ID());
        int countCart =0;
        for(int i =0 ;i<myOrder.getCartList().size() ;i++) {
            countCart+=myOrder.getCartList().get(i).getTotalQuantity();
        }
        holder.tvQuan_order.setText("SL sản phẩm: " + countCart);
        holder.tvDateOrder.setText("Ngày đặt: "+myOrder.getDateOrder()+", "+myOrder.getTimeOrder());
        holder.tvAddress.setText("Địa chỉ: "+myOrder.getAddress());
        holder.tvBillTotal.setText("Thanh toán: "+myOrder.getTotalAmount()+"đ");

        if(myOrder.getStatus() ==0){
            holder.tvStatus.setText("Đang vận chuyển");
        }else {
            holder.tvStatus.setText("Đã nhận hàng");
        }
    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvQuan_order,tvDateOrder ,tvAddress,tvBillTotal,tvStatus ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId =itemView.findViewById(R.id.tvOrderID);
            tvQuan_order =itemView.findViewById(R.id.tvquantity_order);
            tvDateOrder =itemView.findViewById(R.id.tvdate_order);
            tvAddress=itemView.findViewById(R.id.tvAddress_order);
            tvBillTotal =itemView.findViewById(R.id.tv_payorder);
            tvStatus =itemView.findViewById(R.id.tvStatus);
        }
    }
}
