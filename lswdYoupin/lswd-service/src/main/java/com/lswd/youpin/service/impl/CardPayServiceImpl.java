package com.lswd.youpin.service.impl;

import com.lswd.youpin.dao.lsyp.MemberCardMapper;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CardPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liruilong on 2017/8/10.
 */
@Service
public class CardPayServiceImpl implements CardPayService {
    private final Logger log = LoggerFactory.getLogger(CardPayServiceImpl.class);
    @Autowired
    private MemberCardMapper memberCardMapper;

    @Override
    public LsResponse getBalance(String memberId) {
        log.info("开始查询会员的餐卡余额，memberId===={}", memberId);
        LsResponse lsResponse = new LsResponse();
        if (memberId == null) {
            lsResponse.setAsFailure();
            lsResponse.setMessage("请重新绑卡");
            return lsResponse;
        }
        try {
            Float balance = memberCardMapper.getBalance(memberId);
            lsResponse.setData(balance);
            lsResponse.setAsSuccess();
        } catch (Exception e) {
            log.error("餐卡余额查询失败====={}", e.getMessage());
            lsResponse.setAsFailure();
            lsResponse.setMessage("餐卡余额查询失败");
        }
        return lsResponse;
    }
}
