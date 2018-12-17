package com.lswd.youpin.model.lsyp;

import java.util.List;

/**
 * Created by liuhao on 2017/8/12.
 */
public class EvaluateAdd {
    private Boolean type;
    private List<Evaluate> evaluates;
    private String orderId;

    public Boolean getType() {return type;}

    public List<Evaluate> getEvaluates() {return evaluates;}

    public void setType(Boolean type) {this.type = type;}

    public void setEvaluates(List<Evaluate> evaluates) {this.evaluates = evaluates;}

    public String getOrderId() {return orderId;}

    public void setOrderId(String orderId) {this.orderId = orderId==null?null:orderId.trim();}
}
