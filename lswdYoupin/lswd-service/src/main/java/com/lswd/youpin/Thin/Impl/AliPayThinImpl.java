package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.AliPayThin;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.model.vo.OrderVO;
import com.lswd.youpin.service.AliPayService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by liruilong on 2017/8/10.
 */
@Component
public class AliPayThinImpl implements AliPayThin {

    private final Logger log = LoggerFactory.getLogger(AliPayThinImpl.class);

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private RedisManager redisManager;

    @Override
    public void aliPayNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iterator = requestParams.keySet().iterator(); iterator.hasNext(); ) {
            String name = (String) iterator.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        String out_trade_no = params.get("out_trade_no");
        try {
            OrderVO orderVO = (OrderVO) SerializeUtils.deserialize(redisManager.get(out_trade_no.getBytes()));
            String tradeType = orderVO.getTradeType();
            String canteenId = orderVO.getCanteenId();
            DataSourceHandle.setDataSourceType(canteenId.substring(0, 4));
            //如果是充值操作则切换到公共库，如果是商品或者菜品购买则切换到自己的商家库，餐厅编号的前四位是商家编码，根据前四位进行切库
            aliPayService.notifyUrl(params, orderVO, request, response);
        } catch (Exception e) {
            log.info("java.io.exception={}", e.getMessage());
        }
    }
}
