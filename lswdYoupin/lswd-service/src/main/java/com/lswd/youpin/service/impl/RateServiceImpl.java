package com.lswd.youpin.service.impl;

import com.lswd.youpin.dao.lsyp.RateMapper;
import com.lswd.youpin.model.lsyp.Rate;
import com.lswd.youpin.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/14 0014.
 */
@Service
public class RateServiceImpl implements RateService {

    private final Logger log = LoggerFactory.getLogger(RateServiceImpl.class);
    @Autowired
    private RateMapper rateMapper;

    @Override
    public Rate getRateByCanteenId(String canteenId) {
        try {
            Rate rate = rateMapper.getByCanteenId(canteenId);
            return rate;
        } catch (Exception e) {
            log.info("获取餐厅费率信息失败={}", e.getMessage());
        }
        return null;
    }
}
