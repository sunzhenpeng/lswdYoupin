package com.lswd.youpin.utils;

import java.util.Date;
import java.util.List;

public class TemporaryOrders {

    private Date pickTime;
    private String orderId;
    private Float priceAll;
    private List<TemporaryGood> good;

    public TemporaryOrders() {
        super();
    }

    public TemporaryOrders(Date pickTime, String orderId, Float priceAll) {
        this.pickTime = pickTime;
        this.orderId = orderId;
        this.priceAll = priceAll;
    }

    public Date getPickTime() {
        return pickTime;
    }

    public void setPickTime(Date pickTime) {
        this.pickTime = pickTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<TemporaryGood> getGood() {
        return good;
    }

    public void setGood(List<TemporaryGood> good) {
        this.good = good;
    }

    public Float getPriceAll() {
        return priceAll;
    }

    public void setPriceAll(Float priceAll) {
        this.priceAll = priceAll;
    }
}
