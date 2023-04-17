package com.example.doan_ltddnc_appbantaphoa.Model;

public class Populars {
    private String name;
    private String description ;
    private String rating ;
    private String discount ;
    private String type ;
    private String img_url;

    @Override
    public String toString() {
        return "Populars{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating='" + rating + '\'' +
                ", discount='" + discount + '\'' +
                ", type='" + type + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }

    public Populars() {
    }

    public Populars(String name, String description, String rating, String discount, String type, String img_url) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.discount = discount;
        this.type = type;
        this.img_url = img_url;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
