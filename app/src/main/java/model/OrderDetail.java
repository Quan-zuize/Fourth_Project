package model;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    int orderId;
    int menu_title;
    int quantity;
    double price;
    double total;

    public OrderDetail(int menu_title, int quantity, double price) {
        this.menu_title = menu_title;
        this.quantity = quantity;
        this.price = price;
        this.total = price * quantity;
    }

    public OrderDetail() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenu_title() {
        return menu_title;
    }

    public void setMenu_title(int menu_title) {
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
