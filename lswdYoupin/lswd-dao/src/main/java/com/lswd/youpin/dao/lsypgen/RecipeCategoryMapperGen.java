package com.lswd.youpin.dao.lsypgen;

import com.lswd.youpin.model.lsyp.RecipeCategory;

public interface RecipeCategoryMapperGen {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_category
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_category
     *
     * @mbg.generated
     */
    int insert(RecipeCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_category
     *
     * @mbg.generated
     */
    int insertSelective(RecipeCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_category
     *
     * @mbg.generated
     */
    RecipeCategory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_category
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RecipeCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_recipe_category
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RecipeCategory record);
}