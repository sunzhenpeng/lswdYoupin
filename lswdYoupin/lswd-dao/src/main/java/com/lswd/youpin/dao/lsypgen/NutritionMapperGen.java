package com.lswd.youpin.dao.lsypgen;

import com.lswd.youpin.model.lsyp.Nutrition;

public interface NutritionMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_nutrition
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer nutritionid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_nutrition
     *
     * @mbg.generated
     */
    int insert(Nutrition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_nutrition
     *
     * @mbg.generated
     */
    int insertSelective(Nutrition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_nutrition
     *
     * @mbg.generated
     */
    Nutrition selectByPrimaryKey(Integer nutritionid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_nutrition
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Nutrition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dh_nutrition
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Nutrition record);
}