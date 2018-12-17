package com.lswd.youpin.dao;

import com.lswd.youpin.daogen.AssociatorAccountMapperGen;
import com.lswd.youpin.model.AssociatorAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssociatorAccountMapper extends AssociatorAccountMapperGen {

    List<AssociatorAccount> getInfo(@Param("associatorId") String associatorId);

    AssociatorAccount getAccountInfo(@Param("associatorId") String associatorId);

    AssociatorAccount getByAccountType(@Param("associatorId") String associatorId, @Param("accountType") short accountType);

    Integer updateSelective(AssociatorAccount associatorAccount);

    Integer updateAccount(String associatorId);


}