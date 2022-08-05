package model;

import java.io.Serializable;

public class User implements Serializable {
    String user_id;
    String name;
    String password;
    String gmail;
    String phone;
    int role, site_id;

    public User(String name, String gmail, String phone) {
        this.name = name;
        this.gmail = gmail;
        this.phone = phone;
        this.role = 0;
        this.site_id = 0;
    }

    public User() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String buyer_id) {
        this.user_id = buyer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }
}
