package com.example.doan_ltddnc_appbantaphoa.Model;

import java.io.Serializable;

public class ViewAllProduct implements Serializable, Comparable<ViewAllProduct> {
    private String name ;
    private String description ;
    private String rating ;
    private String img_url ;
    private String type ;
    private int price_1 ;
    private int price ;

    public ViewAllProduct(String name, String description, String rating, String img_url, String type, int price_1, int price) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.img_url = img_url;
        this.type = type;
        this.price_1 = price_1;
        this.price = price;
    }

    public ViewAllProduct() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice_1() {
        return price_1;
    }

    public void setPrice_1(int price_1) {
        this.price_1 = price_1;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ViewAllProduct{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating='" + rating + '\'' +
                ", img_url='" + img_url + '\'' +
                ", type='" + type + '\'' +
                ", price_1=" + price_1 +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(ViewAllProduct viewAllProduct) {
        return Integer.compare(viewAllProduct.price,price);
    }
}
