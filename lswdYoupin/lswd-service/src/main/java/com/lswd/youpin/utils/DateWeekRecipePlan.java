package com.lswd.youpin.utils;

import com.lswd.youpin.model.lsyp.RecipePlan;

/**
 * Created by zhenguanqi on 2017/7/10.
 */
public class DateWeekRecipePlan {

    private String date;
    private String weekday;
    private RecipePlan recipePlan;

    private String[] morning;
    private String[] noon;
    private String[] dinner;

    public String[] getMorning() {
        return morning;
    }

    public void setMorning(String[] morning) {
        this.morning = morning;
    }

    public String[] getNoon() {
        return noon;
    }

    public void setNoon(String[] noon) {
        this.noon = noon;
    }

    public String[] getDinner() {
        return dinner;
    }

    public void setDinner(String[] dinner) {
        this.dinner = dinner;
    }

    public String getDate() {return date;}

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public RecipePlan getRecipePlan() {
        return recipePlan;
    }

    public void setRecipePlan(RecipePlan recipePlan) {
        this.recipePlan = recipePlan;
    }
}
