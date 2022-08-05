package com.example.onlytest.models;

import java.io.Serializable;

public class Menu implements Serializable {
    int menu_id;
    String title;
    String type;
    Double price;
    String image_src;

    public Menu() {
    }

    public Menu(int menu_id, String title, String type, Double price, String image_src) {
        this.menu_id = menu_id;
        this.title = title;
        this.type = type;
        this.price = price;
        this.image_src = image_src;
    }

    public int getId() {
        return menu_id;
    }

    public void setId(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
