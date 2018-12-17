package com.lswd.youpin.model.vo;

public class MealStatisticVO {
    private Float breakfastAmount;
    private Float lunchAmount;
    private Float supperAmount;
    private Float totalAmount;
    private String payTime;

    public Float getBreakfastAmount() {
        return breakfastAmount;
    }

    public void setBreakfastAmount(Float breakfastAmount) {
        this.breakfastAmount = breakfastAmount;
    }

    public Float getLunchAmount() {
        return lunchAmount;
    }

    public void setLunchAmount(Float lunchAmount) {
        this.lunchAmount = lunchAmount;
    }

    public Float getSupperAmount() {
        return supperAmount;
    }

    public void setSupperAmount(Float supperAmount) {
        this.supperAmount = supperAmount;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
