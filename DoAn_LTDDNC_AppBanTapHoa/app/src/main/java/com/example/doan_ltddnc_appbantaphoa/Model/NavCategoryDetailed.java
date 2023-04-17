package com.example.doan_ltddnc_appbantaphoa.Model;

public class NavCategoryDetailed {
    private String name ;
    private String img_url;
    private String type;
    private int price;


    public NavCategoryDetailed(String name, String img_url, String type, int price) {
        this.name = name;
        this.img_url = img_url;
        this.type = type;
        this.price = price;
    }

    @Override
    public String toString() {
        return "NavCategoryDetailed{" +
                "name='" + name + '\'' +
                ", img_url='" + img_url + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

    public NavCategoryDetailed() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
