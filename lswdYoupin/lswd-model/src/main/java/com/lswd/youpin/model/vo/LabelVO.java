package com.lswd.youpin.model.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/11/27.
 */
public class LabelVO {
    private String canteenId;
    private String recipeId;
    private int diskTypeId;
   /* private Date startTime;
    private Date endTime;*/
    private List<String> labels;

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public int getDiskTypeId() {
        return diskTypeId;
    }

    public void setDiskTypeId(int diskTypeId) {
        this.diskTypeId = diskTypeId;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    /*public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }*/

}
