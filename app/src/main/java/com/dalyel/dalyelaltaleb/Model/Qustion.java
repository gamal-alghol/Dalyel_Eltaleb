package com.dalyel.dalyelaltaleb.Model;



public class Qustion {

    private String qustion;
    private int image;

    public Qustion(String qustion, int image) {

        this.qustion = qustion;
        this.image = image;
    }


    public String getQustion() {
        return qustion;
    }

    public void setQustion(String qustion) {
        this.qustion = qustion;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
