package com.lswd.youpin.model.lsyp;

import java.util.Date;

/**
 * Created by liuhao on 2017/6/20.
 */
public class Material {

    public Material() {
    }

    public Material(String materialName, short level, String region, short season,Integer categoryId, Boolean isDelete,
                    Date updateTime, Date createTime, String updateUser, Double price, Integer stock,String specification) {
        this.materialName = materialName;
        this.level = level;
        this.region = region;
        this.season = season;
        this.categoryId = categoryId;
        this.isDelete = isDelete;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.price = price;
        this.stock = stock;
        this.specification = specification;
    }



    private String updateTimeString;//修改时间，String类型
    private String createTimeString;//创建时间，String类型
    private String seasonName;//季节名称
    private String categoryName;//材料分类名称
    private Boolean deleteStatus;//删除状态
    private String specification;
    private Integer id;//材料id
    private String materialId;//材料编号
    private String materialName;//材料名称
    private short level;//材料等级
    private String region;//材料产地
    private short season;//材料季节
    private Supplier supplier;
    private Integer categoryId;//材料分类编号
    private Boolean isDelete;//删除状态
    private Date updateTime;//修改时间
    private Date createTime;//创建时间
    private String updateUser;//修改者
    private Double price;
    private Integer stock;
    private Integer nutritionId;
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public String getMaterialId() {
        return materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public short getLevel() {
        return level;
    }

    public String getRegion() {
        return region;
    }

    public short getSeason() {
        return season;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Boolean getDelete() {return isDelete;}

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSeason(short season) {
        this.season = season;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setDelete(Boolean delete) {isDelete = delete;}

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTimeString() {return updateTimeString;}
    public void setUpdateTimeString(String updateTimeString) {this.updateTimeString = updateTimeString;}
    public String getCreateTimeString() {return createTimeString;}
    public void setCreateTimeString(String createTimeString) {this.createTimeString = createTimeString;}

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public Boolean getDeleteStatus() {return isDelete;}
    public void setDeleteStatus(Boolean deleteStatus) {this.isDelete = deleteStatus;}

    public String getSpecification() {return specification;}

    public void setSpecification(String specification) {this.specification= specification==null?"":specification.trim();}

    public Supplier getSupplier() {return supplier;}

    public void setSupplier(Supplier supplier) {this.supplier = supplier;}


    public Double getPrice() {return price;}

    public void setPrice(Double price) {this.price = price;}
    private Unit unit;
    private String canteenId;

    public String getCanteenId() {return canteenId;}

    public void setCanteenId(String canteenId) {this.canteenId = canteenId;}

    private String canteenName;

    public String getCanteenName() {return canteenName;}

    public void setCanteenName(String canteenName) {this.canteenName = canteenName==null?"":canteenName.trim();}

    public Unit getUnit() {return unit;}

    public void setUnit(Unit unit) {this.unit=unit;}

    private Integer number;

    private Double priceAll;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getPriceAll() {return priceAll;}

    public void setPriceAll(Double priceAll) {this.priceAll = priceAll;}

    public Integer getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(Integer nutritionId) {
        this.nutritionId = nutritionId;
    }
}
