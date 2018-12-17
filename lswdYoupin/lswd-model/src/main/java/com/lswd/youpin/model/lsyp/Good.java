package com.lswd.youpin.model.lsyp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Good {

    private Boolean cancel = false;//删除列表上的商品
    private List<String> delImgs = new ArrayList<>();//商品详情时，用户删除的图片
    private List<String> oldSmallPic = new ArrayList<>();//商品老图片(小图)
    private List<String> oldBigPic = new ArrayList<>();//商品老图片(大图)
    private boolean isHot = false;//是否热销
    private Integer surplus = 0;//剩余量
    private String standard;//商品规格
    private Integer num = 0;
    private float marketPrice;//市场价
    private String bigimageurl;//大图片
    private String  startEndModel;//开始-结束日期
    private Integer monthSale = 0;//月销量
    private String unitName;//单位name
    private Integer unit;//单位
    private Supplier supplier;//供应商
    private boolean checked = false;//商品计划添加商品的时候，做的一个标识
    private float price;//餐厅优惠价
    private String categoryName;//分类名称
    private String supplierName = "";//供应商名称
    private String canteenId;//餐厅编号
    private GoodCategory category;//分类
    private GoodAttribute goodAttribute;//商品自身属性
    private GoodAdditiveattribute goodAdditiveattribute;//商品附加属性
    private List<GoodDetailsImg> goodDetailsImgs;

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public List<String> getDelImgs() {
        return delImgs;
    }

    public void setDelImgs(List<String> delImgs) {
        this.delImgs = delImgs;
    }

    public List<GoodDetailsImg> getGoodDetailsImgs() {
        return goodDetailsImgs;
    }

    public void setGoodDetailsImgs(List<GoodDetailsImg> goodDetailsImgs) {
        this.goodDetailsImgs = goodDetailsImgs;
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

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getBigimageurl() {
        return bigimageurl;
    }

    public void setBigimageurl(String bigimageurl) {
        this.bigimageurl = bigimageurl;
    }

    public String getStartEndModel() { return startEndModel; }

    public void setStartEndModel(String startEndModel) { this.startEndModel = startEndModel; }

    public Integer getMonthSale() {
        return monthSale;
    }

    public void setMonthSale(Integer monthSale) {
        this.monthSale = monthSale;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public GoodAdditiveattribute getGoodAdditiveattribute() {
        return goodAdditiveattribute;
    }

    public void setGoodAdditiveattribute(GoodAdditiveattribute goodAdditiveattribute) {this.goodAdditiveattribute = goodAdditiveattribute;}

    public GoodAttribute getGoodAttribute() {
        return goodAttribute;
    }

    public void setGoodAttribute(GoodAttribute goodAttribute) {
        this.goodAttribute = goodAttribute;
    }

    public GoodCategory getCategory() {return category;}

    public void setCategory(GoodCategory category) {this.category = category;}

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    private Integer id;

    private String goodId;

    private String goodName;

    private String imageurl;

    private String supplierId;

    private Integer categoryId;

    private Date startTime;

    private Date endTime;

    private Boolean isDelete = false;

    private Short status = 0;

    private String updateUser;

    private Date updateTime;

    private String createUser;

    private Date createTime;

    private String description;

    private String safetyone;

    private String safetytwo;

    private String safetythree;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId == null ? null : goodId.trim();
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl == null ? null : imageurl.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}