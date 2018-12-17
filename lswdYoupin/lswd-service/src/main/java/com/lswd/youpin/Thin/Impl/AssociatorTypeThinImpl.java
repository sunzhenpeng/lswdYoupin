package com.lswd.youpin.Thin.Impl;

import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.Thin.AssociatorTypeThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorType;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorTypeService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AssociatorTypeThinImpl implements AssociatorTypeThin{
    private final Logger log = LoggerFactory.getLogger(AssociatorTypeThin.class);
    @Autowired
    private AssociatorTypeService associatorTypeService;

    @Override
    public LsResponse getAssociatorTypeList(String name) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorTypeService.getAssociatorTypeList(name);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addAssociatorType(AssociatorType associatorType, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorTypeService.addAssociatorType(associatorType,user);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateAssociatorType(AssociatorType associatorType, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorTypeService.updateAssociatorType(associatorType,user);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

}
