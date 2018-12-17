package com.lswd.youpin.service;

import com.lswd.youpin.model.lsyp.UnifiedOrder;
import com.lswd.youpin.response.LsResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 2017/8/14.
 */
public interface UnifiedOrderService {
    LsResponse getUnifiedOrderByOrderNo(Map<String, String> data);
    List<UnifiedOrder> getUnifiedOrderList(Map<String, String> data);
}
