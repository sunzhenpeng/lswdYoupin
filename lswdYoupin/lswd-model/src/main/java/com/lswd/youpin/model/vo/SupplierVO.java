package com.lswd.youpin.model.vo;

import com.lswd.youpin.model.lsyp.Supplier;

import java.util.List;

/**
 * Created by H61M-K on 2017/8/28.
 */
public class SupplierVO {
    private String canteenId;
    private List<Supplier> suppliers;


    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}
