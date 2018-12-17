package com.lswd.youpin.model.lsyp;

/**
 * Created by liuhao on 2017/7/5.
 */
public class RecipeStatisricsOrder {
    private int id ;
    private String name;
    private String imageurl;
    private Double price;
    private int quantity;
    private String goodId;

    public int getId() {return id;}

    public String getName() {return name;}

    public String getImageurl() {return imageurl;}

    public Double getPrice() {return price;}

    public int getQuantity() {return quantity;}

    public void setId(int id) {this.id = id;}

    public void setName(String name) {this.name = name==null?null:name.trim();}

    public void setImageurl(String imageurl) {this.imageurl = imageurl==null?null:imageurl.trim();}

    public void setPrice(Double price) {this.price = price;}

    public void setQuantity(int quantity) {this.quantity = quantity;}

    public String getGoodId() {return goodId;}

    public void setGoodId(String goodId) {this.goodId = goodId;}

    private  String canteenId;

    private String canteenName;

    public String getCanteenId() {return canteenId;}

    public void setCanteenId(String canteenId) {this.canteenId = canteenId==null?null:canteenId.trim();}

    public String getCanteenName() {return canteenName;}

    public void setCanteenName(String canteenName) {this.canteenName = canteenName==null?null:canteenName.trim();}
}
