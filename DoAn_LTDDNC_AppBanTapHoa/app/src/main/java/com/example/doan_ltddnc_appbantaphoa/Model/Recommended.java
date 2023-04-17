package com.example.doan_ltddnc_appbantaphoa.Model;

public class Recommended {
    private String name ;
    private String description ;
    private String rating ;
    private String mass ;
    private String img_url ;
    private int price ;

    public Recommended(String name, String description, String rating, String mass, String img_url, int price) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.mass = mass;
        this.img_url = img_url;
        this.price = price;
    }

    public Recommended() {
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

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
