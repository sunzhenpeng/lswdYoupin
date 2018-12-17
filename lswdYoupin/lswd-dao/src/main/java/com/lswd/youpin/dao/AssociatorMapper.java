package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.AssociatorMapperGen;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorShare;
import com.lswd.youpin.model.RechargeLog;
import com.lswd.youpin.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssociatorMapper extends AssociatorMapperGen {

    Associator selectByEmplId(@Param(value = "employeeId") String employeeId);

    Integer addAssociator(Associator associator);

    Associator selectByAccount(@Param(value = "account") String account);


    Integer updateLockById(Associator associator);

    Integer deleteById(Associator associator);

    Integer selectAssociatorCount(@Param(value = "keyword") String keyword,@Param(value = "user") User user,
                                  @Param(value = "canteenIds") List<String> canteenIds);

    List<Associator> selectAssociatorList(@Param(value = "keyword") String keyword, @Param(value = "pageSize")Integer pageSize,
                                          @Param(value = "offSet") int offSet,@Param(value = "user") User user,
                                          @Param(value = "canteenIds") List<String> canteenIds);

    Associator selectAssociatorById(@Param(value = "id") Integer id);

    Associator selectByAssociatorId(@Param(value = "associatorId") String associatorId);

    Integer selectLastAssociatorId();

    Associator getAssociatorByAssociatorId(@Param("associatorId")String associatorId);

    List <RechargeLog> getPayLog(@Param(value = "associatorId") String associatorId,
                                 @Param(value = "offSet")Integer offSet,
                                 @Param(value = "pageSize")Integer pageSize,
                                 @Param(value = "keyword")String keyword);

    Integer getPlayLogCount(@Param(value = "associatorId")String associatorId,
                            @Param(value = "keyword") String keyword);


    Associator selectById(Associator associator);

    Double selectMoney(Associator associator);

    Integer deleteCanteen(@Param(value = "canteenId") String canteenId,@Param(value = "associatorId") String associatorId);

    Associator getByOpenId(@Param("openId") String openId);


    AssociatorShare selectShare(@Param(value = "associatorId") String associatorId, @Param(value = "shareId") String shareId);


    void insertShare(AssociatorShare share);

    void updateShare(AssociatorShare share);

    Integer updateById(@Param(value = "id") Integer id, @Param(value = "memberId") String memberId);


}