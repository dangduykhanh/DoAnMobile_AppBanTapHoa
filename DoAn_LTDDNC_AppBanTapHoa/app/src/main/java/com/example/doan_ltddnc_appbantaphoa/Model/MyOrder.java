package com.example.doan_ltddnc_appbantaphoa.Model;

import java.util.List;

public class MyOrder {
    private String totalAmount ;
    private String dateOrder;
    private String timeOrder;
    private String address ;
    private List <MyCart> cartList ;
    private int status  ;
    private String Order_ID  ;

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    public MyOrder(String totalAmount, String dateOrder, String timeOrder, String address, List<MyCart> cartList, int status) {
        this.totalAmount = totalAmount;
        this.dateOrder = dateOrder;
        this.timeOrder = timeOrder;
        this.address = address;
        this.cartList = cartList;
        this.status = status;
    }
    public int Quan_Order (){
        return cartList.size();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MyOrder() {
    }



    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MyCart> getCartList() {
        return cartList;
    }

    public void setCartList(List<MyCart> cartList) {
        this.cartList = cartList;
    }
}
