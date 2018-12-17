package com.lswd.youpin.model.lsyp;

import java.util.Date;
import java.util.List;

/**
 * Created by liuhao on 2017/6/21.
 */
public class RecipeOrder {

    private String canteenName;
    private Integer id;
    private String orderId;
    private String associatorId;
    private String canteenId;
    private short payType;
    private String pay;
    private Date eatTime;
    private Double orderAmount;
    private short status;
    private String description;
    private Date createTime;
    private Date updateTime;
    private Integer unifiedId;
    private List<RecipeOrderItems> recipeOrderItemsList;
    private String associatorTel;
    private String payTypeName;
    private Boolean gOrRFlag = false;
    private String canteenLogo;
    private String associatorName;
    private Integer pickType;
    private String pickName;
    private String createTimeString;


    public String getAssociatorTel() {
        return associatorTel;
    }

    public void setAssociatorTel(String associatorTel) {
        this.associatorTel = associatorTel;
    }

    public String getCanteenLogo() {
        return canteenLogo;
    }

    public void setCanteenLogo(String canteenLogo) {
        this.canteenLogo = canteenLogo;
    }

    public Boolean getgOrRFlag() {
        return gOrRFlag;
    }

    public void setgOrRFlag(Boolean gOrRFlag) {
        this.gOrRFlag = gOrRFlag;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }

    public Integer getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public String getAssociatorId() {
        return associatorId;
    }

    public short getPayType() {
        return payType;
    }

    public Date getEatTime() {
        return eatTime;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public short getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public List<RecipeOrderItems> getRecipeOrderItemsList() {
        return recipeOrderItemsList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public void setAssociatorId(String associatorId) {
        this.associatorId = associatorId == null ? null : associatorId.trim();
    }

    public void setEatTime(Date eatTime) {
        this.eatTime = eatTime;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setRecipeOrderItemsList(List<RecipeOrderItems> recipeOrderItemsList) {
        this.recipeOrderItemsList = recipeOrderItemsList;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId == null ? null : canteenId.trim();
    }

    public void setPayType(short payType) {
        this.payType = payType;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay == null ? null : pay.trim();
    }

    public String getAssociatorName() {
        return associatorName;
    }

    public void setAssociatorName(String associatorName) {
        this.associatorName = associatorName == null ? null : associatorName.trim();
    }

    public Integer getUnifiedId() {
        return unifiedId;
    }

    public void setUnifiedId(Integer unifiedId) {
        this.unifiedId = unifiedId;
    }

    public Integer getPickType() {return pickType;}

    public void setPickType(Integer pickType) {this.pickType = pickType;}

    public String getPickName() {return pickName;}

    public void setPickName(String pickName) {this.pickName = pickName==null?null:pickName.trim();}

    public String getCreateTimeString() {return createTimeString;}

    public void setCreateTimeString(String createTimeString) {this.createTimeString = createTimeString==null?null:createTimeString.trim();}

    private Double outFee;

    public Double getOutFee() {return outFee;}

    public void setOutFee(Double outFee) {this.outFee = outFee;}

}
