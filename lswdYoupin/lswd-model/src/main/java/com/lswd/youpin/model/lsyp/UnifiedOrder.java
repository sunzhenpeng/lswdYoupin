package com.lswd.youpin.model.lsyp;

public class UnifiedOrder {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_unified_order.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_unified_order.order_no
     *
     * @mbg.generated
     */
    private String orderNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_unified_order.amount
     *
     * @mbg.generated
     */
    private Float amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_unified_order.order_type
     *
     * @mbg.generated
     */
    private Short orderType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_unified_order.id
     *
     * @return the value of t_unified_order.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_unified_order.id
     *
     * @param id the value for t_unified_order.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_unified_order.order_no
     *
     * @return the value of t_unified_order.order_no
     *
     * @mbg.generated
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_unified_order.order_no
     *
     * @param orderNo the value for t_unified_order.order_no
     *
     * @mbg.generated
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_unified_order.amount
     *
     * @return the value of t_unified_order.amount
     *
     * @mbg.generated
     */
    public Float getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_unified_order.amount
     *
     * @param amount the value for t_unified_order.amount
     *
     * @mbg.generated
     */
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_unified_order.order_type
     *
     * @return the value of t_unified_order.order_type
     *
     * @mbg.generated
     */
    public Short getOrderType() {
        return orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_unified_order.order_type
     *
     * @param orderType the value for t_unified_order.order_type
     *
     * @mbg.generated
     */
    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }
}