package com.lswd.youpin.model.lsyp;

import java.util.Date;
import java.util.List;

/**
 * Created by liuhao on 2017/6/20.
 */
public class MaterialCategory {
    private Integer id;
    private String name;
    private String canteenId;
    private short level;
    private Integer parentId;
    private String createUser;
    private String updateUser;
    private String description;
    private Date updateTime;
    private Date createTime;
    private List<MaterialCategory>childrens;
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public short getLevel() {
        return level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public String getDescription() {
        return description;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<MaterialCategory> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<MaterialCategory> childrens) {
        this.childrens = childrens;
    }

    public String getCanteenId() {return canteenId;}

    public void setCanteenId(String canteenId) {this.canteenId=canteenId==null?null:canteenId.toString();}

    private String canteenName;

    public String getCanteenName() {return canteenName;}

    public void setCanteenName(String canteenName) {this.canteenName = canteenName==null?null:canteenName.trim();}
}
