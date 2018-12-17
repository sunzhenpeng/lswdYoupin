package com.lswd.youpin.Thin;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2017/6/10.
 */
public interface AssociatorThin {

    LsResponse lock(Integer id,Boolean lock);

    LsResponse deleteAssociator(Integer id);

    LsResponse updateAssociator(Associator associator);

    LsResponse getAssociatorList(String keyword, Integer pageNum, Integer pageSize,User user,String canteenId);

    LsResponse canteenList(String associatorId,User user);

    LsResponse lookAssociator(Integer id);

    LsResponse bindCanteen(String data,Associator Associator);

    LsResponse pay(String associatorId,String keyword,Integer pageSize,Integer pageNum);

    LsResponse seeAssociator(Associator associator);

    LsResponse updateLoginPwd(String data,Associator associator);

    LsResponse updatePayPwd(String data, Associator associator);

    LsResponse updatePhone(String data, Associator associator);

    LsResponse getMoney(Associator associator);

    LsResponse removeCanteen(String canteenId, Associator associator);

    LsResponse isBindCanteen(String canteenId, Associator associator);

    LsResponse addPhone(String tel, Associator associator);

    LsResponse bindCard(String data, HttpServletRequest request);
}
