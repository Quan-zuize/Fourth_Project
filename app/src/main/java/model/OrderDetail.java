package model;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    int detailId;
    int orderId;
    int menuId;
    int quantity;
    double price;

    public OrderDetail(int detailId, int orderId, int menuId, int quantity, double price) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetail(int menuId, int quantity, double price) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetail() {
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
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

    public void setPrice(float price) {
        this.price = price;
    }
}
