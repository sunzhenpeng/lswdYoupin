package com.lswd.youpin.dao.lsypgen;

import com.lswd.youpin.model.lsyp.MemberRefundBill;

public interface MemberRefundBillMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_refundbill
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_refundbill
     *
     * @mbg.generated
     */
    int insert(MemberRefundBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_refundbill
     *
     * @mbg.generated
     */
    int insertSelective(MemberRefundBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_refundbill
     *
     * @mbg.generated
     */
    MemberRefundBill selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_refundbill
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MemberRefundBill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_member_refundbill
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MemberRefundBill record);
}