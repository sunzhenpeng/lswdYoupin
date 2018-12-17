package com.lswd.youpin.model.vo;

public class NutritionVo {

    private String recipeId;
    private Integer mealRecordId;
    private Float calorie = 0F;//卡路里
    private Float protein = 0F;//蛋白质
    private Float fat = 0F;//脂肪
    private Float carbonhydrate = 0F;//碳水化合物
    private Float vc = 0F;//维生素C

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public Float getCalorie() {
        return calorie;
    }

    public void setCalorie(Float calorie) {
        this.calorie = calorie;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getCarbonhydrate() {
        return carbonhydrate;
    }

    public void setCarbonhydrate(Float carbonhydrate) {
        this.carbonhydrate = carbonhydrate;
    }

    public Float getVc() {
        return vc;
    }

    public void setVc(Float vc) {
        this.vc = vc;
    }
}
