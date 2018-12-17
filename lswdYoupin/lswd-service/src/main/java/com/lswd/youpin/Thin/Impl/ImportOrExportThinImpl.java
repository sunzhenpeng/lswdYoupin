package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.ImportOrExportThin;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.ImportOrExportService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhenguanqi on 2017/8/8.
 */
@Service
public class ImportOrExportThinImpl implements ImportOrExportThin {
    @Autowired
    private CanteenService canteenService;
    @Autowired
    private ImportOrExportService importOrExportService;

    @Override
    public LsResponse exportExeclList(User user, Integer flag, HttpServletResponse response) {
        LsResponse lsResponse = null;
        try {
           String[] canteenIds = user.getCanteenIds().split(",");
            List<Canteen> canteens = new ArrayList<>();
            Canteen canteen = null;
            String dataSource = DataSourceHandle.getDataSourceType();
            if(!DataSourceConst.LSWD.equals(dataSource)){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            for (int i=0;i<canteenIds.length;i++){
                 canteen = (Canteen)canteenService.getCanteenByCanteenId(canteenIds[i]).getData();
                 if (canteen != null){
                     canteens.add(canteen);
                 }
            }
            DataSourceHandle.setDataSourceType(dataSource);
            lsResponse = importOrExportService.exportExeclList(canteenIds,canteens,flag,response);
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setMessage(e.toString());
            lsResponse.setSuccess(false);
        }
        return lsResponse;
    }
}
