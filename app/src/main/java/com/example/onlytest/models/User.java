package com.example.onlytest.models;

public class User {
    private String email;
    private String phone;
    private String fullName;

    public User(String email, String phone, String fullName) {
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
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
}
