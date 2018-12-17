package com.lswd.youpin.model;

import java.util.Date;

public class CanteenLocation {

    private Integer id;

    private Double longitude;

    private Double latitude;

    private Double distance;

    private String canteenId;

    private String tenantId;

    private String canteenName;

    private String areaId;

    private String description;

    private Date updateTime;

    private String safetyone;

    private String safetytwo;

    private String safetythree;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId == null ? null : canteenId.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName == null ? null : canteenName.trim();
    }
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSafetyone() {
        return safetyone;
    }

    public void setSafetyone(String safetyone) {
        this.safetyone = safetyone == null ? null : safetyone.trim();
    }

    public String getSafetytwo() {
        return safetytwo;
    }

    public void setSafetytwo(String safetytwo) {
        this.safetytwo = safetytwo == null ? null : safetytwo.trim();
    }

    public String getSafetythree() {
        return safetythree;
    }

    public void setSafetythree(String safetythree) {
        this.safetythree = safetythree == null ? null : safetythree.trim();
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}