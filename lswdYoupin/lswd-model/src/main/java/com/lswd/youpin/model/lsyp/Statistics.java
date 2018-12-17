package com.lswd.youpin.model.lsyp;

/**
 * Created by liuhao on 2017/7/4.
 */
public class Statistics {
    private Double priceAll;
    private Double refundAll;
    private Double goodPrice;
    private Integer goodOrderCount;
    private Double recipePrice;
    private Float goodPaidPrice;
    private Float recipePaidPrice;
    private Integer recipeOrderCount;
    private Integer value;
    private String name;



    public Double getPriceAll() {return priceAll;}

    public Double getRefundAll() {return refundAll;}

    public Double getGoodPrice() {return goodPrice;}

    public Integer getGoodOrderCount() {return goodOrderCount;}

    public Double getRecipePrice() {return recipePrice;}

    public Integer getRecipeOrderCount() {return recipeOrderCount;}

    public void setPriceAll(Double priceAll) {this.priceAll = priceAll;}

    public void setRefundAll(Double refundAll) {this.refundAll = refundAll;}

    public void setGoodPrice(Double goodPrice) {this.goodPrice = goodPrice;}

    public void setGoodOrderCount(Integer goodOrderCount) {this.goodOrderCount = goodOrderCount;}

    public void setRecipePrice(Double recipePrice) {this.recipePrice = recipePrice;}

    public void setRecipeOrderCount(Integer recipeOrderCount) {this.recipeOrderCount = recipeOrderCount;}

    public Integer getValue() {return value;}

    public String getName() {return name;}

    public void setValue(Integer value) {this.value = value;}

    public void setName(String name) {this.name = name;}

    public Float getGoodPaidPrice() {return goodPaidPrice;}

    public void setGoodPaidPrice(Float goodPaidPrice) {this.goodPaidPrice = goodPaidPrice;}

    public Float getRecipePaidPrice() {return recipePaidPrice;}

    public void setRecipePaidPrice(Float recipePaidPrice) {this.recipePaidPrice = recipePaidPrice;}
}
