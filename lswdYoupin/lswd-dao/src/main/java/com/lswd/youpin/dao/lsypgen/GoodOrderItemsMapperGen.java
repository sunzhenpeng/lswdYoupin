package com.lswd.youpin.dao.lsypgen;

import com.lswd.youpin.model.lsyp.GoodOrderItems;

public interface GoodOrderItemsMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_good_order_items
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_good_order_items
     *
     * @mbg.generated
     */
    int insert(GoodOrderItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_good_order_items
     *
     * @mbg.generated
     */
    int insertSelective(GoodOrderItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_good_order_items
     *
     * @mbg.generated
     */
    GoodOrderItems selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_good_order_items
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(GoodOrderItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_good_order_items
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(GoodOrderItems record);
}