package com.lswd.youpin.model;

import java.util.Date;

public class AssociatorPayBill {

    private String userName;
    private String payTypeName;
    private Associator associator;
    private String cardUid;
    private String payTimeStr;


    public String getPayTimeStr() {
        return payTimeStr;
    }

    public void setPayTimeStr(String payTimeStr) {
        this.payTimeStr = payTimeStr;
    }

    public String getCardUid() {
        return cardUid;
    }

    public void setCardUid(String cardUid) {
        this.cardUid = cardUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public Associator getAssociator() {
        return associator;
    }

    public void setAssociator(Associator associator) {
        this.associator = associator;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_associator_paybill.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_associator_paybill.money
     *
     * @mbg.generated
     */
    private Float money;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_associator_paybill.associator_id
     *
     * @mbg.generated
     */
    private String associatorId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_associator_paybill.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_associator_paybill.pay_time
     *
     * @mbg.generated
     */
    private Date payTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_associator_paybill.pay_type
     *
     * @mbg.generated
     */
    private Integer payType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_associator_paybill.safetyone
     *
     * @mbg.generated
     */
    private String safetyone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_associator_paybill.safetytwo
     *
     * @mbg.generated
     */
    private String safetytwo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_associator_paybill.id
     *
     * @return the value of dh_associator_paybill.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_associator_paybill.id
     *
     * @param id the value for dh_associator_paybill.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_associator_paybill.money
     *
     * @return the value of dh_associator_paybill.money
     *
     * @mbg.generated
     */
    public Float getMoney() {
        return money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_associator_paybill.money
     *
     * @param money the value for dh_associator_paybill.money
     *
     * @mbg.generated
     */
    public void setMoney(Float money) {
        this.money = money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_associator_paybill.associator_id
     *
     * @return the value of dh_associator_paybill.associator_id
     *
     * @mbg.generated
     */
    public String getAssociatorId() {
        return associatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_associator_paybill.associator_id
     *
     * @param associatorId the value for dh_associator_paybill.associator_id
     *
     * @mbg.generated
     */
    public void setAssociatorId(String associatorId) {
        this.associatorId = associatorId == null ? null : associatorId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_associator_paybill.user_id
     *
     * @return the value of dh_associator_paybill.user_id
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_associator_paybill.user_id
     *
     * @param userId the value for dh_associator_paybill.user_id
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_associator_paybill.pay_time
     *
     * @return the value of dh_associator_paybill.pay_time
     *
     * @mbg.generated
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_associator_paybill.pay_time
     *
     * @param payTime the value for dh_associator_paybill.pay_time
     *
     * @mbg.generated
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_associator_paybill.pay_type
     *
     * @return the value of dh_associator_paybill.pay_type
     *
     * @mbg.generated
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_associator_paybill.pay_type
     *
     * @param payType the value for dh_associator_paybill.pay_type
     *
     * @mbg.generated
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_associator_paybill.safetyone
     *
     * @return the value of dh_associator_paybill.safetyone
     *
     * @mbg.generated
     */
    public String getSafetyone() {
        return safetyone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_associator_paybill.safetyone
     *
     * @param safetyone the value for dh_associator_paybill.safetyone
     *
     * @mbg.generated
     */
    public void setSafetyone(String safetyone) {
        this.safetyone = safetyone == null ? null : safetyone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_associator_paybill.safetytwo
     *
     * @return the value of dh_associator_paybill.safetytwo
     *
     * @mbg.generated
     */
    public String getSafetytwo() {
        return safetytwo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_associator_paybill.safetytwo
     *
     * @param safetytwo the value for dh_associator_paybill.safetytwo
     *
     * @mbg.generated
     */
    public void setSafetytwo(String safetytwo) {
        this.safetytwo = safetytwo == null ? null : safetytwo.trim();
    }
}