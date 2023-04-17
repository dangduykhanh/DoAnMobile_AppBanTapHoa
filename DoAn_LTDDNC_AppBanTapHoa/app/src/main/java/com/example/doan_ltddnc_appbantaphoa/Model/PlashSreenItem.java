package com.example.doan_ltddnc_appbantaphoa.Model;

public class PlashSreenItem {
    private int Image ;
    private String tittle ;

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public PlashSreenItem(int image, String tittle) {

        Image = image;
        this.tittle = tittle;
    }
}
