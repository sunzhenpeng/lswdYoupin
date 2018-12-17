package com.lswd.youpin.model.lsyp;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/10/13.
 */
public class Orders {

    private List<Map<String,Object>> orders;
    private Boolean needOut;
    private String newAddress;

    public List<Map<String, Object>> getOrders() {return orders;}

    public void setOrders(List<Map<String, Object>> orders) {this.orders = orders;}

    public Boolean getNeedOut() {return needOut;}

    public void setNeedOut(Boolean needOut) {this.needOut = needOut;}

    public String getNewAddress() {return newAddress;}

    public void setNewAddress(String newAddress) {this.newAddress = newAddress==null?null:newAddress.trim();}

}
