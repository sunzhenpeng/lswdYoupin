package com.lswd.youpin.Thin;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.vo.SupplierVO;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liruilong on 2017/6/14.
 */
public interface CanteenThin {
    LsResponse getAllCanteen(User u,String institutionId,String canteenName, Integer pageNum, Integer pageSize);

    LsResponse getAllCanteenPart(User u,String institutionId,String canteenName,Integer pageNum,Integer pageSize);

    LsResponse addCanteenSupplierLink(SupplierVO supplierVO, User u);

    LsResponse getUserCanteenList(String canteenIds);

    LsResponse getCanteenByCanteenId(String canteenId,User u);

}
