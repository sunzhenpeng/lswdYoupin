package com.lswd.youpin.dao.lsypgen;

import com.lswd.youpin.model.lsyp.CounterUserLinked;

public interface CounterUserLinkedMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_count_user_linked
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_count_user_linked
     *
     * @mbg.generated
     */
    int insert(CounterUserLinked record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_count_user_linked
     *
     * @mbg.generated
     */
    int insertSelective(CounterUserLinked record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_count_user_linked
     *
     * @mbg.generated
     */
    CounterUserLinked selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_count_user_linked
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CounterUserLinked record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_count_user_linked
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CounterUserLinked record);
}