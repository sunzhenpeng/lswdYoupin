package com.lswd.youpin.commons;

/**
 * Created by liuhao on 2017/9/23.
 */
public enum  RecipeType {
    other(0,"其他"),
    breakfast(1,"早餐"),
    lunch(2,"午餐"),
    dinner(3,"晚餐"),
    supper(4,"夜宵");
    private Integer code;
    private String name;

    public Integer getCode() {return code;}

    public void setCode(Integer code) {this.code = code;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    RecipeType(Integer code,String name)
    {
        this.code=code;
        this.name=name;
    }

    @Override
    public String toString() {
        return "RecipeType{"+"code="+code+";name="+name+";}";
    }

    public static RecipeType getRecipeType(Integer code)
    {
        for (RecipeType recipeType : RecipeType.values()) {
            if (recipeType.getCode()==code) {
                return recipeType;
            }
        }
        return null;
    }


}
