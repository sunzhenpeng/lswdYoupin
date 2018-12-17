package com.lswd.youpin.utils;

import java.io.Serializable;

/**
 * cerate by zhenguanqi 2017-12-18
 * function：用来打印会员的充值小票
 */
public class PrientBean implements Serializable{

    private String memberName;//会员名称
    private String memberTel;//会员手机号
    private Float oldCardBalance;//卡内原余额
    private Float newCardBalance;//卡内现余额
    private Float chargeMoney;//充值金额
    private String chargeTime;//充值时间

    public PrientBean() {
        super();
    }

    public PrientBean(String memberName, String memberTel, Float oldCardBalance, Float newCardBalance, Float chargeMoney, String chargeTime) {
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.oldCardBalance = oldCardBalance;
        this.newCardBalance = newCardBalance;
        this.chargeMoney = chargeMoney;
        this.chargeTime = chargeTime;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    public Float getOldCardBalance() {
        return oldCardBalance;
    }

    public void setOldCardBalance(Float oldCardBalance) {
        this.oldCardBalance = oldCardBalance;
    }

    public Float getNewCardBalance() {
        return newCardBalance;
    }

    public void setNewCardBalance(Float newCardBalance) {
        this.newCardBalance = newCardBalance;
    }

    public Float getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(Float chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }
}
