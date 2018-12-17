package com.lswd.youpin.model.lsyp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recipe {

    private Integer saleTotal = 0;
    private Float saleTotalMoney = 0F;

    public Integer getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Integer saleTotal) {
        this.saleTotal = saleTotal;
    }

    public Float getSaleTotalMoney() {
        return saleTotalMoney;
    }

    public void setSaleTotalMoney(Float saleTotalMoney) {
        this.saleTotalMoney = saleTotalMoney;
    }

    /*-------------------------------------------------营养成分管理 start-------------------------------------------------------*/
    private Float calorieAll = 0F;//卡路里
    private Float proteinAll = 0F;//蛋白质
    private Float fatAll = 0F;//脂肪
    private Float carbonhydrateAll = 0F;//碳水化合物
    private Float vcAll = 0F;//维生素C

    private List<Nutrition> nutritions;

    public List<Nutrition> getNutritions() {
        return nutritions;
    }

    public void setNutritions(List<Nutrition> nutritions) {
        this.nutritions = nutritions;
    }

    public Float getCalorieAll() {
        return calorieAll;
    }

    public void setCalorieAll(Float calorieAll) {
        this.calorieAll = calorieAll;
    }

    public Float getProteinAll() {
        return proteinAll;
    }

    public void setProteinAll(Float proteinAll) {
        this.proteinAll = proteinAll;
    }

    public Float getFatAll() {
        return fatAll;
    }

    public void setFatAll(Float fatAll) {
        this.fatAll = fatAll;
    }

    public Float getCarbonhydrateAll() {
        return carbonhydrateAll;
    }

    public void setCarbonhydrateAll(Float carbonhydrateAll) {
        this.carbonhydrateAll = carbonhydrateAll;
    }

    public Float getVcAll() {
        return vcAll;
    }

    public void setVcAll(Float vcAll) {
        this.vcAll = vcAll;
    }

    /*-------------------------------------------------营养成分管理 end---------------------------------------------------------*/

    private RecipeAttribute recipeAttribute;
    private List<String> delImgs = new ArrayList<>();//菜品详情时，用户删除的图片
    private List<String> oldSmallPic = new ArrayList<>();//菜品老图片(小图)
    private List<String> oldBigPic = new ArrayList<>();//菜品老图片(大图)
    private boolean isHot = false;//是否热销
    private Integer surplus = 0;//剩余量
    private Float marketPrice;//市场价
    private Integer num = 0 ;
    private String bigimageurl;//宽屏图片
    private Integer monthSale = 0;//月销量
    private boolean checked = false;//菜品计划添加时需要用到的标识
    private String statusName;//状态name
    private String cookTypeName;//烹饪类型名称
    private String categoryName;//菜品分类名称
    private String canteenId;//餐厅编号
    private String updateTimeString;//更新时间---- 字符串
    private String createTimeString;//创建时间---- 字符串
    private List<RecipeMaterial> materials;
    private List<RecipeDetailsImg> recipeDetailsImgs;

    public RecipeAttribute getRecipeAttribute() {
        return recipeAttribute;
    }

    public void setRecipeAttribute(RecipeAttribute recipeAttribute) {
        this.recipeAttribute = recipeAttribute;
    }

    public List<String> getDelImgs() {
        return delImgs;
    }

    public void setDelImgs(List<String> delImgs) {
        this.delImgs = delImgs;
    }

    public List<RecipeDetailsImg> getRecipeDetailsImgs() {
        return recipeDetailsImgs;
    }

    public void setRecipeDetailsImgs(List<RecipeDetailsImg> recipeDetailsImgs) {
        this.recipeDetailsImgs = recipeDetailsImgs;
    }

    public List<String> getOldSmallPic() {
        return oldSmallPic;
    }

    public void setOldSmallPic(List<String> oldSmallPic) {
        this.oldSmallPic = oldSmallPic;
    }

    public List<String> getOldBigPic() {
        return oldBigPic;
    }

    public void setOldBigPic(List<String> oldBigPic) {
        this.oldBigPic = oldBigPic;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBigimageurl() {
        return bigimageurl;
    }

    public void setBigimageurl(String bigimageurl) {
        this.bigimageurl = bigimageurl;
    }

    public Integer getMonthSale() {
        return monthSale;
    }

    public void setMonthSale(Integer monthSale) {
        this.monthSale = monthSale;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<RecipeMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<RecipeMaterial> materials) {
        this.materials = materials;
    }

    public Recipe() {
    }

    public Recipe(Float marketPrice,String recipeName,Short cookType,String imageurl,String bigimageurl,
                  Short status, Float guidePrice, Integer categoryId, String canteenId,String cookDetail, Boolean isDelete, String createUser, String updateUser, Date createTime, Date updateTime) {
        this.marketPrice = marketPrice;
        this.recipeName = recipeName;
        this.cookType = cookType;
        this.imageurl = imageurl;
        this.bigimageurl = bigimageurl;
        this.status = status;
        this.guidePrice = guidePrice;
        this.categoryId = categoryId;
        this.canteenId = canteenId;
        this.cookDetail = cookDetail;
        this.isDelete = isDelete;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCookTypeName() {
        return cookTypeName;
    }

    public void setCookTypeName(String cookTypeName) {
        this.cookTypeName = cookTypeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUpdateTimeString() {return updateTimeString;}

    public void setUpdateTimeString(String updateTimeString) {this.updateTimeString = updateTimeString;}

    public String getCreateTimeString() {return createTimeString;}

    public void setCreateTimeString(String createTimeString) {this.createTimeString = createTimeString;}

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    private Integer id;

    private String recipeName;

    private String recipeId;

    private String imageurl;

    private Short recipeType;

    private Short cookType;

    private Short status = 0;

    private Float guidePrice;

    private Integer categoryId;

    private String cookDetail;

    private Boolean isDelete = false;

    private String createUser;

    private String updateUser;

    private Date createTime;

    private Date updateTime;

    private String safetyone;

    private String safetytwo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName == null ? null : recipeName.trim();
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Short getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(Short recipeType) {
        this.recipeType = recipeType;
    }

    public Short getCookType() {
        return cookType;
    }

    public void setCookType(Short cookType) {
        this.cookType = cookType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Float getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Float guidePrice) {
        this.guidePrice = guidePrice;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCookDetail() {
        return cookDetail;
    }

    public void setCookDetail(String cookDetail) {
        this.cookDetail = cookDetail == null ? null : cookDetail.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

}