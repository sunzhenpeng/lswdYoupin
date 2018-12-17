package com.lswd.youpin.Thin;

import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by zhenguanqi on 2017/12/14.
 */
public interface CounterOrderThin {

    LsResponse getMemberListBT(CounterUser counterUser, String memberName, String memberTel, String memberCardUid, Integer typeId, String counterId, Integer pageNum, Integer pageSize);
}
