package com.lswd.youpin.model.lsyp;

public class RoleResource {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_resource.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_resource.role_id
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_resource.resource_id
     *
     * @mbg.generated
     */
    private Integer resourceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_resource.is_delete
     *
     * @mbg.generated
     */
    private Boolean isDelete;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_resource.id
     *
     * @return the value of t_role_resource.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_resource.id
     *
     * @param id the value for t_role_resource.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_resource.role_id
     *
     * @return the value of t_role_resource.role_id
     *
     * @mbg.generated
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_resource.role_id
     *
     * @param roleId the value for t_role_resource.role_id
     *
     * @mbg.generated
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_resource.resource_id
     *
     * @return the value of t_role_resource.resource_id
     *
     * @mbg.generated
     */
    public Integer getResourceId() {
        return resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_resource.resource_id
     *
     * @param resourceId the value for t_role_resource.resource_id
     *
     * @mbg.generated
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_resource.is_delete
     *
     * @return the value of t_role_resource.is_delete
     *
     * @mbg.generated
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_resource.is_delete
     *
     * @param isDelete the value for t_role_resource.is_delete
     *
     * @mbg.generated
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}