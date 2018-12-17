package com.lswd.youpin.model.lsyp;

/**
 * Created by liuhao on 2017/7/14.
 */
public class Unit {
    private Integer id;
    private String unit;

    private String unitLen;

    public String getUnitLen() {
        return unitLen;
    }

    public void setUnitLen(String unitLen) {
        this.unitLen = unitLen;
    }

    public Integer getId() {return id;}

    public String getUnit() {return unit;}

    public void setId(Integer id) {this.id = id;}

    public void setUnit(String unit) {this.unit = unit;}
}
