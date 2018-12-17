package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.StatisticsThin;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodStatisticsOrder;
import com.lswd.youpin.model.lsyp.RecipeStatisricsOrder;
import com.lswd.youpin.model.lsyp.Statistics;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.StatisticsService;
import com.lswd.youpin.service.WeiXinService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/7/31.
 */
@Component
public class StatisticsThinImpl implements StatisticsThin {
    private final Logger log = LoggerFactory.getLogger(StatisticsThinImpl.class);
    @Autowired
    private CanteenService canteenService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private WeiXinService weiXinService;
    @Override
    public LsResponse getCanteenOrders(User user) {
        LsResponse lsResponse=new LsResponse();
        Map<String,Object>map=new HashedMap();
        String dataSourceType=DataSourceHandle.getDataSourceType();
        try {
            if (!DataSourceConst.LSWD.equals(dataSourceType)) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            List<Canteen> canteenList = (List<Canteen>) canteenService.getUserCanteenList(user.getCanteenIds()).getData();
            DataSourceHandle.setDataSourceType(dataSourceType);
            if (canteenList != null && canteenList.size() > 0) {
                List<String> canteenName = new ArrayList<>();
                List<Statistics> list = new ArrayList<>();
                for (int i = 0; i < canteenList.size(); i++) {
                    canteenName.add(canteenList.get(i).getCanteenName());
                    Statistics s = new Statistics();
                    Integer value = (Integer) statisticsService.getCateenOrders(canteenList.get(i).getCanteenId()).getData();
                    s.setValue(value);
                    s.setName(canteenList.get(i).getCanteenName());
                    list.add(s);
                }
                map.put("canteenName", canteenName);
                map.put("canteenData", list);
                lsResponse.setData(map);
            }

        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getSalesCount(String startDate, String endDate, String canteenId,TenantAssociator tenantAssociator) {
        LsResponse lsResponse=new LsResponse();
        Map<String,Object>map=new HashedMap();
        try {
            List<Canteen> canteens = new ArrayList<>();
            List<String> canteenIds = getCanteenIds(canteenId, tenantAssociator);
            if (canteenIds != null && canteenIds.size() > 0) {
                lsResponse = statisticsService.getSalesCount(startDate, endDate, canteenIds);
                Statistics statistics = (Statistics) lsResponse.getData();
                if (statistics != null) {
                    map.put("startTime", startDate);
                    map.put("endTime", endDate);
                    map.put("restaurantArray", canteens);
                    map.put("totalmoney", statistics.getPriceAll());
                    map.put("refundmoney", statistics.getRefundAll());
                    map.put("shopmoney", statistics.getGoodPrice());
                    map.put("shopCount", statistics.getGoodOrderCount());
                    map.put("goodPaidMoney",statistics.getGoodPaidPrice());
                    map.put("recipePaidMoney",statistics.getRecipePaidPrice());
                    map.put("dishmoney", statistics.getRecipePrice());
                    map.put("dishCount", statistics.getRecipeOrderCount());
                    lsResponse.setData(map);
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.STATISTICS_NO_SALES.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.STATISTICS_NO_SALES.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }

        return lsResponse;
    }
    @Override
    public LsResponse goodSalesCount(String date, Integer pageSize, Integer pageNum, String canteenId, TenantAssociator tenantAssociator) {
        LsResponse lsResponse = new LsResponse();
        try {
            Map<String,Object>map=new HashedMap();
            List<String> canteenIds=getCanteenIds(canteenId,tenantAssociator);
            if(canteenIds!=null&&canteenIds.size()>0){
            lsResponse = statisticsService.goodSalesCount(date, pageSize, pageNum, canteenIds);
            List<GoodStatisticsOrder> goodStatisticsOrders=( List<GoodStatisticsOrder>)lsResponse.getData();
            if(goodStatisticsOrders==null||goodStatisticsOrders.size()<=0)
            {
                lsResponse.checkSuccess(false, CodeMessage.STATISTICS_NO_SALES.name());
                map.put("date",Dates.now());
                map.put("order","没有销售信息");
                lsResponse.setData(map);
            }else{
                List<GoodStatisticsOrder>goodStatisticsOrderList=new ArrayList<>();
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                List<Canteen>canteens=(List<Canteen>)canteenService.getTenantAssociatorCanteenList(tenantAssociator.getAssociatorId()).getData();
                for (int i = 0; i < goodStatisticsOrders.size(); i++) {
                    GoodStatisticsOrder goodStatisticsOrder = goodStatisticsOrders.get(i);
                    for (int j = 0; j < canteens.size(); j++) {
                        Canteen can = canteens.get(j);
                        if (can.getCanteenId().equals(goodStatisticsOrder.getCanteenId())) {
                            goodStatisticsOrder.setCanteenName(can.getCanteenName());
                            break;
                        }
                    }
                    goodStatisticsOrderList.add(goodStatisticsOrder);
                }
                map.put("date",Dates.now());
                map.put("order",goodStatisticsOrderList);
                lsResponse.setData(map);
            }
            }else{
                map.put("date",Dates.now());
                map.put("order","没有销售信息");
                lsResponse.setData(map);
                lsResponse.checkSuccess(false,CodeMessage.STATISTICS_NO_SALES.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse recipeSalesCount(String date, Integer pageSize, Integer pageNum, String canteenId, TenantAssociator tenantAssociator) {
        LsResponse lsResponse=new LsResponse();
        Map<String,Object>map=new HashedMap();
        try {
            List<String> canteenIds=getCanteenIds(canteenId,tenantAssociator);
            if(canteenIds!=null&&canteenIds.size()>0){
                lsResponse = statisticsService.recipeSalesCount(date, pageSize, pageNum, canteenIds);
                List<RecipeStatisricsOrder> recipeStatisricsOrders = (List<RecipeStatisricsOrder>) lsResponse.getData();
                if (recipeStatisricsOrders == null || recipeStatisricsOrders.size() <= 0) {
                    lsResponse.checkSuccess(false, CodeMessage.STATISTICS_NO_SALES.name());
                    map.put("date", Dates.now());
                    lsResponse.setData(map);
            }else{
                    List<RecipeStatisricsOrder> recipeStatisricsOrderList = new ArrayList<>();
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                    List<Canteen> canteens = (List<Canteen>) canteenService.getTenantAssociatorCanteenList(tenantAssociator.getAssociatorId()).getData();
                    for (int i = 0; i < recipeStatisricsOrders.size(); i++) {
                        RecipeStatisricsOrder recipeStatisticsOrder = recipeStatisricsOrders.get(i);
                        for (int j = 0; j < canteens.size(); j++) {
                            Canteen can = canteens.get(j);
                            if (can.getCanteenId().equals(recipeStatisticsOrder.getCanteenId())) {
                                recipeStatisticsOrder.setCanteenName(can.getCanteenName());
                                break;
                            }
                        }
                        recipeStatisricsOrderList.add(recipeStatisticsOrder);
                    }
                map.put("date",Dates.now());
                map.put("order",recipeStatisricsOrderList);
                lsResponse.setData(map);
            }
            }else{
                lsResponse.checkSuccess(false, CodeMessage.STATISTICS_NO_SALES.name());
                map.put("date",Dates.now());
                lsResponse.setData(map);
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return  lsResponse;
    }

    private List<String> getCanteenIds(String canteenId,TenantAssociator tenantAssociator)
    {
        List<String> canteenIds=new ArrayList<>();
        if (canteenId != null&&!"".equals(canteenId)) {
            canteenIds.add(canteenId);
        } else {
            String dataSource = DataSourceHandle.getDataSourceType();
            if (!DataSourceConst.LSWD.equals(dataSource)) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            List<Canteen> canteens = (List<Canteen>) weiXinService.lookCanteen(tenantAssociator.getAssociatorId()).getData();
            DataSourceHandle.setDataSourceType(dataSource);
            if (canteens != null && canteens.size() > 0) {
                for (int i = 0; i < canteens.size(); i++) {
                    canteenIds.add(canteens.get(i).getCanteenId());
                }
            }
        }
        return canteenIds;
    }

    @Override
    public LsResponse payTypeMoney(String canteenId, User user, String startDate,String endDate) {
        LsResponse lsResponse = new LsResponse();
        Map<String, Object> map = new HashedMap();
        String[] payTypes = {"微信", "支付宝", "餐卡"};
        map.put("payTypes", payTypes);
        List<String> canteenIds = new ArrayList<>();
        try {
            String dataSocre=  DataSourceHandle.getDataSourceType();
            if ("".equals(canteenId)||canteenId == null) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                for (String canId : user.getCanteenIds().split(",")) {
                    LsResponse ls=  canteenService.getCanteenByCanteenId(canId);
                    if(ls.getData()!=null){
                      canteenIds.add(canId);
                    }
                }
            } else {
                canteenIds.add(canteenId);
            }
            Date startTime;
            Date endTime;
            if(startDate!=null&&!"".equals(startDate))
            {
                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
                ParsePosition pos = new ParsePosition(0);
                startTime = time.parse(startDate, pos);
                ParsePosition endPos = new ParsePosition(0);
                endTime = time.parse(endDate,endPos);
            }else{
                startTime=Dates.addMonths(Dates.now(),-1);
                endTime=Dates.endOfDay(Dates.now());
            }
            if(canteenIds.size()>0){
                DataSourceHandle.setDataSourceType(dataSocre);
                List<Map<String, Object>> moneys = statisticsService.payTypeMoney(canteenIds, startTime, endTime);
                map.put("moneys", moneys);
            }else{
                map.put("moneys",new ArrayList<>());
            }
            lsResponse.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse payTypeOrder(String canteenId, User user,String startDate,String endDate) {
        LsResponse lsResponse = new LsResponse();
        Map<String, Object> map = new HashedMap();
        map.put("name","订单数量分布图");
        String[] payTypes = {"微信", "支付宝", "餐卡"};
        map.put("payTypes", payTypes);
        try {
            String dataSocre=  DataSourceHandle.getDataSourceType();
            List<String> canteenIds=new ArrayList<>();
            if ("".equals(canteenId)||canteenId==null) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                for (String canId :user.getCanteenIds().split(",")) {
                    lsResponse=canteenService.getCanteenByCanteenId(canId);
                    if(lsResponse.getData()!=null){
                        canteenIds.add(canId);
                    }
                }
            } else {
                canteenIds.add(canteenId);
            }
            Date startTime;
            Date endTime;
            if(startDate!=null&&!"".equals(startDate))
            {
                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
                ParsePosition pos = new ParsePosition(0);
                ParsePosition endPos = new ParsePosition(0);
                endTime =time.parse(endDate,endPos);
                startTime = time.parse(startDate, pos);
            }else{
                endTime=Dates.endOfDay(Dates.now());
                startTime=Dates.addMonths(Dates.now(),-1);
            }
            if (canteenIds.size() > 0) {
                DataSourceHandle.setDataSourceType(dataSocre);
                List<Integer> counts = statisticsService.payTypeOrder(canteenIds, startTime, endTime);
                map.put("payData", counts);
            } else {
                map.put("payData", new ArrayList<>());
            }
            lsResponse.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse goodSaleCount(User user, String canteenId, String date) {
        LsResponse lsResponse=new LsResponse();
        try {
            lsResponse=statisticsService.goodSaleCount(user,canteenId,date);
            List<GoodStatisticsOrder>goodStatisticsOrders=new ArrayList<>();
            List<Canteen>canteens=new ArrayList<>();
            if(lsResponse.getData()!=null)
            {
                goodStatisticsOrders=(List<GoodStatisticsOrder>)lsResponse.getData();
            }
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            LsResponse ls=canteenService.getUserCanteenList(user.getCanteenIds());
            if(ls.getData()!=null)
            {
                canteens=(List<Canteen>)ls.getData();
            }
            List<GoodStatisticsOrder>goodStatisticsOrderList=new ArrayList<>();
            for(int i=0;i<goodStatisticsOrders.size();i++)
            {
                GoodStatisticsOrder goodStatisticsOrder=goodStatisticsOrders.get(i);
                for(Canteen canteen:canteens)
                {
                    if(canteen.getCanteenId().equals(goodStatisticsOrder.getCanteenId()))
                    {
                        goodStatisticsOrder.setCanteenName(canteen.getCanteenName());
                        break;
                    }
                }
                goodStatisticsOrderList.add(goodStatisticsOrder);
            }
            lsResponse.setData(goodStatisticsOrderList);
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }


}
