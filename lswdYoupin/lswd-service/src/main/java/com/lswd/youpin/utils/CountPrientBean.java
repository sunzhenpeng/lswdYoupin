package com.lswd.youpin.utils;

import java.io.Serializable;

/**
 * cerate by zhenguanqi 2017-12-18
 * function：用来打印吧台的充值小票
 */
public class CountPrientBean implements Serializable{

    private String recipeName;
    private Integer count;
    private Float money;
    private Float totalMoney;

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
