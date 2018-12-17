package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.CounterThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.CounterUserLinked;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterService;
import com.lswd.youpin.service.CounterUserService;
import com.lswd.youpin.service.UserService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/11/16.
 */
@Service
public class CounterThinImpl implements CounterThin{
    @Autowired
    private CounterService counterService;
    @Autowired
    private CounterUserService counterUserService;

    @Override
    public LsResponse getBingCounterUserList(String keyword,String counterId) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<CounterUserLinked> linkedes = new ArrayList<>();
        List<CounterUser> counterUsers = new ArrayList<>();
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            lsResponse = counterService.getCounterUserLinkedList(counterId);
            if (lsResponse.getData() != null){
                linkedes = (List<CounterUserLinked>)lsResponse.getData();
            }
            if (!dataSource.equals(DataSourceConst.LSWD)){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = counterService.getBingCounterUserList(keyword,counterId);
            if (lsResponse.getData() != null){
                counterUsers = (List<CounterUser>)lsResponse.getData();
            }
            if (linkedes != null && linkedes.size() > 0) {
                for (CounterUser user : counterUsers) {
                    for (CounterUserLinked linked : linkedes) {
                        if (user.getUserId().equals(linked.getUserId())) {
                            user.setChecked(true);
                        }
                    }
                }
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterUserLinkedList(String counterId,String keyword) {
        LsResponse lsResponse;
        CounterUserLinked userLinked = new CounterUserLinked();
        lsResponse = counterService.getCounterUserLinkedList(counterId,keyword);
        if (lsResponse.getData() != null){
            try {
                DataSourceHandle.setDataSourceType(ConstantsCode.LSWD_DB);
                userLinked = (CounterUserLinked) lsResponse.getData();
                String userId = userLinked.getUserId();
                CounterUser user = counterUserService.getCounterUserByUserId(userId);
                if (user != null){
                    userLinked.setCounterUser(user);
                }else {
                    userLinked.setCounterUser(new CounterUser());
                }
            }catch (Exception e){
                lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            }
        }
        return lsResponse;
    }
}
