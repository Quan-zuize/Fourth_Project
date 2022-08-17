package com.example.project_4_admin.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    int orderID, status;
    String buyerId, buyerName, buyerPhone;
    Double total;
    String siteAddress, note;
    String TimeOrder;
    String TimeTaken;
    List<OrderDetail> Details;

    public Order(int orderID, String buyerId, String buyerName,String buyerPhone, Double total, String siteAddress, String note, String timeOrder, List<OrderDetail> details) {
        this.orderID = orderID;
        this.buyerId = buyerId;
        this.status = 1; //0: cancelled, 2:Processing, 3:Paid
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.total = total;
        this.siteAddress = siteAddress;
        this.note = note;
        this.TimeOrder = timeOrder;
        this.TimeTaken = "";
        this.Details = details;
    }

    public Order(int orderID, int status, String buyerId, String buyerName, String buyerPhone, Double total, String siteAddress, String note, String timeOrder, String timeTaken, List<OrderDetail> details) {
        this.orderID = orderID;
        this.status = status;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.total = total;
        this.siteAddress = siteAddress;
        this.note = note;
        TimeOrder = timeOrder;
        TimeTaken = timeTaken;
        Details = details;
    }

    public int getId() {
        return orderID;
    }

    public void setId(int id) {
        this.orderID = orderID;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getTimeOrder() {
        return TimeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        TimeOrder = timeOrder;
    }

    public String getTimeTaken() {
        return TimeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        TimeTaken = timeTaken;
    }

    public List<OrderDetail> getDetails() {
        return Details;
    }

    public void setDetails(List<OrderDetail> details) {
        Details = details;
    }
}
