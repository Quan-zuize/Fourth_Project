package model;

import java.io.Serializable;

public class Buyer implements Serializable {
    int buyer_id;
    String name;
    String password;
    String gmail;
    String phone;

    public Buyer(String name, String gmail, String phone) {
        this.name = name;
        this.gmail = gmail;
        this.phone = phone;
    }

    public Buyer() {
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
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
}
