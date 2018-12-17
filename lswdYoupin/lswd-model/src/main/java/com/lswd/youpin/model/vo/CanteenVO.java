package com.lswd.youpin.model.vo;

import com.lswd.youpin.model.Canteen;

/**
 * Created by liruilong on 2017/6/10.
 */
public class CanteenVO {
    private Canteen canteen;
    private Double rate;

    public Canteen getCanteen() {
        return canteen;
    }

    public void setCanteen(Canteen canteen) {
        this.canteen = canteen;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
