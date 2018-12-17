package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorCanteen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.WeixinOauth2Token;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by SAMA on 2017/6/6.
 */
public interface AssociatorService {

    LsResponse  registerAssociator(String data);

    LsResponse login(Associator associator);

    LsResponse lock(Integer id,Boolean lock);

    LsResponse canteenList(String associatorId,User user);

    LsResponse deleteAssociator(Integer id);

    LsResponse updateAssociator(Associator associator);

    LsResponse getAssociatorList(String keyword, Integer pageNum, Integer pageSize, User user,String canteenId);

    LsResponse lookAssociator(Integer id);

    LsResponse bindCanteen(String associatorId, String canteenId);

    LsResponse getAssociatorByAssociatorId(String associatorId);

    LsResponse getAssociatorByAccount(String account);

    LsResponse getPay(String associatorId,String keyword,Integer pageSize,Integer pageNum);

    LsResponse getAssociatorById(Associator associator);

    LsResponse updatePassword(String data);

    LsResponse updateLoginPwd(String data,Associator associator);

    LsResponse updatePayPwd(String data, Associator associator);

    LsResponse updatePhone(String data, Associator associator);

    LsResponse getMoney(Associator associator);

    LsResponse deleteCanteen(String canteenId, Associator associator);

    Integer updateBindCanteen(AssociatorCanteen associatorCanteen);

    LsResponse addPhone(String tel, Associator associator);

    LsResponse add(WeixinOauth2Token wat,String associatorId);

    Integer updateById(Integer id,String memberId);

    LsResponse getBalance(String memberId);

}
