package com.example.project_4.model;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    int menu_id;
    String menu_title;
    int quantity;
    double price;
    double total;

    public OrderDetail() {
    }

    public OrderDetail(int menu_id, String menu_title, int quantity, double price) {
        this.menu_id = menu_id;
        this.menu_title = menu_title;
        this.quantity = quantity;
        this.price = price;
        this.total = price * quantity;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

//    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }

    public String getMenu_title() {
        return menu_title;
    }

    public void setMenu_title(String menu_title) {
        this.menu_title = menu_title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
