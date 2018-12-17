package com.lswd.youpin.model.lsyp;

public class GoodAdditiveattribute {

    private Integer id;

    private String brand;

    private Float price;

    private String region;

    private Short season;

    private String suitcrowd;

    private String goodId;

    private String safetyone;

    private String safetytwo;

    private String safetythree;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public Short getSeason() {
        return season;
    }

    public void setSeason(Short season) {
        this.season = season;
    }

    public String getSuitcrowd() {
        return suitcrowd;
    }

    public void setSuitcrowd(String suitcrowd) {
        this.suitcrowd = suitcrowd == null ? null : suitcrowd.trim();
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId == null ? null : goodId.trim();
    }

    public String getSafetyone() {
        return safetyone;
    }

    public void setSafetyone(String safetyone) {
        this.safetyone = safetyone == null ? null : safetyone.trim();
    }

    public String getSafetytwo() {
        return safetytwo;
    }

    public void setSafetytwo(String safetytwo) {
        this.safetytwo = safetytwo == null ? null : safetytwo.trim();
    }

    public String getSafetythree() {
        return safetythree;
    }

    public void setSafetythree(String safetythree) {
        this.safetythree = safetythree == null ? null : safetythree.trim();
    }

}