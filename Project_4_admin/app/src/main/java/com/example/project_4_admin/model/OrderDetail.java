package com.example.project_4_admin.model;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    int menuId;
    String menuTitle;
    int quantity;
    double price;
    double total;

    public OrderDetail() {
    }

    public OrderDetail(int menuId, String menuTitle, int quantity, double price) {
        this.menuId = menuId;
        this.menuTitle = menuTitle;
        this.quantity = quantity;
        this.price = price;
        this.total = price * quantity;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

//    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
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
