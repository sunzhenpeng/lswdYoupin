package com.lswd.youpin.model;

public class Area {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.area_id
     *
     * @mbg.generated
     */
    private String areaId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.area
     *
     * @mbg.generated
     */
    private String area;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.parent_id
     *
     * @mbg.generated
     */
    private String parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.safetyone
     *
     * @mbg.generated
     */
    private String safetyone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_area.safetytwo
     *
     * @mbg.generated
     */
    private String safetytwo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.id
     *
     * @return the value of t_area.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.id
     *
     * @param id the value for t_area.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.area_id
     *
     * @return the value of t_area.area_id
     *
     * @mbg.generated
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.area_id
     *
     * @param areaId the value for t_area.area_id
     *
     * @mbg.generated
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.area
     *
     * @return the value of t_area.area
     *
     * @mbg.generated
     */
    public String getArea() {
        return area;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.area
     *
     * @param area the value for t_area.area
     *
     * @mbg.generated
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.parent_id
     *
     * @return the value of t_area.parent_id
     *
     * @mbg.generated
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.parent_id
     *
     * @param parentId the value for t_area.parent_id
     *
     * @mbg.generated
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.safetyone
     *
     * @return the value of t_area.safetyone
     *
     * @mbg.generated
     */
    public String getSafetyone() {
        return safetyone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.safetyone
     *
     * @param safetyone the value for t_area.safetyone
     *
     * @mbg.generated
     */
    public void setSafetyone(String safetyone) {
        this.safetyone = safetyone == null ? null : safetyone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_area.safetytwo
     *
     * @return the value of t_area.safetytwo
     *
     * @mbg.generated
     */
    public String getSafetytwo() {
        return safetytwo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_area.safetytwo
     *
     * @param safetytwo the value for t_area.safetytwo
     *
     * @mbg.generated
     */
    public void setSafetytwo(String safetytwo) {
        this.safetytwo = safetytwo == null ? null : safetytwo.trim();
    }
}