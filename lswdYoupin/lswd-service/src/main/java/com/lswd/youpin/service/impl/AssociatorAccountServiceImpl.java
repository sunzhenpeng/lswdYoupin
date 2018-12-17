package com.lswd.youpin.service.impl;

import com.lswd.youpin.dao.AssociatorAccountMapper;
import com.lswd.youpin.model.AssociatorAccount;
import com.lswd.youpin.service.AssociatorAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liruilong on 2017/8/14 0014.
 */
@Service
public class AssociatorAccountServiceImpl implements AssociatorAccountService {
    private final Logger log = LoggerFactory.getLogger(AssociatorAccountServiceImpl.class);
    @Autowired
    private AssociatorAccountMapper associatorAccountMapper;

    @Override
    public int update(AssociatorAccount associatorAccount) {
        try {
            int i = associatorAccountMapper.updateSelective(associatorAccount);
            return i;
        } catch (Exception e) {
            log.info("会员账户更新失败：{}", e.getMessage());
        }
        return 0;
    }
}
