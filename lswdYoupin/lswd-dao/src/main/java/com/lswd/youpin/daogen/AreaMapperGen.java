package com.lswd.youpin.daogen;

import com.lswd.youpin.model.Area;

public interface AreaMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_area
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_area
     *
     * @mbg.generated
     */
    int insert(Area record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_area
     *
     * @mbg.generated
     */
    int insertSelective(Area record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_area
     *
     * @mbg.generated
     */
    Area selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_area
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Area record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_area
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Area record);
}