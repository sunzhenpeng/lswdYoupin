package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.SupplierMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Material;
import com.lswd.youpin.model.lsyp.Supplier;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhao on 2017/6/7.
 */
@Service
public class SupplierServiceImpl implements SupplierService {
    private final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public List<Supplier> getSupplierByCanteenId(String canteenId) {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            suppliers = supplierMapper.getCanteenLinkSupplierByCanteenId(canteenId);
        } catch (Exception e) {
            log.error("供货商查询失败：{}", e.getMessage());
        }
        return suppliers;
    }

    @Override
    public LsResponse addSupplier(Supplier supplier, User user) {

        LsResponse lsResponse = new LsResponse();
        try {
            if (supplier != null) {
                supplier.setUpdateTime(Dates.now());
                supplier.setUpdateUser(user.getUsername());
                int id = supplierMapper.selLastSupplierId();
                supplier.setSupplierId("s" + String.valueOf(1001 + id));
                supplier.setIsDelete(false);
                supplier.setAddress(supplier.getAddress() + "/" + supplier.getDetailAddress());
                int b = supplierMapper.insertSupplier(supplier);
                if (b > 0) {
                    lsResponse.setMessage(CodeMessage.SUPPLIER_ADD_SUCCESS.name());
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.SUPPLIER_ADD_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.SUPPLIER_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.setMessage(e.toString());
            lsResponse.setErrorCode("500");
            lsResponse.setSuccess(false);
        }
        return lsResponse;
    }


    @Override
    public LsResponse deleteSupplier(Integer id, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            Supplier supplier = new Supplier();
            supplier.setId(id);
            supplier.setIsDelete(true);
            supplier.setUpdateTime(Dates.now());
            int b = supplierMapper.deleteById(supplier);
            if (b > 0) {
                lsResponse.setMessage(CodeMessage.SUPPLIER_DELETE_SUCCESS.name());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.SUPPLIER_DELETE_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.setSuccess(false);
            lsResponse.setMessage(e.toString());
            lsResponse.setErrorCode("500");
        }
        return lsResponse;
    }


    @Override
    public LsResponse updateSupplier(Supplier supplier, User user) {
        LsResponse lsResponse = new LsResponse();
        if (supplier != null) {
            supplier.setUpdateTime(Dates.now());
            supplier.setUpdateUser(user.getUsername());
            supplier.setAddress(supplier.getAddress() + "/" + supplier.getDetailAddress());
            if (supplier.getBank() == null) {
                supplier.setBank("");
            }
            if (supplier.getBankcode() == null) {
                supplier.setBankcode("");
            }
            if (supplier.getDescription() == null) {
                supplier.setDescription("");
            }

            try {
                int b = supplierMapper.updateSupplier(supplier);
                if (b > 0) {
                    lsResponse.setMessage(CodeMessage.SUPPLIER_UPDATE_SUCCESS.name());
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.SUPPLIER_UPDATE_ERR.name());
                }
            } catch (Exception e) {
                log.error(e.toString());
                lsResponse.setMessage(e.toString());
                lsResponse.setSuccess(false);
                lsResponse.setErrorCode("500");
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.SUPPLIER_NO_ERR.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getSupplierList(User user, Integer pageNum, Integer pageSize, String keyword) {
        LsResponse lsResponse = new LsResponse();
        try {
            int offSet = 0;
            if (keyword != null && !"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = "%" + keyword + "%";
            } else {
                keyword = "";
            }
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = supplierMapper.getSupplierCount(keyword);
            List<Supplier> supplierList = supplierMapper.selectSuppliers(keyword, pageSize, offSet);
            if (supplierList != null && supplierList.size() > 0) {
                lsResponse.setData(supplierList);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.SUPPLIER_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.setSuccess(false);
            lsResponse.setMessage(e.toString());
            lsResponse.setErrorCode("500");
        }
        return lsResponse;
    }

    @Override
    public LsResponse lookSupplier(String supplierId) {
        LsResponse lsResponse = new LsResponse();
        try {
            Supplier supplier = supplierMapper.selectBySupplierId(supplierId);
            if (supplier != null) {
                String address = supplier.getAddress();
                if (address != null && !"".equals(address)) {
                    String[] addressList = address.split("/");
                    supplier.setDetailAddress(addressList[addressList.length - 1]);
                    address = "";
                    for (int i = 0; i < addressList.length - 1; i++) {
                        address += addressList[i] + "/";
                    }
                    supplier.setAddress(address.substring(0, address.length() - 1));
                }
                lsResponse.setData(supplier);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.SUPPLIER_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.setErrorCode("500");
            lsResponse.setSuccess(false);
            lsResponse.setMessage(e.toString());
        }
        return lsResponse;
    }

    @Override
    public List<Supplier> getSuppliers() {
        return supplierMapper.getSuppliers();
    }

    @Override
    public LsResponse getSupplierListByCanteenId(String canteenId) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (canteenId != null && !"".equals(canteenId)) {
                canteenId = new String(canteenId.getBytes("iso8859-1"), "utf-8");
            } else {
                canteenId = "";
            }
            List<Supplier> suppliers = supplierMapper.getSuppliersByCanteenId(canteenId);
            if (suppliers != null && suppliers.size() > 0) {
                lsResponse.setData(suppliers);
            } else {
                lsResponse.checkSuccess(false,CodeMessage.SUPPLIER_NO_ERR.name());
            }
        } catch (Exception e) {
           log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getSuplierMaterials(String keyword, Integer pageNum, Integer pageSize, String supplierId) {
        LsResponse lsResponse = new LsResponse();
        try {
            int offSet = 0;
            if (keyword != null && !"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = "%" + keyword + "%";
            } else {
                keyword = "";
            }
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = supplierMapper.getSuplierMaterialCount(supplierId, keyword);
            List<Material> materialList = supplierMapper.getSuplierMaterials(supplierId, keyword, offSet, pageSize);
            if (materialList != null && materialList.size() > 0) {
                lsResponse.setData(materialList);
                lsResponse.setTotalCount(total);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCanteenSupplierList(String keyword, String canteenId, Integer pageNum, Integer pageSize) {
        log.info("{} is being executed. canteenId = {}", "getCanteenSupplierList", JSON.toJSON(canteenId));
        LsResponse lsResponse = new LsResponse();
        String name = "";
        Integer offset = null;
        if (pageNum != null && pageSize != null) {
            offset = (pageNum - 1) * pageSize;
        }
        try {
            if (keyword != null && !(keyword.equals(""))) {
                name = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
            }
            //获取餐厅绑定的供应商列表
            List<Supplier> suppliers = supplierMapper.getCanteenLinkSupplierByCanteenId(canteenId);
            //获取总的供应商列表
            List<Supplier> supplierList = supplierMapper.selectSuppliers(name, pageSize, offset);
            Integer count=supplierMapper.getSupplierCount(name);
            for (Supplier supplier : supplierList) {
                for (Supplier supplier1 :suppliers) {
                    if (supplier.getSupplierId().equals(supplier1.getSupplierId())) {
                        supplier.setChecked(true);
                    }
                }
            }
            lsResponse.setData(supplierList);
            lsResponse.setTotalCount(count);
        } catch (Exception e) {
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SUPPLIER_NO_ERR.getMsg());
            log.info("获取餐厅列表失败={}", e.getMessage());
        }
        return lsResponse;
    }


}
