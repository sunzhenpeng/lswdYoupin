package com.lswd.youpin.model.lsyp;

import java.util.Date;

public class GoodSale {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_good_sale.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_good_sale.good_id
     *
     * @mbg.generated
     */
    private String goodId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_good_sale.sale_time
     *
     * @mbg.generated
     */
    private Date saleTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_good_sale.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_good_sale.id
     *
     * @return the value of t_good_sale.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_good_sale.id
     *
     * @param id the value for t_good_sale.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_good_sale.good_id
     *
     * @return the value of t_good_sale.good_id
     *
     * @mbg.generated
     */
    public String getGoodId() {
        return goodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_good_sale.good_id
     *
     * @param goodId the value for t_good_sale.good_id
     *
     * @mbg.generated
     */
    public void setGoodId(String goodId) {
        this.goodId = goodId == null ? null : goodId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_good_sale.sale_time
     *
     * @return the value of t_good_sale.sale_time
     *
     * @mbg.generated
     */
    public Date getSaleTime() {
        return saleTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_good_sale.sale_time
     *
     * @param saleTime the value for t_good_sale.sale_time
     *
     * @mbg.generated
     */
    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_good_sale.update_time
     *
     * @return the value of t_good_sale.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_good_sale.update_time
     *
     * @param updateTime the value for t_good_sale.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}