package com.lswd.youpin.service.impl;

import com.lswd.youpin.dao.lsyp.UnifiedOrderMapper;
import com.lswd.youpin.model.lsyp.UnifiedOrder;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.UnifiedOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/8/14.
 */
@Service
public class UnifiedOrderServiceImpl implements UnifiedOrderService {
    private final Logger log = LoggerFactory.getLogger(UnifiedOrderServiceImpl.class);
    @Autowired
    private UnifiedOrderMapper unifiedOrderMapper;

    @Override
    public LsResponse getUnifiedOrderByOrderNo(Map<String, String> data) {
        LsResponse lsResponse = new LsResponse();
        String out_trade_no = data.get("out_trade_no");
        String table;
        if (out_trade_no.charAt(out_trade_no.length() - 1) == '1') {
            log.info("商品退款申请，订单号==========" + out_trade_no);
            table = "t_good_order_paid";
        } else {
            table = "t_recipe_order_paid";
        }
        try {
            UnifiedOrder unifiedOrder = unifiedOrderMapper.getUnifiedOrderByOrderNo(out_trade_no, table);
            lsResponse.setData(unifiedOrder);
        } catch (Exception e) {
            log.info("获取统一订单失败={}", e.getMessage());
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public List<UnifiedOrder> getUnifiedOrderList(Map<String, String> data) {
        String out_trade_no = data.get("out_trade_no");
        String table;
        if (out_trade_no.charAt(out_trade_no.length() - 1) == '1') {
            log.info("商品退款申请，订单号==========" + out_trade_no);
            table = "t_good_order_paid";
        } else {
            table = "t_recipe_order_paid";
        }
        try {
            List<UnifiedOrder> unifiedOrder = unifiedOrderMapper.getUnifiedOrderList(out_trade_no, table);
            return unifiedOrder;
        } catch (Exception e) {
            log.info("获取统一订单失败={}", e.getMessage());
        }
        return null;
    }
}
