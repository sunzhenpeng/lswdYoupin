package com.lswd.youpin.service.impl;

import com.lswd.youpin.dao.LeaveInfoMapper;
import com.lswd.youpin.model.LeaveInfo;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.LeaveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by H61M-K on 2018/3/12.
 */
@Service
public class LeaveInfoServiceImpl implements LeaveInfoService {
    @Autowired
    private LeaveInfoMapper leaveInfoMapper;

    @Override
    public LsResponse get() {
        LsResponse lsResponse = new LsResponse();
        List<LeaveInfo> leaveInfoList = leaveInfoMapper.get();
        lsResponse.setData(leaveInfoList);
        return lsResponse;
    }
}
