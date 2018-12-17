package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.StatisticsService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuhao on 2017/7/4.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final Logger log = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    @Autowired
    private GoodOrderMapper goodOrderMapper;
    @Autowired
    private RecipeOrderMapper recipeOrderMapper;
    @Autowired
    private GoodOrderItemsMapper goodOrderItemsMapper;
    @Autowired
    private GoodPlanMapper goodPlanMapper;
    @Autowired
    private GoodPlanItemsMapper goodPlanItemsMapper;
    @Autowired
    private GoodMapper goodMapper;

    @Override
    public LsResponse getSalesCount(String startDate, String endDate, List<String> canteenIds) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (startDate == null || "".equals(startDate)) {
                startDate = Dates.format(Dates.startOfDay(Dates.now()));
                endDate = Dates.format(Dates.endDateOfWeek(Dates.now()));
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startTime = simpleDateFormat.parse(startDate);
            Date endTime = simpleDateFormat.parse(endDate);
            List<GoodOrder> goodOrdersOver = goodOrderMapper.getSalesList(startTime, endTime, canteenIds);
            List<RecipeOrder> recipeOrdersOver = recipeOrderMapper.getSalesList(startTime, endTime, canteenIds);
            List<GoodOrder> goodOrdersRefund = goodOrderMapper.getSalesRefundList(startTime, endTime, canteenIds);
            List<RecipeOrder> recipeOrdersRefund = recipeOrderMapper.getSalesRefundList(startTime, endTime, canteenIds);
            Float goodPaidMoney=goodOrderMapper.getMoneyPaidAll(startDate,endDate,canteenIds);
            Float recipePaidMoney=recipeOrderMapper.getMoneyPaidAll(startDate,endDate,canteenIds);
            Statistics statistics = new Statistics();
            double goodPrice = 0;
            double recipePrice = 0;
            double refundPrice = 0;
            int goodCount = 0;
            int recipeCount = 0;
            //商品订单数和销量
            if (goodOrdersOver != null && goodOrdersOver.size() > 0) {
                goodCount = goodOrdersOver.size();
                for (int i = 0; i < goodOrdersOver.size(); i++) {
                    GoodOrder goodOrder = goodOrdersOver.get(i);
                    goodPrice += goodOrder.getOrderAmount();
                }
            }
            statistics.setGoodOrderCount(goodCount);
            BigDecimal goodPriceDouble = new BigDecimal(goodPrice);
            goodPrice = goodPriceDouble.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            statistics.setGoodPrice(goodPrice);
            //菜品订单数和销量
            if (recipeOrdersOver != null && recipeOrdersOver.size() > 0) {
                recipeCount = recipeOrdersOver.size();
                for (int i = 0; i < recipeOrdersOver.size(); i++) {
                    recipePrice += recipeOrdersOver.get(i).getOrderAmount();
                }
            }
            statistics.setRecipeOrderCount(recipeCount);
            BigDecimal recipePriceDouble = new BigDecimal(recipePrice);
            recipePrice = recipePriceDouble.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println(recipePrice);
            statistics.setRecipePrice(recipePrice);
            //商品退款
            if (goodOrdersRefund != null && goodOrdersRefund.size() > 0) {
                for (int i = 0; i < goodOrdersRefund.size(); i++) {
                    refundPrice += goodOrdersRefund.get(i).getOrderAmount();
                }
            }
            //菜品退款
            if (recipeOrdersRefund != null && recipeOrdersRefund.size() > 0) {
                for (int i = 0; i < recipeOrdersRefund.size(); i++) {
                    refundPrice += recipeOrdersRefund.get(i).getOrderAmount();
                }
            }
            //总退款数
            BigDecimal refundPriceDouble = new BigDecimal(refundPrice);
            refundPrice = refundPriceDouble.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            statistics.setRefundAll(refundPrice);
            //总销售额
            statistics.setPriceAll(goodPrice + recipePrice);
            if (goodPaidMoney != null) {
                statistics.setGoodPaidPrice(goodPaidMoney);
            } else {
                statistics.setGoodPaidPrice(0f);
            }
            if(recipePaidMoney!=null)
            {
                statistics.setRecipePaidPrice(recipePaidMoney);
            }else{
                statistics.setRecipePaidPrice(0f);
            }
            lsResponse.setData(statistics);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse goodSalesCount(String date, Integer pageSize, Integer pageNum, List<String> canteenIds) throws ParseException {
        LsResponse lsResponse = new LsResponse();
        if (date == null || "".equals(date)) {
            date = Dates.format(Dates.now());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = Dates.startOfDay(simpleDateFormat.parse(date));
        startTime = Dates.startOfDay(startTime);
        Date endTime = Dates.endOfDay(startTime);
        int offSet = 0;
        if (pageSize != null && pageNum != null) {
            offSet = (pageNum - 1) * pageSize;
        }
        List<GoodStatisticsOrder> goodStatisticsOrders = goodOrderMapper.getGoodPaidList(startTime, endTime, canteenIds, offSet, pageSize);
        int total = goodOrderMapper.getGoodPaidCount(startTime, endTime, canteenIds);
        if (goodStatisticsOrders!= null && goodStatisticsOrders.size()> 0) {
            lsResponse.setTotalCount(total);
            lsResponse.setData(goodStatisticsOrders);
        } else {
            lsResponse.checkSuccess(false, CodeMessage.STATISTICS_NO_ERR.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse recipeSalesCount(String date, Integer pageSize, Integer pageNum, List<String> canteenIds) throws ParseException {
        LsResponse lsResponse = new LsResponse();
        if (date == null || "".equals(date)) {
            date = Dates.format(Dates.now());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = simpleDateFormat.parse(date);
        Date endTime = Dates.addDays(startTime, 1);
        int offSet = 0;
        if (pageSize != null && pageNum != null) {
            offSet = (pageNum - 1) * pageSize;
        }
        int total = recipeOrderMapper.selectSalesCount(startTime, endTime, canteenIds);
        List<RecipeStatisricsOrder> recipeStatisricsOrders = recipeOrderMapper.getSalesRecipeList(startTime, endTime, offSet, pageSize, canteenIds);
        if (recipeStatisricsOrders != null && recipeStatisricsOrders.size() > 0) {
            lsResponse.setTotalCount(total);
            lsResponse.setData(recipeStatisricsOrders);
        } else {
            lsResponse.checkSuccess(false, CodeMessage.STATISTICS_NO_ERR.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getDanPin(User user) {
        LsResponse lsResponse = new LsResponse();
        String[] canteenIds = user.getCanteenIds().split(",");
        Map<String, Object> map = new HashedMap();
        map.put("shopName", "食材销量");
        map.put("dishName", "菜品销量");
        String[] str = {"第一名", "第二名", "第三名", "第四名", "第五名"};
        map.put("xaxisData", str);
        try {
            List<GoodOrder> goodOrders= goodOrderMapper.getDanPin(Dates.addMonths(Dates.now(), -1), Dates.now(), canteenIds);
            if(goodOrders!=null&&goodOrders.size()>0)
            {
                List<GoodOrderItems>goodOrderItemList=new ArrayList<>();
                for(GoodOrder goodOrder:goodOrders)
                {
                    List<GoodOrderItems> goodOrderItemses=goodOrderItemsMapper.getGoodOrderItemsList(goodOrder.getOrderId());
                    if(goodOrderItemses!=null&&goodOrderItemses.size()>0)
                    {
                        for(GoodOrderItems goodOrderItems:goodOrderItemses)
                        {
                            goodOrderItemList.add(goodOrderItems);
                        }
                    }
                }
                List<Statistics>goodStatisticses=getGoods(goodOrderItemList);
                if (goodStatisticses != null && goodStatisticses.size() > 0) {
                    Collections.sort(goodStatisticses,(o1, o2) ->(o2.getValue()-o1.getValue()) );

                }
                map.put("shopData",goodStatisticses.subList(0,goodStatisticses.size()<5?goodStatisticses.size():5));
            }
            lsResponse.setData(map);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    private List<Statistics> getGoods(List<GoodOrderItems> goodOrderItemList) {
        List<Statistics>goodStatisticses=new ArrayList<>();
        Set<String>goodIds=new HashSet<>();
        for(GoodOrderItems goodOrderItems:goodOrderItemList)
        {
            Good good=goodOrderItems.getGood();
            if(good!=null)
            {
                goodIds.add(good.getGoodId());
            }
        }
        for(String goodId:goodIds)
        {
            Statistics statistics=new Statistics();
            Integer count=0;
            String name="";
            for(GoodOrderItems goodOrderItems:goodOrderItemList)
            {
                Good good=goodOrderItems.getGood();
                if(goodId.equals(good.getGoodId()))
                {
                     count+=goodOrderItems.getQuantity();
                     name =good.getGoodName();
                }
            }
            statistics.setName(name);
            statistics.setValue(count);
            goodStatisticses.add(statistics);
        }
        return  goodStatisticses;
    }

    @Override
    public LsResponse getCateenOrders(String canteenId) {
        LsResponse lsResponse = new LsResponse();
        try {
            int value = goodOrderMapper.getSalesOrderCount(Dates.addDays(Dates.now(), -15), Dates.addDays(Dates.now(), 15), canteenId);
            value += recipeOrderMapper.getSalesOrderCount(Dates.addDays(Dates.now(), -15), Dates.addDays(Dates.now(), 15), canteenId);
            lsResponse.setData(value);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }
    @Override
    public List<Map<String, Object>> payTypeMoney(List<String> canteenIds, Date startDate, Date endDate) {
        List<Map<String, Object>> list = new ArrayList<>();
        double zhiFuBao = 0;
        double weiXin = 0;
        double canKa = 0;
        List<GoodOrder> goodOrders = goodOrderMapper.payOrder(canteenIds, startDate, endDate);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.payOrder(canteenIds, startDate, endDate);
        if (goodOrders != null && goodOrders.size() > 0) {
            for (GoodOrder goodOrder : goodOrders) {
                if (goodOrder.getPayType() == 1) {
                    weiXin += goodOrder.getOrderAmount();
                } else if (goodOrder.getPayType() == 2) {
                    zhiFuBao += goodOrder.getOrderAmount();
                } else if (goodOrder.getPayType() == 3) {
                    canKa += goodOrder.getOrderAmount();
                }
            }
        }
        if (recipeOrders != null && recipeOrders.size() > 0) {
            for (RecipeOrder recipeOrder : recipeOrders) {
                if (recipeOrder.getPayType() == 1) {
                    weiXin = weiXin + recipeOrder.getOrderAmount();
                } else if (recipeOrder.getPayType() == 2) {
                    zhiFuBao = zhiFuBao + recipeOrder.getOrderAmount();
                } else if (recipeOrder.getPayType() == 3) {
                    canKa = canKa + recipeOrder.getOrderAmount();
                }
            }
        }
        BigDecimal weiDouble = new BigDecimal(weiXin);
        weiXin = weiDouble.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal zhiDouble = new BigDecimal(zhiFuBao);
        zhiFuBao = zhiDouble.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal canDouble = new BigDecimal(canKa);
        canKa = canDouble.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Map<String, Object> weiMap = new HashedMap();
        weiMap.put("name", "微信");
        weiMap.put("value", weiXin);
        Map<String, Object> zhiMap = new HashedMap();
        zhiMap.put("name", "支付宝");
        zhiMap.put("value", zhiFuBao);
        Map<String, Object> canMap = new HashedMap();
        canMap.put("name", "餐卡");
        canMap.put("value", canKa);
        list.add(weiMap);
        list.add(zhiMap);
        list.add(canMap);
        return list;
    }

    @Override
    public List<Integer> payTypeOrder(List<String> canteenIds, Date startDate, Date endDate) {
        int zhiCount = 0;
        int weiCount = 0;
        int canCount = 0;
        List<GoodOrder> goodOrders = goodOrderMapper.payOrder(canteenIds, startDate, endDate);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.payOrder(canteenIds, startDate, endDate);
        if (goodOrders != null && goodOrders.size() > 0) {
            for (GoodOrder goodOrder : goodOrders) {
                if (goodOrder.getPayType() == 1) {
                    weiCount++;
                } else if (goodOrder.getPayType() == 2) {
                    zhiCount++;
                } else if (goodOrder.getPayType() == 3) {
                    canCount++;
                }
            }
        }
        if (recipeOrders != null && recipeOrders.size() > 0) {
            for (RecipeOrder recipeOrder : recipeOrders) {
                if (recipeOrder.getPayType() == 1) {
                    weiCount = weiCount + 1;
                } else if (recipeOrder.getPayType() == 2) {
                    zhiCount = zhiCount + 1;
                } else if (recipeOrder.getPayType() == 3) {
                    canCount = canCount + 1;
                }
            }
        }
        List<Integer> list = new ArrayList<>();
        list.add(weiCount);
        list.add(zhiCount);
        list.add(canCount);
        return list;
    }

    @Override
    public LsResponse timeGoodOrder(User user) {
        LsResponse lsResponse = new LsResponse();
        Map<String, Object> map = new HashedMap();
        List<String> xaxisData = new ArrayList<>();
        List<Integer> shopData = new ArrayList<>();
        map.put("shopName", "食材销量");
        Date date = Dates.addDays(Dates.now(), -14);
        String[] canteenIds = user.getCanteenIds().split(",");
        try {
            for (int i = 0; i < 15; i++) {
                String toDate = Dates.getNextDayFormat(i, date);
                Integer goodValue = goodOrderMapper.getTimeOrder(toDate + "%", canteenIds);
                if (goodValue != null && goodValue > 0) {
                    xaxisData.add(toDate);
                    shopData.add(goodValue);
                }
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            e.printStackTrace();
        }
        map.put("xaxisData", xaxisData);
        map.put("shopData", shopData);
        lsResponse.setData(map);
        return lsResponse;
    }

    @Override
    public LsResponse timeRecipeOrder(User user) {
        LsResponse lsResponse = new LsResponse();
        Map<String, Object> map = new HashedMap();
        List<String> xaxisData = new ArrayList<>();
        List<Integer> dishData = new ArrayList<>();
        map.put("dishName", "菜品销量");
        Date date = Dates.addDays(Dates.now(), -14);
        String[] canteenIds = user.getCanteenIds().split(",");
        try {
            for (int i = 0; i < 15; i++) {
                String toDate = Dates.getNextDayFormat(i, date);
                Integer recipeValue = recipeOrderMapper.getTimeOrder(toDate + "%", canteenIds);
                if (recipeValue != null && recipeValue > 0) {
                    xaxisData.add(toDate);
                    dishData.add(recipeValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        map.put("xaxisData", xaxisData);
        map.put("dishData", dishData);
        lsResponse.setData(map);
        return lsResponse;
    }

    @Override
    public LsResponse goodSaleCount(User user, String canteenId, String date) {
        LsResponse lsResponse = new LsResponse();
        List<String> canteenIds = new ArrayList<>();
        try {
            //处理餐厅，当餐厅为空时按照管理员绑定的查找，否则按照传入的餐厅查找
            if (canteenId != null && !"".equals(canteenId)) {
                canteenIds.add(canteenId);
            } else {
                for (String can : user.getCanteenIds().split(",")) {
                    canteenIds.add(can);
                }
            }
            //处理日期，当日期为空时，按照当天查找，否则按照传入的日期查找
            Date startDate = Dates.startOfDay(Dates.now());
            Date endDate=Dates.endOfDay(Dates.now());
            if (date != null && !"".equals(date)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                startDate = simpleDateFormat.parse(date.split("-")[0]);
                endDate=simpleDateFormat.parse(date.split("-")[1]);
            }
            //查找餐厅在startDate时的售卖计划
            List<GoodPlan> goodPlanList = goodPlanMapper.getGoodPlanContentByTime(canteenIds, startDate,endDate);
            if (goodPlanList != null && goodPlanList.size() > 0) {
                //获取售卖计划编号
                List<String> goodPlanIds = new ArrayList<>();
                for (int i = 0; i < goodPlanList.size(); i++) {
                    GoodPlan goodPlan = goodPlanList.get(i);
                    goodPlanIds.add(goodPlan.getGoodPlanId());
                }
                //根据计划编号查找计划详情
                List<GoodPlanItems> goodPlanItemsList = goodPlanItemsMapper.getGoodPlanByPlanIds(goodPlanIds);
                    if (goodPlanItemsList != null && goodPlanItemsList.size() > 0) {
                    //对商品销售统计数据做初始化工作
                    List<GoodStatisticsOrder> goodStatisticsOrders = getStatisticsOrders(goodPlanItemsList);
                    if (goodStatisticsOrders != null && goodStatisticsOrders.size() > 0) {
                        //查找今天商品售卖订单
                        List<GoodOrder> goodOrders = goodOrderMapper.getSalesList(startDate, endDate, canteenIds);
                        if (goodOrders != null && goodOrders.size() > 0) {
                            List<GoodStatisticsOrder> goodStatisticsOrderList = new ArrayList<>();
                            List<String> orderIds = new ArrayList<>();
                            for (GoodOrder goodOrder : goodOrders) {
                                orderIds.add(goodOrder.getOrderId());
                            }
                            //根据订单查找商品售卖详情
                            List<GoodOrderItems> goodOrderItemses = goodOrderItemsMapper.getGoodItemss(orderIds);
                            //统计商品的售卖数量
                            for (int i = 0; i < goodStatisticsOrders.size(); i++) {
                                GoodStatisticsOrder statisticsOrder = goodStatisticsOrders.get(i);
                                Integer quantity=0;
                                Float price=0f;
                                for (GoodOrderItems goodOrderItems : goodOrderItemses) {
                                    if (statisticsOrder.getGoodId().equals(goodOrderItems.getGoodId())) {
                                        quantity+=goodOrderItems.getQuantity();
                                        price+=goodOrderItems.getOrderAmount()*goodOrderItems.getQuantity();
                                    }
                                }
                                statisticsOrder.setQuantity(quantity);
                                statisticsOrder.setPriceAll(price);
                                goodStatisticsOrderList.add(statisticsOrder);
                            }
                            goodStatisticsOrders=goodStatisticsOrderList;
                        }
                        lsResponse.setData(goodStatisticsOrders);
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.GOODPLAN_GETGOODPLAN_NO_ERR.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.GOODPLAN_GETGOODPLAN_NO_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.GOODPLAN_GETGOODPLAN_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error("统计当天商品售卖情况{}" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    private List<GoodStatisticsOrder> getStatisticsOrders(List<GoodPlanItems> goodPlanItemsList) throws Exception{
        List<GoodStatisticsOrder> goodStatisticsOrders = new ArrayList<>();
        for (int i = 0; i < goodPlanItemsList.size(); i++) {
            GoodStatisticsOrder goodStatisticsOrder = new GoodStatisticsOrder();
            GoodPlanItems goodPlanItems = goodPlanItemsList.get(i);
            Good good = goodMapper.getGoodByGoodId(goodPlanItems.getGoodId());
            goodPlanItems.setGood(good);
            goodStatisticsOrder.setDescription(good.getDescription());
            goodStatisticsOrder.setGoodId(good.getGoodId());
            goodStatisticsOrder.setImageurl(good.getImageurl());
            goodStatisticsOrder.setName(good.getGoodName());
            goodStatisticsOrder.setPrice(goodPlanItems.getPriceDay());
            goodStatisticsOrder.setPriceAll(0f);
            goodStatisticsOrder.setQuantity(0);
            goodStatisticsOrder.setCanteenId(good.getCanteenId());
            goodStatisticsOrders.add(goodStatisticsOrder);
        }
        return goodStatisticsOrders;
    }

}
