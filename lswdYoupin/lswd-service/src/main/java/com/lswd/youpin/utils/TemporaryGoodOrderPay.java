package com.lswd.youpin.utils;

import com.lswd.youpin.model.Associator;

import java.util.List;

public class TemporaryGoodOrderPay {

    private String address;
    private Associator associator;
    private List<TemporaryOrders> orders;
    private String back;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Associator getAssociator() {
        return associator;
    }

    public void setAssociator(Associator associator) {
        this.associator = associator;
    }

    public List<TemporaryOrders> getOrders() {
        return orders;
    }

    public void setOrders(List<TemporaryOrders> orders) {
        this.orders = orders;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }
}
