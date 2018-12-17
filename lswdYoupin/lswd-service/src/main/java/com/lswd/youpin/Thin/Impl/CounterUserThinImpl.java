package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.CounterUserThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterUserService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhenguanqi on 2017/11/29.
 */
@Service
public class CounterUserThinImpl implements CounterUserThin {
    @Autowired
    private CounterUserService counterUserService;

    @Override
    public LsResponse getCounterUserList(String keyword, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if (!dataSource.equals(DataSourceConst.LSWD)) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = counterUserService.getCounterUserList(keyword, pageNum, pageSize);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCounterUser(CounterUser counterUser, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if (!dataSource.equals(DataSourceConst.LSWD)) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = counterUserService.addCounterUser(counterUser, user);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounterUser(CounterUser counterUser, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if (!dataSource.equals(DataSourceConst.LSWD)) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = counterUserService.updateCounterUser(counterUser, user);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounterUser(Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if (!dataSource.equals(DataSourceConst.LSWD)) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = counterUserService.deleteCounterUser(id);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounterUserStatus(Integer id, Boolean status) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if (!dataSource.equals(DataSourceConst.LSWD)) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = counterUserService.updateCounterUserStatus(id, status);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse resetCounterUserPass(Integer id, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if (!dataSource.equals(DataSourceConst.LSWD)) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = counterUserService.resetCounterUserPass(id, user);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

}
