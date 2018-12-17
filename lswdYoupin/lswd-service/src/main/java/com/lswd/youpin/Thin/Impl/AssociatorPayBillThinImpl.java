package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.AssociatorPayBillThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorPayBillService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by zhenguanqi on 2017/11/22.
 */
@Service
public class AssociatorPayBillThinImpl implements AssociatorPayBillThin {
    private final Logger logger = LoggerFactory.getLogger(AssociatorPayBillThinImpl.class);

    @Autowired
    private AssociatorPayBillService associatorPayBillService;

    @Override
    public LsResponse getAssociatorPayBillList(String keyword, String canteenId, String date, Integer pageNum, Integer pageSize, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorPayBillService.getAssociatorPayBillList(keyword,canteenId,date,pageNum,pageSize,user);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }
}
