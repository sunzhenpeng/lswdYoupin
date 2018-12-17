package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.AssociatorRefundBillThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorRefundBillService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhenguanqi on 2017/11/30.
 */
@Service
public class AssociatorRefundBillThinImpl implements AssociatorRefundBillThin{

    @Autowired
    private AssociatorRefundBillService associatorRefundBillService;

    @Override
    public LsResponse getAssociatorRefundBillList(String keyword, String canteenId, String date, Integer pageNum, Integer pageSize, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorRefundBillService.getAssociatorRefundBillList(keyword,canteenId,date,pageNum,pageSize,user);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }
}
