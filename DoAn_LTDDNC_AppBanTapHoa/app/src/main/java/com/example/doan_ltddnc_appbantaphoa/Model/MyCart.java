package com.example.doan_ltddnc_appbantaphoa.Model;

import java.io.Serializable;

public class MyCart implements Serializable {
    private String productName;
    private String currentDate;
    private String currentTime;
    private String img_url ;
    private int productPrice;
    private int totalPrice;
    private int totalQuantity;
    String documentId ;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public MyCart(String productName, String currentDate, String currentTime, String img_url, int productPrice, int totalPrice, int totalQuantity,String documentId) {
        this.productName = productName;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.img_url = img_url;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.documentId =documentId ;
    }

    public MyCart() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
