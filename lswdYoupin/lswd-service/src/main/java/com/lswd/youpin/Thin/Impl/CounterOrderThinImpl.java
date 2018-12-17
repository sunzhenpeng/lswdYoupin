package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.CounterOrderThin;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.lsyp.Members;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.CounterOrderService;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhenguanqi on 2017/12/14.
 */
@Service
public class CounterOrderThinImpl implements CounterOrderThin{

    @Autowired
    private CounterOrderService counterOrderService;
    @Autowired
    private CanteenService canteenService;

    @Override
    public LsResponse getMemberListBT(CounterUser counterUser, String memberName, String memberTel, String memberCardUid, Integer typeId, String counterId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        lsResponse = counterOrderService.getMemberListBT(counterUser, memberName, memberTel, memberCardUid, typeId, counterId, pageNum, pageSize);
        if (lsResponse.getData() == null){
           return lsResponse;
        }
        List<Members> members = (List<Members>)lsResponse.getData();
        DataSourceHandle.setDataSourceType(ConstantsCode.LSWD_DB);
        if (canteenService.getCanteenByCanteenId(members.get(0).getCanteenId()).getData() == null){
            return lsResponse;
        }
        Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(members.get(0).getCanteenId()).getData();
        for (Members mem : members){
            mem.setCanteenName(canteen.getCanteenName());
        }
//        List<Canteen> canteens = (List<Canteen>) canteenService.getUserCanteenList(counterUser.getCanteenIds()).getData();
//        for (Members mem : members){
//            for (Canteen canteen : canteens){
//                if (mem.getCanteenId().equals(canteen.getCanteenId())){
//                    mem.setCanteenName(canteen.getCanteenName());
//                    break;
//                }
//            }
//        }
        return lsResponse;
    }
}
