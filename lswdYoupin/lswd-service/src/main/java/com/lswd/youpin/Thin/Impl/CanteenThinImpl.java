package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.CanteenThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Institution;
import com.lswd.youpin.model.vo.SupplierVO;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.InstitutionService;
import com.lswd.youpin.service.SupplierService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liruilong on 2017/6/14.
 */
@Component
public class CanteenThinImpl implements CanteenThin {


    @Autowired
    private CanteenService canteenService;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private SupplierService supplierService;

    private final Logger log = LoggerFactory.getLogger(CanteenThinImpl.class);

    @Override
    public LsResponse getAllCanteen(User u, String institutionId, String canteenName, Integer pageNum, Integer pageSize) {

        LsResponse lsResponse = new LsResponse();
        try {
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            lsResponse = canteenService.getAllCanteen(u.getCanteenIds(), institutionId, canteenName, pageNum, pageSize);
            List<Canteen> canteens = (List<Canteen>) lsResponse.getData();
            DataSourceHandle.setDataSourceType(u.getTenantId().substring(0, 4));
            for (Canteen canteen : canteens) {
                canteen.setSuppliers(supplierService.getSupplierByCanteenId(canteen.getCanteenId()));
            }
            lsResponse.setData(canteens);
        } catch (Exception e) {
            lsResponse.setMessage("供货商查询失败");
            log.error("查询餐厅关联的供货商失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAllCanteenPart(User u, String institutionId, String canteenName, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        String currentSourceType = DataSourceHandle.getDataSourceType();
        try {
            lsResponse = canteenService.getAllCanteenPart(u, institutionId, canteenName, pageNum, pageSize);
        } catch (Exception e) {
            lsResponse.setMessage("餐厅查询失败");
            log.error("餐厅查询失败：{}", e.getMessage());
        } finally {
            DataSourceHandle.setDataSourceType(currentSourceType);
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCanteenSupplierLink(SupplierVO supplierVO, User u) {
        DataSourceHandle.setDataSourceType(u.getUsername().substring(0,4));
        LsResponse lsResponse = new LsResponse();
        try {
            lsResponse = canteenService.addCanteenSupplierLink(supplierVO);
        } catch (Exception e) {
            lsResponse.setMessage("供货商添加失败");
            log.error("供货商添加失败：={}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getUserCanteenList(String canteenIds) {
        LsResponse lsResponse = new LsResponse();
        try {
            lsResponse = canteenService.getUserCanteenList(canteenIds);
        } catch (Exception e) {
            lsResponse.setMessage("供货商查询失败");
            log.error("查询餐厅关联的供货商失败：{}", e.getMessage());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCanteenByCanteenId(String canteenId, User u) {
        LsResponse lsResponse = new LsResponse();
        String code = u.getTenantId().substring(0,4);
        try {
            Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(canteenId).getData();
            DataSourceHandle.setDataSourceType(code);
            Institution institution = (Institution) institutionService.getInstitutionByInstitutionId(canteen.getInstitutionId()).getData();
            canteen.setInstitutionName(institution.getInstitutionName());
            lsResponse.setData(canteen);
        } catch (Exception e) {
            lsResponse.setMessage(CodeMessage.CANTEEN_SELECT_ERR.getMsg());
            log.error("查询餐厅失败：{}", e.getMessage());
        }
        return lsResponse;
    }
}
