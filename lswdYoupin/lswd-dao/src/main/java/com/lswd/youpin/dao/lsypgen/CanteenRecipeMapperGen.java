package com.lswd.youpin.dao.lsypgen;

import com.lswd.youpin.model.lsyp.CanteenRecipe;

public interface CanteenRecipeMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipes_canteen
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipes_canteen
     *
     * @mbg.generated
     */
    int insert(CanteenRecipe record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipes_canteen
     *
     * @mbg.generated
     */
    int insertSelective(CanteenRecipe record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipes_canteen
     *
     * @mbg.generated
     */
    CanteenRecipe selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipes_canteen
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CanteenRecipe record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipes_canteen
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CanteenRecipe record);
}