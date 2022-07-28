package model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    int order_id;
    String buyer_name;
    Double total;
    String status;
    int site_id;
    Date TimeOrder;
    Date TimeTaken;

    public Order() {
    }

    public Order(int order_id, String buyer_name, Double total, int site_id, Date timeOrder, Date timeTaken) {
        this.order_id = order_id;
        this.buyer_name = buyer_name;
        this.total = total;
        this.status = "Pending";
        this.site_id = site_id;
        TimeOrder = timeOrder;
        TimeTaken = timeTaken;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
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
