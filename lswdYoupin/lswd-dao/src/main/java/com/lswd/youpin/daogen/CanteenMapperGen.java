package com.lswd.youpin.daogen;

import com.lswd.youpin.model.Canteen;

public interface CanteenMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_canteen
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_canteen
     *
     * @mbg.generated
     */
    int insert(Canteen record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_canteen
     *
     * @mbg.generated
     */
    int insertSelective(Canteen record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_canteen
     *
     * @mbg.generated
     */
    Canteen selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_canteen
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Canteen record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_canteen
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Canteen record);
}