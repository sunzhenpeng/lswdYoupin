package com.lswd.youpin.dao.lsypgen;

import com.lswd.youpin.model.lsyp.RecipePlanItems;

public interface RecipePlanItemsMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_planitems
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_planitems
     *
     * @mbg.generated
     */
    int insert(RecipePlanItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_planitems
     *
     * @mbg.generated
     */
    int insertSelective(RecipePlanItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_planitems
     *
     * @mbg.generated
     */
    RecipePlanItems selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_planitems
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RecipePlanItems record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_planitems
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RecipePlanItems record);
}