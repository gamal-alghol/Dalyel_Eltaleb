package com.dalyel.dalyelaltaleb.Model;

public class Doctor {
    String name,email,phone,college;
    public Doctor() { }
    public Doctor(String name,String college, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.college = college;
    }


    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
