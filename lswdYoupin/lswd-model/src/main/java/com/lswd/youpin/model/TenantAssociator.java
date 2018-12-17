package com.lswd.youpin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TenantAssociator implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_tenant_associator.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_tenant_associator.associator_id
     *
     * @mbg.generated
     */
    private String associatorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_tenant_associator.account
     *
     * @mbg.generated
     */
    private String account;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_tenant_associator.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_tenant_associator.telephone
     *
     * @mbg.generated
     */
    private String telephone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_tenant_associator.tenant_id
     *
     * @mbg.generated
     */
    private String tenantId;
    /**
     * 判断是否删除
     */
    private Boolean isDelete;

    private List<Canteen>canteenList;

    private Integer canteenCount;

    private String nickname;

    private Integer status;

    private String state;


    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_tenant_associator.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_tenant_associator.safetyone
     *
     * @mbg.generated
     */
    private String safetyone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_tenant_associator.safetytwo
     *
     * @mbg.generated
     */
    private String safetytwo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_tenant_associator.id
     *
     * @return the value of t_tenant_associator.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_tenant_associator.id
     *
     * @param id the value for t_tenant_associator.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_tenant_associator.associator_id
     *
     * @return the value of t_tenant_associator.associator_id
     *
     * @mbg.generated
     */
    public String getAssociatorId() {
        return associatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_tenant_associator.associator_id
     *
     * @param associatorId the value for t_tenant_associator.associator_id
     *
     * @mbg.generated
     */
    public void setAssociatorId(String associatorId) {
        this.associatorId = associatorId == null ? null : associatorId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_tenant_associator.account
     *
     * @return the value of t_tenant_associator.account
     *
     * @mbg.generated
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_tenant_associator.account
     *
     * @param account the value for t_tenant_associator.account
     *
     * @mbg.generated
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_tenant_associator.password
     *
     * @return the value of t_tenant_associator.password
     *
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_tenant_associator.password
     *
     * @param password the value for t_tenant_associator.password
     *
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_tenant_associator.telephone
     *
     * @return the value of t_tenant_associator.telephone
     *
     * @mbg.generated
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_tenant_associator.telephone
     *
     * @param telephone the value for t_tenant_associator.telephone
     *
     * @mbg.generated
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_tenant_associator.tenant_id
     *
     * @return the value of t_tenant_associator.tenant_id
     *
     * @mbg.generated
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_tenant_associator.tenant_id
     *
     * @param tenantId the value for t_tenant_associator.tenant_id
     *
     * @mbg.generated
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    /**
     *
     * @return
     */
    public Boolean getDelete() {
        return isDelete;
    }

    /**
     *
     * @param delete
     */
    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_tenant_associator.update_time
     *
     * @return the value of t_tenant_associator.update_time
     *
     * @mbg.generated
     */

    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_tenant_associator.update_time
     *
     * @param updateTime the value for t_tenant_associator.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_tenant_associator.safetyone
     *
     * @return the value of t_tenant_associator.safetyone
     *
     * @mbg.generated
     */
    public String getSafetyone() {
        return safetyone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_tenant_associator.safetyone
     *
     * @param safetyone the value for t_tenant_associator.safetyone
     *
     * @mbg.generated
     */
    public void setSafetyone(String safetyone) {
        this.safetyone = safetyone == null ? null : safetyone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_tenant_associator.safetytwo
     *
     * @return the value of t_tenant_associator.safetytwo
     *
     * @mbg.generated
     */
    public String getSafetytwo() {
        return safetytwo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_tenant_associator.safetytwo
     *
     * @param safetytwo the value for t_tenant_associator.safetytwo
     *
     * @mbg.generated
     */
    public void setSafetytwo(String safetytwo) {
        this.safetytwo = safetytwo == null ? null : safetytwo.trim();
    }


    public List<Canteen> getCanteenList() {return canteenList;}

    public Integer getCanteenCount() {return canteenCount;}

    public String getNickname() {return nickname;}

    public Integer getStatus() {return status;}

    public String getState() {return state;}

    public void setCanteenList(List<Canteen> canteenList) {this.canteenList = canteenList;}

    public void setCanteenCount(Integer canteenCount) {this.canteenCount = canteenCount;}

    public void setNickname(String nickname) {this.nickname = nickname;}

    public void setStatus(Integer status) {this.status = status;}

    public void setState(String state) {this.state = state;}
}