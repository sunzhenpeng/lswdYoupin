package com.lswd.youpin.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by H61M-K on 2018/2/7.
 */
public class RecordInfo {
    //记录Id
    private Integer recId;
    //人员ID，与personInfo表ID关联
    private Integer perId;
    //人员卡号
    private String cardNo;
    //设备号
    private Integer devNo;
    //交易金额
    private BigDecimal money;
    //剩余现金余额
    private BigDecimal balance;
    //剩余补贴金额
    private BigDecimal subSidy;
    //折扣率
    private Integer discount;
    //交易时间
    private Date dealTime;
    //交易方式
    private String dealInfo;
    //交易类型
    private String dealType;
    //结算方式
    private String moneyType;
    //交易Id
    private Integer recIndex;
    //操作员
    private String Operator;


    public Integer getRecId() {
        return recId;
    }

    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getDevNo() {
        return devNo;
    }

    public void setDevNo(Integer devNo) {
        this.devNo = devNo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getSubSidy() {
        return subSidy;
    }

    public void setSubSidy(BigDecimal subSidy) {
        this.subSidy = subSidy;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealInfo() {
        return dealInfo;
    }

    public void setDealInfo(String dealInfo) {
        this.dealInfo = dealInfo;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public Integer getRecIndex() {
        return recIndex;
    }

    public void setRecIndex(Integer recIndex) {
        this.recIndex = recIndex;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }
}
