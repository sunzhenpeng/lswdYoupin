package com.lswd.youpin.daogen;

import com.lswd.youpin.model.Button;

public interface ButtonMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_button
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_button
     *
     * @mbg.generated
     */
    int insert(Button record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_button
     *
     * @mbg.generated
     */
    int insertSelective(Button record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_button
     *
     * @mbg.generated
     */
    Button selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_button
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Button record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_button
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Button record);
}