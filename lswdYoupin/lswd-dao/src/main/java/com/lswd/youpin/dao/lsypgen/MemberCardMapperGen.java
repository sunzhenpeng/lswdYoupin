package com.lswd.youpin.dao.lsypgen;

import com.lswd.youpin.model.lsyp.MemberCard;

public interface MemberCardMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_card
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer cardid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_card
     *
     * @mbg.generated
     */
    int insert(MemberCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_card
     *
     * @mbg.generated
     */
    int insertSelective(MemberCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_card
     *
     * @mbg.generated
     */
    MemberCard selectByPrimaryKey(Integer cardid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_card
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MemberCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_card
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MemberCard record);
}