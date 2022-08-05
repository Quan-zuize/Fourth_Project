package com.example.onlytest.models;

public class User {
    private String email;
    private String phone;
    private String fullName;
    private String type;
    private String site_id;


    public User(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public User(String email, String phone, String fullName, String type ,String site_id) {
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.type = type;
        this.site_id = site_id;
    }

    public User(String email, String phone, String fullName, String type) {
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.type = type;
    }

    public User(String email, String fullName, String type) {
        this.email = email;
        this.fullName = fullName;
        this.type = type;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }
}
