package com.lswd.youpin.model.lsyp;

public class Nutrition {

    private Float amount;//含量

    private Integer ingredientid;

    public Integer getIngredientid() {
        return ingredientid;
    }

    public void setIngredientid(Integer ingredientid) {
        this.ingredientid = ingredientid;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_nutrition.nutritionid
     *
     * @mbg.generated
     */
    private Integer nutritionid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_nutrition.nutritionname
     *
     * @mbg.generated
     */
    private String nutritionname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_nutrition.calorie
     *
     * @mbg.generated
     */
    private Float calorie;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_nutrition.protein
     *
     * @mbg.generated
     */
    private Float protein;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_nutrition.fat
     *
     * @mbg.generated
     */
    private Float fat;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_nutrition.carbonhydrate
     *
     * @mbg.generated
     */
    private Float carbonhydrate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_nutrition.vc
     *
     * @mbg.generated
     */
    private Float vc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_nutrition.isupload
     *
     * @mbg.generated
     */
    private Byte isupload;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dh_nutrition.usednum
     *
     * @mbg.generated
     */
    private Long usednum;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_nutrition.nutritionid
     *
     * @return the value of dh_nutrition.nutritionid
     *
     * @mbg.generated
     */
    public Integer getNutritionid() {
        return nutritionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_nutrition.nutritionid
     *
     * @param nutritionid the value for dh_nutrition.nutritionid
     *
     * @mbg.generated
     */
    public void setNutritionid(Integer nutritionid) {
        this.nutritionid = nutritionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_nutrition.nutritionname
     *
     * @return the value of dh_nutrition.nutritionname
     *
     * @mbg.generated
     */
    public String getNutritionname() {
        return nutritionname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_nutrition.nutritionname
     *
     * @param nutritionname the value for dh_nutrition.nutritionname
     *
     * @mbg.generated
     */
    public void setNutritionname(String nutritionname) {
        this.nutritionname = nutritionname == null ? null : nutritionname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_nutrition.calorie
     *
     * @return the value of dh_nutrition.calorie
     *
     * @mbg.generated
     */
    public Float getCalorie() {
        return calorie;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_nutrition.calorie
     *
     * @param calorie the value for dh_nutrition.calorie
     *
     * @mbg.generated
     */
    public void setCalorie(Float calorie) {
        this.calorie = calorie;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_nutrition.protein
     *
     * @return the value of dh_nutrition.protein
     *
     * @mbg.generated
     */
    public Float getProtein() {
        return protein;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_nutrition.protein
     *
     * @param protein the value for dh_nutrition.protein
     *
     * @mbg.generated
     */
    public void setProtein(Float protein) {
        this.protein = protein;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_nutrition.fat
     *
     * @return the value of dh_nutrition.fat
     *
     * @mbg.generated
     */
    public Float getFat() {
        return fat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_nutrition.fat
     *
     * @param fat the value for dh_nutrition.fat
     *
     * @mbg.generated
     */
    public void setFat(Float fat) {
        this.fat = fat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_nutrition.carbonhydrate
     *
     * @return the value of dh_nutrition.carbonhydrate
     *
     * @mbg.generated
     */
    public Float getCarbonhydrate() {
        return carbonhydrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_nutrition.carbonhydrate
     *
     * @param carbonhydrate the value for dh_nutrition.carbonhydrate
     *
     * @mbg.generated
     */
    public void setCarbonhydrate(Float carbonhydrate) {
        this.carbonhydrate = carbonhydrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_nutrition.vc
     *
     * @return the value of dh_nutrition.vc
     *
     * @mbg.generated
     */
    public Float getVc() {
        return vc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_nutrition.vc
     *
     * @param vc the value for dh_nutrition.vc
     *
     * @mbg.generated
     */
    public void setVc(Float vc) {
        this.vc = vc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_nutrition.isupload
     *
     * @return the value of dh_nutrition.isupload
     *
     * @mbg.generated
     */
    public Byte getIsupload() {
        return isupload;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_nutrition.isupload
     *
     * @param isupload the value for dh_nutrition.isupload
     *
     * @mbg.generated
     */
    public void setIsupload(Byte isupload) {
        this.isupload = isupload;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dh_nutrition.usednum
     *
     * @return the value of dh_nutrition.usednum
     *
     * @mbg.generated
     */
    public Long getUsednum() {
        return usednum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dh_nutrition.usednum
     *
     * @param usednum the value for dh_nutrition.usednum
     *
     * @mbg.generated
     */
    public void setUsednum(Long usednum) {
        this.usednum = usednum;
    }
}