package com.lswd.youpin.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by H61M-K on 2018/2/7.
 */
public class PersonInfo {
    private Integer id;
    //state:
    //0:正常卡、1：挂失卡、2：注销卡、3：未办卡人员
    private Integer state;
    //人员编号
    private String number;
    //卡号
    private String cardNo;
    //会员名称
    private String name;
    //级别Id
    private Integer leave;
    //级别名称
    private String leaveName;
    //现金余额
    private BigDecimal balance;
    //补贴余额
    private BigDecimal subsidy;
    //开户押金
    private BigDecimal deposit;
    //交易单号
    private Integer recIndex;
    //身份证号
    private String idNo;
    //联系电话
    private String phone;
    //备注
    private String note;
    //开卡时间
    private Date openTime;
    //开卡操作员
    private String operator;
    //最后充值时间
    private Date lastAddMoneyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getLastAddMoneyTime() {
        return lastAddMoneyTime;
    }

    public void setLastAddMoneyTime(Date lastAddMoneyTime) {
        this.lastAddMoneyTime = lastAddMoneyTime;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getRecIndex() {
        return recIndex;
    }

    public void setRecIndex(Integer recIndex) {
        this.recIndex = recIndex;
    }
}
