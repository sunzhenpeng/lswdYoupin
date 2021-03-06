package com.lswd.youpin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class  Associator  implements Serializable{


    private String statusName;

    private Integer associatorTypeId;

    private String associatorTypeName;

    private AssociatorCard associatorCard = new AssociatorCard();

    public AssociatorCard getAssociatorCard() {
        return associatorCard;
    }

    public void setAssociatorCard(AssociatorCard associatorCard) {
        this.associatorCard = associatorCard;
    }

    public String getAssociatorTypeName() {
        return associatorTypeName;
    }

    public void setAssociatorTypeName(String associatorTypeName) {
        this.associatorTypeName = associatorTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getAssociatorTypeId() {
        return associatorTypeId;
    }

    public void setAssociatorTypeId(Integer associatorTypeId) {
        this.associatorTypeId = associatorTypeId;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.associator_id
     *
     * @mbg.generated
     */
    private String associatorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.account
     *
     * @mbg.generated
     */
    private String account;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.associator_wx
     *
     * @mbg.generated
     */
    private String associatorWx;


    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.is_delete
     *
     * @mbg.generated
     */
    private Boolean isDelete;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.is_use
     *
     * @mbg.generated
     */
    private Boolean isUse;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.lock_time
     *
     * @mbg.generated
     */
    private Short lockTime;

    private String buyService;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    private String memberId;

    private String img;
    /**
     *会员绑定的餐厅列表
     */
    private List<Canteen>canteenList;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.safetyone
     *
     * @mbg.generated
     */
    private String safetyone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.safetytwo
     *
     * @mbg.generated
     */
    private String safetytwo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_associator.safetythree
     *
     * @mbg.generated
     */
    private String safetythree;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.id
     *
     * @return the value of t_associator.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.id
     *
     * @param id the value for t_associator.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.associator_id
     *
     * @return the value of t_associator.associator_id
     *
     * @mbg.generated
     */
    public String getAssociatorId() {
        return associatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.associator_id
     *
     * @param associatorId the value for t_associator.associator_id
     *
     * @mbg.generated
     */
    public void setAssociatorId(String associatorId) {
        this.associatorId = associatorId == null ? null : associatorId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.account
     *
     * @return the value of t_associator.account
     *
     * @mbg.generated
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.account
     *
     * @param account the value for t_associator.account
     *
     * @mbg.generated
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.password
     *
     * @return the value of t_associator.password
     *
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.password
     *
     * @param password the value for t_associator.password
     *
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.associator_wx
     *
     * @return the value of t_associator.associator_wx
     *
     * @mbg.generated
     */
    public String getAssociatorWx() {
        return associatorWx;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.associator_wx
     *
     * @param associatorWx the value for t_associator.associator_wx
     *
     * @mbg.generated
     */
    public void setAssociatorWx(String associatorWx) {
        this.associatorWx = associatorWx == null ? null : associatorWx.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.is_delete
     *
     * @return the value of t_associator.is_delete
     *
     * @mbg.generated
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.is_delete
     *
     * @param isDelete the value for t_associator.is_delete
     *
     * @mbg.generated
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.is_use
     *
     * @return the value of t_associator.is_use
     *
     * @mbg.generated
     */
    public Boolean getIsUse() {
        return isUse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.is_use
     *
     * @param isUse the value for t_associator.is_use
     *
     * @mbg.generated
     */
    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.lock_time
     *
     * @return the value of t_associator.lock_time
     *
     * @mbg.generated
     */
    public Short getLockTime() {
        return lockTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.lock_time
     *
     * @param lockTime the value for t_associator.lock_time
     *
     * @mbg.generated
     */
    public void setLockTime(Short lockTime) {
        this.lockTime = lockTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.create_time
     *
     * @return the value of t_associator.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.create_time
     *
     * @param createTime the value for t_associator.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.update_time
     *
     * @return the value of t_associator.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.update_time
     *
     * @param updateTime the value for t_associator.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.safetyone
     *
     * @return the value of t_associator.safetyone
     *
     * @mbg.generated
     */
    public String getSafetyone() {
        return safetyone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.safetyone
     *
     * @param safetyone the value for t_associator.safetyone
     *
     * @mbg.generated
     */
    public void setSafetyone(String safetyone) {
        this.safetyone = safetyone == null ? null : safetyone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.safetytwo
     *
     * @return the value of t_associator.safetytwo
     *
     * @mbg.generated
     */
    public String getSafetytwo() {
        return safetytwo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.safetytwo
     *
     * @param safetytwo the value for t_associator.safetytwo
     *
     * @mbg.generated
     */
    public void setSafetytwo(String safetytwo) {
        this.safetytwo = safetytwo == null ? null : safetytwo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_associator.safetythree
     *
     * @return the value of t_associator.safetythree
     *
     * @mbg.generated
     */
    public String getSafetythree() {
        return safetythree;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_associator.safetythree
     *
     * @param safetythree the value for t_associator.safetythree
     *
     * @mbg.generated
     */
    public void setSafetythree(String safetythree) {
        this.safetythree = safetythree == null ? null : safetythree.trim();
    }

    public List<Canteen> getCanteenList() {
        return canteenList;
    }

    public void setCanteenList(List<Canteen> canteenList) {
        this.canteenList = canteenList;
    }

    private Integer canteenCount;

    private String levelName;

    private String telephone;

    private String State;

    private AssociatorAccount associatorAccount;

    public Integer getCanteenCount() {return canteenCount;}

    public String getLevelName() {return levelName;}

    public String getTelephone() {return telephone;}

    public String getState() {return State;}

    public AssociatorAccount getAssociatorAccount() {return associatorAccount;}

    public void setCanteenCount(Integer canteenCount) {this.canteenCount = canteenCount;}

    public void setLevelName(String levelName) {this.levelName = levelName;}

    public void setTelephone(String telephone) {this.telephone = telephone;}

    public void setState(String state) {State = state;}

    public void setAssociatorAccount(AssociatorAccount associatorAccount) {this.associatorAccount = associatorAccount;}

    private Boolean sexType;

    private String sex;

    private Integer level;

    public Boolean getSexType() {return sexType;}

    public String getSex() {return sex;}

    public void setSexType(Boolean sexType) {this.sexType = sexType;}

    public void setSex(String sex) {this.sex = sex==null?null:sex.trim();}

    private String canteenId;//当前用户进入的餐厅编号

    public String getCanteenId() {return canteenId;}

    public void setCanteenId(String canteenId) {this.canteenId = canteenId==null?null:canteenId.trim();}

    private String payPassword;

    public String getPayPassword() {return payPassword;}

    public void setPayPassword(String payPassword) {this.payPassword=payPassword==null?null:payPassword.trim();}


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String nickName;

    public String getNickName() {return nickName;}

    public void setNickName(String nickName) {this.nickName = nickName;}


    private AssociatorShare share;

    public AssociatorShare getShare() {return share;}

    public void setShare(AssociatorShare share) {this.share = share;}


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getBuyService() {
        return buyService;
    }

    public void setBuyService(String buyService) {
        this.buyService = buyService;
    }
}