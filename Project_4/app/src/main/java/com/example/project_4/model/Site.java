package com.example.project_4.model;

public class Site {
    int siteId;
    String address;
    String contact_info;

    public Site(int siteId, String address) {
        this.siteId = siteId;
        this.address = address;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_info() {
        return contact_info;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }
}
