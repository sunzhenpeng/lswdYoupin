package com.lswd.youpin.model.vo;

/**
 * Created by zhenguanqi on 2017/12/07.
 */
public class TotalCountMoney {

    private Integer totalCount;

    private Float totalMoney;

    private Float totalReceivableMoney;

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Float getTotalReceivableMoney() {
        return totalReceivableMoney;
    }

    public void setTotalReceivableMoney(Float totalReceivableMoney) {
        this.totalReceivableMoney = totalReceivableMoney;
    }

    /*--------------------营养信息 start--------------------------*/

    private Float calorie;//卡路里
    private Float protein;//蛋白质
    private Float fat;//脂肪
    private Float carbonhydrate;//碳水化合物
    private Float vc;//维生素C

    public Float getCalorie() {
        return calorie;
    }

    public void setCalorie(Float calorie) {
        this.calorie = calorie;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getCarbonhydrate() {
        return carbonhydrate;
    }

    public void setCarbonhydrate(Float carbonhydrate) {
        this.carbonhydrate = carbonhydrate;
    }

    public Float getVc() {
        return vc;
    }

    public void setVc(Float vc) {
        this.vc = vc;
    }

    /*---------------------营养信息  end---------------------------*/

}
