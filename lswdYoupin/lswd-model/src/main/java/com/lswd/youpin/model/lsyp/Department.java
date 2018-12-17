package com.lswd.youpin.model.lsyp;

import java.util.Date;

public class Department {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.department_id
     *
     * @mbg.generated
     */
    private String departmentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.department_name
     *
     * @mbg.generated
     */
    private String departmentName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.leader
     *
     * @mbg.generated
     */
    private String leader;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.telephone
     *
     * @mbg.generated
     */
    private String telephone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.level
     *
     * @mbg.generated
     */
    private Short level;



    /**
     * 判断是否删除
     */
    private Boolean isDelete;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.parent_id
     *
     * @mbg.generated
     */
    private String parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.institution_id
     *
     * @mbg.generated
     */
    private String institutionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.update_user
     *
     * @mbg.generated
     */
    private String updateUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.safetyone
     *
     * @mbg.generated
     */
    private String safetyone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.safetytwo
     *
     * @mbg.generated
     */
    private String safetytwo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.id
     *
     * @return the value of t_department.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.id
     *
     * @param id the value for t_department.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.department_id
     *
     * @return the value of t_department.department_id
     *
     * @mbg.generated
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.department_id
     *
     * @param departmentId the value for t_department.department_id
     *
     * @mbg.generated
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.department_name
     *
     * @return the value of t_department.department_name
     *
     * @mbg.generated
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.department_name
     *
     * @param departmentName the value for t_department.department_name
     *
     * @mbg.generated
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.leader
     *
     * @return the value of t_department.leader
     *
     * @mbg.generated
     */
    public String getLeader() {
        return leader;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.leader
     *
     * @param leader the value for t_department.leader
     *
     * @mbg.generated
     */
    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.telephone
     *
     * @return the value of t_department.telephone
     *
     * @mbg.generated
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.telephone
     *
     * @param telephone the value for t_department.telephone
     *
     * @mbg.generated
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.level
     *
     * @return the value of t_department.level
     *
     * @mbg.generated
     */
    public Short getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.level
     *
     * @param level the value for t_department.level
     *
     * @mbg.generated
     */
    public void setLevel(Short level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.parent_id
     *
     * @return the value of t_department.parent_id
     *
     * @mbg.generated
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.parent_id
     *
     * @param parentId the value for t_department.parent_id
     *
     * @mbg.generated
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.institution_id
     *
     * @return the value of t_department.institution_id
     *
     * @mbg.generated
     */
    public String getInstitutionId() {
        return institutionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.institution_id
     *
     * @param institutionId the value for t_department.institution_id
     *
     * @mbg.generated
     */
    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId == null ? null : institutionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.update_user
     *
     * @return the value of t_department.update_user
     *
     * @mbg.generated
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.update_user
     *
     * @param updateUser the value for t_department.update_user
     *
     * @mbg.generated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.is_delete
     *
     * @param isDelete the value for t_user.is_delete
     *
     * @mbg.generated
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.update_time
     *
     * @return the value of t_department.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.update_time
     *
     * @param updateTime the value for t_department.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.safetyone
     *
     * @return the value of t_department.safetyone
     *
     * @mbg.generated
     */
    public String getSafetyone() {
        return safetyone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.safetyone
     *
     * @param safetyone the value for t_department.safetyone
     *
     * @mbg.generated
     */
    public void setSafetyone(String safetyone) {
        this.safetyone = safetyone == null ? null : safetyone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.safetytwo
     *
     * @return the value of t_department.safetytwo
     *
     * @mbg.generated
     */
    public String getSafetytwo() {
        return safetytwo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.safetytwo
     *
     * @param safetytwo the value for t_department.safetytwo
     *
     * @mbg.generated
     */
    public void setSafetytwo(String safetytwo) {
        this.safetytwo = safetytwo == null ? null : safetytwo.trim();
    }
}