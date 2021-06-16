package com.dalyel.dalyelaltaleb.Model;

import java.util.ArrayList;
import java.util.Date;

public class News2 {
    private String imgNews;
    private String imgUrl;
    private Date datePost;
    private String textNews;
    private String title;
    private ArrayList<String> listImagesUrl;


    public String getImgNews() {
        return imgNews;
    }

    public void setImgNews(String imgNews) {
        this.imgNews = imgNews;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    public String getTextNews() {
        return textNews;
    }

    public void setTextNews(String textNews) {
        this.textNews = textNews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getListImagesUrl() {
        return listImagesUrl;
    }

    public void setListImagesUrl(ArrayList<String> listImagesUrl) {
        this.listImagesUrl = listImagesUrl;
    }
}
