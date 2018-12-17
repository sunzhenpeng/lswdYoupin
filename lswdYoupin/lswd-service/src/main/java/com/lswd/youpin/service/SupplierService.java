package com.lswd.youpin.service;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Supplier;
import com.lswd.youpin.response.LsResponse;

import java.util.List;

/**
 * Created by liuhao on 2017/6/7.
 */
public interface SupplierService {
    List<Supplier> getSupplierByCanteenId(String canteenId);

    LsResponse addSupplier(Supplier supplier, User user);

    LsResponse deleteSupplier(Integer id, User user);

    LsResponse updateSupplier(Supplier supplier, User user);

    LsResponse getSupplierList(User user, Integer pageNum, Integer pageSize, String keyword);

    LsResponse lookSupplier(String supplierId);

    List<Supplier> getSuppliers();
    LsResponse getSupplierListByCanteenId(String canteenId);

    LsResponse getSuplierMaterials(String keyword, Integer pageNum, Integer pageSize, String supplierId);

    LsResponse getCanteenSupplierList(String keyword, String canteenId, Integer pageNum, Integer pageSize);


}
