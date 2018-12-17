package com.lswd.youpin.Thin.Impl;

import com.alibaba.fastjson.JSON;
import com.lswd.youpin.Thin.OrderThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.lsyp.Orders;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.OrderService;
import com.lswd.youpin.service.impl.StatisticsServiceImpl;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/8/30.
 */
@Component
public class OrderThinImpl implements OrderThin {
    private final Logger log = LoggerFactory.getLogger(OrderThinImpl.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private CanteenService canteenService;
    @Override
//    public LsResponse addGenerateOrder(List<Map<String, Object>> carOrders, Associator associator) {
//        LsResponse lsResponse=new LsResponse();
//        try {
//            String dateScore= DataSourceHandle.getDataSourceType();
//            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
//            Canteen canteen=(Canteen)canteenService.getCanteenByCanteenId(associator.getCanteenId()).getData();
//            String endTime=canteen.getDeadline();
//            String []times=endTime.split(":");
//            Integer time=Integer.parseInt(times[0])*60+Integer.parseInt(times[1]);
//            DataSourceHandle.setDataSourceType(dateScore);
//            lsResponse=orderService.addGenerateOrder(carOrders,associator,time);
//        } catch (NumberFormatException e) {
//             log.error(e.toString());
//            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
//        }
//        return lsResponse;
//    }
    public LsResponse addGenerateOrder(Orders orders, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            System.out.println(JSON.toJSONString(orders));
            lsResponse.setData(orders);
            String dateScore = DataSourceHandle.getDataSourceType();
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(associator.getCanteenId()).getData();
            String endTime = canteen.getDeadline();
            String[] times = endTime.split(":");
            Integer time = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
            DataSourceHandle.setDataSourceType(dateScore);
            lsResponse = orderService.addGenerateOrder(orders, associator, time,canteen.getOutFee());
        } catch (NumberFormatException e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getOrdersCount(Associator associator) {
        LsResponse lsResponse=new LsResponse();
        try {
            String dataScorec=DataSourceHandle.getDataSourceType();
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            LsResponse ls=canteenService.getCanteenByCanteenId(associator.getCanteenId());
            Boolean flag=false;
            if(ls.getData()!=null)
            {
              Canteen canteen=(Canteen)ls.getData();
              flag= canteen.getAssess();
            }
            DataSourceHandle.setDataSourceType(dataScorec);
            lsResponse=orderService.getOrdersCount(associator,flag);
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }
}
