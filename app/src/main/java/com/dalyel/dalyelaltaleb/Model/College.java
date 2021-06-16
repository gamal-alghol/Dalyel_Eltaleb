package com.dalyel.dalyelaltaleb.Model;

public class College {

    public College() {
    }
    public College(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
