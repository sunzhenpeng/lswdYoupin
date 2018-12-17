package com.lswd.youpin.utils;

import com.lswd.youpin.model.lsyp.GoodOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/6/23.
 */
public class ResponseDate {
    private List<GoodOrder> goodOrders = new ArrayList<>();//商品订单集合

    float amountAll = 0;//商品营业额
    Integer orderNumber = 0;//商品订单数
    float refundAmount = 0;//商品退款金额

    public ResponseDate(float amountAll, Integer orderNumber, float refundAmount) {
        this.amountAll = amountAll;
        this.orderNumber = orderNumber;
        this.refundAmount = refundAmount;
    }

    public ResponseDate(List<GoodOrder> goodOrders) {
        this.goodOrders = goodOrders;
    }

    public ResponseDate() {
    }

    public List<GoodOrder> getGoodOrders() {
        return goodOrders;
    }

    public void setGoodOrders(List<GoodOrder> goodOrders) {
        this.goodOrders = goodOrders;
    }

    public float getAmountAll() {
        return amountAll;
    }

    public void setAmountAll(float amountAll) {
        this.amountAll = amountAll;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }
}
