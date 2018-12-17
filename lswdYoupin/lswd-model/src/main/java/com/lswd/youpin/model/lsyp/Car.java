package com.lswd.youpin.model.lsyp;

import java.util.Date;

/**
 * Created by liuhao on 2017/6/19.
 */
public class Car {
    private Integer id;
    private Integer number;
    private Good good;
    private Recipe recipe;
    private String canteenId;
    private String goodId;
    private String planId;
    private Boolean isType;
    private Boolean isDelete;
    private String associatorId;
    private Integer recipeType;
    private String img;
    private String msg;
    private String text;
    private String Price;
    private Date pickTime;
    private Date updateTime;
    private Date createTime;
    private Boolean checked=true;
    public Integer getId() {
        return id;
    }

    public Good getGood() {
        return good;
    }

    public String getCanteenId() {return canteenId;}

    public Boolean getType() {return isType;}

    public Recipe getRecipe() {return recipe;}

    public String getGoodId() {return goodId;}

    public String getAssociatorId() {
        return associatorId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public void setRecipe(Recipe recipe) {this.recipe = recipe;}

    public void setAssociatorId(String associatorId) {
        this.associatorId = associatorId;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCanteenId(String canteenId) {this.canteenId = canteenId==null?null:canteenId.trim();}

    public void setType(Boolean type) {isType = type;}

    public void setGoodId(String goodId) {this.goodId = goodId==null?null:goodId.trim();}

    public Integer getNumber() {return number;}

    public String getImg() {return img;}

    public String getMsg() {return msg;}

    public String getText() {return text;}

    public String getPrice() {return Price;}

    public void setNumber(Integer number) {this.number = number;}

    public void setImg(String img) {this.img = img==null?null:img.trim();}

    public void setMsg(String msg) {this.msg = msg==null?null:msg.trim();}

    public void setText(String text) {this.text = text==null?null:text.trim();}

    public void setPrice(String price) {Price = price==null?null:price.trim();}

    public void setCreateTime(Date createTime) {this.createTime = createTime;}

    public Boolean getChecked() {return checked;}

    public void setChecked(Boolean checked) {this.checked = checked;}

    public Date getPickTime() {return pickTime;}

    public void setPickTime(Date pickTime) {this.pickTime = pickTime;}

    public Integer getRecipeType() {return recipeType;}

    public void setRecipeType(Integer recipeType) {this.recipeType = recipeType;}

    public String getPlanId() {return planId;}

    public void setPlanId(String planId) {this.planId = planId==null?null:planId.trim();}


    private Double outFee;

    public Double getOutFee() {return outFee;}

    public void setOutFee(Double outFee) {this.outFee = outFee;}

}
