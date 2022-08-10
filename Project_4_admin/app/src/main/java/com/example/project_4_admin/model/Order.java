package com.example.project_4_admin.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    int order_id, status;
    String buyer_name;
    Double total;
    String site_address;
    Date TimeOrder;
    Date TimeTaken;

    public Order() {
    }

    public Order(int order_id, int status, String buyer_name, Double total, String site_address, Date timeOrder) {
        this.order_id = order_id;
        this.status = 1;
        this.buyer_name = buyer_name;
        this.total = total;
        this.site_address = site_address;
        TimeOrder = timeOrder;
    }

    public int getId() {
        return order_id;
    }

    public void setId(int id) {
        this.order_id = order_id;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSite_address() {
        return site_address;
    }

    public void setSite_address(String site_address) {
        this.site_address = site_address;
    }

    public Date getTimeOrder() {
        return TimeOrder;
    }

    public void setTimeOrder(Date timeOrder) {
        TimeOrder = timeOrder;
    }

    public Date getTimeTaken() {
        return TimeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        TimeTaken = timeTaken;
    }
}
