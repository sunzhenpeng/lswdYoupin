package com.lswd.youpin.model.lsyp;

/**
 * Created by liuhao on 2017/7/4.
 */
public class GoodStatisticsOrder {
    private Integer id;
    private String name;
    private String goodId;
    private Integer quantity;
    private String description;
    private String imageurl;
    private Float price;
    private String unit;
    private String canteenId;
    private String canteenName;
    private Float priceAll;

    public Integer getId() {return id;}

    public String getName() {return name;}

    public Integer getQuantity() {return quantity;}

    public String getDescription() {return description;}

    public String getImageurl() {return imageurl;}

    public Float getPrice() {return price;}

    public String getUnit() {return unit;}

    public void setName(String name) {this.name = name==null?null:name.trim();}

    public void setQuantity(Integer quantity) {this.quantity = quantity;}

    public void setDescription(String description) {this.description = description==null?null:description.trim();}

    public void setImageurl(String imageurl) {this.imageurl = imageurl==null?null:imageurl.trim();}

    public void setPrice(Float price) {this.price = price;}

    public void setUnit(String unit) {this.unit = unit==null?null:unit.trim();}

    public void setId(Integer id) {this.id = id;}

    public String getGoodId() {return goodId;}

    public void setGoodId(String goodId) {this.goodId = goodId;}

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {this.canteenId = canteenId;}

    public String getCanteenName() {return canteenName;}

    public void setCanteenName(String canteenName) {this.canteenName = canteenName;}

    public Float getPriceAll() {return priceAll;}

    public void setPriceAll(Float priceAll) {this.priceAll = priceAll;}
}
