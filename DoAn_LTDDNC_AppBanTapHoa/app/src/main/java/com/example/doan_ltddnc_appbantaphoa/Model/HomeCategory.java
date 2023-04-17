package com.example.doan_ltddnc_appbantaphoa.Model;

public class HomeCategory {
    private String name ;
    private String type ;
    private String img_url ;

    public HomeCategory(String name, String type, String img_url) {
        this.name = name;
        this.type = type;
        this.img_url = img_url;
    }

    public HomeCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
