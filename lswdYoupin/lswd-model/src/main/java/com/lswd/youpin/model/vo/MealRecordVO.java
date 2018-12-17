package com.lswd.youpin.model.vo;

import java.util.Date;

/**
 * Created by H61M-K on 2017/12/14.
 */
public class MealRecordVO {
    private Date eatTime;
    private Float totalAmount;
    private Integer totalCount;
    private String memberName;
    private String cardUid;
    private Integer totalNum;
    private Integer memberId;

    public Date getEatTime() {
        return eatTime;
    }

    public void setEatTime(Date eatTime) {
        this.eatTime = eatTime;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getCardUid() {
        return cardUid;
    }

    public void setCardUid(String cardUid) {
        this.cardUid = cardUid;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
