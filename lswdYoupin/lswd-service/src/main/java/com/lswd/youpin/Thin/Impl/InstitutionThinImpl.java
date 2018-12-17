package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.InstitutionThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.InstitutionService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liruilong on 2017/8/17.
 */
@Component
public class InstitutionThinImpl implements InstitutionThin {
    @Autowired
    private CanteenService canteenService;
    @Autowired
    private InstitutionService institutionService;

    @Override
    public LsResponse deleteInstitution(String institutionId, HttpServletRequest request) {
        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        LsResponse lsResponse=new LsResponse();
        List<Canteen> canteens =(List<Canteen>)canteenService.getAllCanteenByInstitutionId(institutionId, 1, 10).getData();
        if (canteens.size()>1) {
            lsResponse.setMessage(CodeMessage.INSTITUTION_DELETE_LINK_ERR.getMsg());
            lsResponse.setAsFailure();
            return lsResponse;
        }
        DataSourceHandle.setDataSourceType(DataSourceConst.LSCT);
        lsResponse = institutionService.deleteInstitution(institutionId);
        return lsResponse;
    }
}
