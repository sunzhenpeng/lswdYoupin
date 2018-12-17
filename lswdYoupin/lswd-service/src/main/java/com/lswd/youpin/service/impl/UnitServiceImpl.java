package com.lswd.youpin.service.impl;

import com.lswd.youpin.model.lsyp.Unit;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MaterialService;
import com.lswd.youpin.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhenguanqi on 2017/7/19.
 */
@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private MaterialService materialService;

    @Override
    public LsResponse getUnitListAll() {
        LsResponse lsResponse = new LsResponse();
        try {
            List<Unit> units = materialService.getUnits();
            for (Unit unit : units){
                unit.setUnitLen(unit.getUnit());
                unit.setUnit(null);
            }
            if (units != null && units.size() >0) {
                lsResponse.setData(units);
            } else {
                lsResponse.checkSuccess(false, "单位下拉列表查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setMessage(e.toString());
            lsResponse.setSuccess(false);
        }
        return lsResponse;
    }
}
