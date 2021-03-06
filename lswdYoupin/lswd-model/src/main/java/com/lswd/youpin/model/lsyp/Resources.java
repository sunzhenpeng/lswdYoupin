package com.lswd.youpin.model.lsyp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Resources implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.resource_code
     *
     * @mbg.generated
     */
    private String resourceCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.resource_name
     *
     * @mbg.generated
     */
    private String resourceName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.permission
     *
     * @mbg.generated
     */
    private String permission;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.url
     *
     * @mbg.generated
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.level
     *
     * @mbg.generated
     */
    private Short level;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.sequence
     *
     * @mbg.generated
     */
    private Integer sequence;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.parent_id
     *
     * @mbg.generated
     */
    private Integer parentId;


    private Boolean flag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.resource_type
     *
     * @mbg.generated
     */
    private Short resourceType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.create_time
     *
     * @mbg.generated
     */
    private Date createTime;


    private List<Resources> childrens;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.safetyone
     *
     * @mbg.generated
     */
    private String safetyone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_resource.safetytwo
     *
     * @mbg.generated
     */
    private String safetytwo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.id
     *
     * @return the value of t_resource.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.id
     *
     * @param id the value for t_resource.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.resource_code
     *
     * @return the value of t_resource.resource_code
     *
     * @mbg.generated
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.resource_code
     *
     * @param resourceCode the value for t_resource.resource_code
     *
     * @mbg.generated
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode == null ? null : resourceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.resource_name
     *
     * @return the value of t_resource.resource_name
     *
     * @mbg.generated
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.resource_name
     *
     * @param resourceName the value for t_resource.resource_name
     *
     * @mbg.generated
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.permission
     *
     * @return the value of t_resource.permission
     *
     * @mbg.generated
     */
    public String getPermission() {
        return permission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.permission
     *
     * @param permission the value for t_resource.permission
     *
     * @mbg.generated
     */
    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.url
     *
     * @return the value of t_resource.url
     *
     * @mbg.generated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.url
     *
     * @param url the value for t_resource.url
     *
     * @mbg.generated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.level
     *
     * @return the value of t_resource.level
     *
     * @mbg.generated
     */
    public Short getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.level
     *
     * @param level the value for t_resource.level
     *
     * @mbg.generated
     */
    public void setLevel(Short level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.sequence
     *
     * @return the value of t_resource.sequence
     *
     * @mbg.generated
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.sequence
     *
     * @param sequence the value for t_resource.sequence
     *
     * @mbg.generated
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.parent_id
     *
     * @return the value of t_resource.parent_id
     *
     * @mbg.generated
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.parent_id
     *
     * @param parentId the value for t_resource.parent_id
     *
     * @mbg.generated
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.resource_type
     *
     * @return the value of t_resource.resource_type
     *
     * @mbg.generated
     */
    public Short getResourceType() {
        return resourceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.resource_type
     *
     * @param resourceType the value for t_resource.resource_type
     *
     * @mbg.generated
     */
    public void setResourceType(Short resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.update_time
     *
     * @return the value of t_resource.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.update_time
     *
     * @param updateTime the value for t_resource.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.create_time
     *
     * @return the value of t_resource.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.create_time
     *
     * @param createTime the value for t_resource.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.safetyone
     *
     * @return the value of t_resource.safetyone
     *
     * @mbg.generated
     */
    public String getSafetyone() {
        return safetyone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.safetyone
     *
     * @param safetyone the value for t_resource.safetyone
     *
     * @mbg.generated
     */
    public void setSafetyone(String safetyone) {
        this.safetyone = safetyone == null ? null : safetyone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_resource.safetytwo
     *
     * @return the value of t_resource.safetytwo
     *
     * @mbg.generated
     */
    public String getSafetytwo() {
        return safetytwo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_resource.safetytwo
     *
     * @param safetytwo the value for t_resource.safetytwo
     *
     * @mbg.generated
     */
    public void setSafetytwo(String safetytwo) {
        this.safetytwo = safetytwo == null ? null : safetytwo.trim();
    }

    public List<Resources> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Resources> childrens) {
        this.childrens = childrens;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}