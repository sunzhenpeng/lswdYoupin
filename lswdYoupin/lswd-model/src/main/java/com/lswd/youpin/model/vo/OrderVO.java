package com.lswd.youpin.model.vo;

import java.io.Serializable;

/**
 * Created by liruilong on 2017/8/10.
 */
public class OrderVO implements Serializable {
    private String orders;
    private String canteenId;
    private String tradeType;
    private String associatorId;
    private String memberId;

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getAssociatorId() {
        return associatorId;
    }

    public void setAssociatorId(String associatorId) {
        this.associatorId = associatorId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
