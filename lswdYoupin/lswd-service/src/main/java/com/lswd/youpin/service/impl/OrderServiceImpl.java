package com.lswd.youpin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.commons.OrderType;
import com.lswd.youpin.commons.RecipeType;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CarService;
import com.lswd.youpin.service.OrderService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/6/26.
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private GoodOrderItemsMapper goodOrderItemsMapper;
    @Autowired
    private RecipeOrderItemsMapper recipeOrderItemsMapper;
    @Autowired
    private RecipeOrderMapper recipeOrderMapper;
    @Autowired
    private GoodOrderMapper goodOrderMapper;
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private CarService carService;
    @Autowired
    private GoodPlanMapper goodPlanMapper;
    @Autowired
    private RecipePlanMapper recipePlanMapper;

    //添加订单号主方法
    @Override
//    public LsResponse addGenerateOrder(List<Map<String, Object>> carOrders, Associator associator,Integer time) {
//        LsResponse lsResponse = new LsResponse();
//        Map<String, Object> orderMap = new HashedMap();
//        String address = "";
//        try {
//            if (carOrders != null && carOrders.size() > 0) {
//                List<Car> cars = new ArrayList<>();
//                List<GoodOrder> goodOrders = new ArrayList<>();//商品订单
//                List<RecipeOrder> recipeOrders = new ArrayList<>();//菜谱订单
//                for (int i = 0; i < carOrders.size(); i++) { //对数据进行解析处理
//                    Map<String, Object> map = carOrders.get(i);
//                    address = (String) map.get("address");//获取取货地址
//                    String pickTimeString = (String) map.get("pickTime");//获取取货时间
//                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                    ParsePosition pos = new ParsePosition(0);
//                    Date pickTime = formatter.parse(pickTimeString, pos);
//                    Float goodOrderPrice = 0f;//商品订单总价
//                    Double recipeOrderPrice = 0d;//菜品订单总价
//                    String carListString = JSONArray.toJSONString(map.get("commodity"));//获取商品下单信息
//                    List<Car> carList = JSON.parseArray(carListString, Car.class);//将json数据解析为购物车对象
//                    List<Car> goodCars = new ArrayList<>();//商品购物车提交信息
//                    List<Car> recipeCars = new ArrayList<>();//菜品购物车提交信息
//                    //对购物车进行商品或者菜品分类处理
//                    for (Car car : carList) {
//                        if (!Dates.isAfter(Dates.now(), Dates.addMinutes(Dates.startOfDay(pickTime), time))) {  //判断下单是否超时
//                            if (car.getGoodId().startsWith("g")) {
//                                goodCars.add(car);
//                            } else {
//                                recipeCars.add(car);
//                            }
//                        } else {
//                            cars.add(car);
//                        }
//                    }
//                    //对商品订单进行整理
//                    if (goodCars.size() > 0) {
//                        Map<String, List<GoodOrderItems>> mapGoodOrders = getGoodOrderItems(goodCars);
//                        List<GoodOrderItems> goodOrderItemsOutList = mapGoodOrders.get("no");
//                        List<GoodOrderItems> goodOrderItemses = mapGoodOrders.get("yes");
//                        if (goodOrderItemses.size() > 0) {
//                            //商品订单应付款数
//                            for (GoodOrderItems goodOrderItems : goodOrderItemses) {
////                                这里有个潜在的风险，浮点数的运算，在不同计算机里可能出现不同情况，解决方案是，应用BigDecimal类进行精细运算
////                                如下所示,但建议对BigDecimal进行封装，写成一个工具类，便于运算使用
//                                BigDecimal bg1=new BigDecimal(goodOrderItems.getOrderAmount()).multiply(new BigDecimal(goodOrderItems.getQuantity()));
//                                goodOrderPrice=new BigDecimal(goodOrderPrice).add(bg1).floatValue();
////                                goodOrderPrice = goodOrderPrice + goodOrderItems.getOrderAmount() * goodOrderItems.getQuantity();
//                            }
//                              GoodOrder goodOrder = new GoodOrder();
//                              goodOrder.setStatus((short) 0);
//                              goodOrder.setGoodOrderItems(goodOrderItemses);
//                              goodOrder.setCreateTime(Dates.now());
//                              goodOrder.setUpdateTime(Dates.now());
//                              goodOrder.setPayType((short) 0);
//                              goodOrder.setOrderAmount(goodOrderPrice);
//                              goodOrder.setPickingTime(pickTime);
//                              goodOrder.setCanteenId(goodCars.get(0).getCanteenId());
//                              goodOrder.setAssociatorId(associator.getAssociatorId());
//                              goodOrders.add(goodOrder);
//
//                          }
//                        if (goodOrderItemsOutList.size() > 0) {
//                            for (GoodOrderItems goodOrderItems : goodOrderItemsOutList) {
//                                Car car = new Car();
//                                car.setMsg(goodOrderItems.getGood().getGoodName());
//                                car.setPickTime(pickTime);
//                                cars.add(car);
//                            }
//                        }
//                    }
//                    //对菜品订单进行整理
//                    if (recipeCars.size() > 0) {
//                        Map<String, List<RecipeOrderItems>> mapRecipeOrderItems = getRecipeOrderItems(recipeCars);
//                        List<RecipeOrderItems> recipeOrderItemsList = mapRecipeOrderItems.get("yes");
//                        if (recipeOrderItemsList.size() > 0) {
//                            //菜品订单应付款数
//                            for (RecipeOrderItems recipeOrderItems : recipeOrderItemsList) {
//                                //潜在风险，解决方案同上
//                                BigDecimal bg1=new BigDecimal(recipeOrderItems.getOrderAmount()).multiply(new BigDecimal(recipeOrderItems.getQuantity()));
//                                recipeOrderPrice=new BigDecimal(recipeOrderPrice).add(bg1).doubleValue();
//                               // recipeOrderPrice = recipeOrderPrice + recipeOrderItems.getQuantity() * recipeOrderItems.getOrderAmount();
//                            }
//                            RecipeOrder recipeOrder = new RecipeOrder();
//                            recipeOrder.setEatTime(pickTime);
//                            recipeOrder.setStatus((short) 0);
//                            recipeOrder.setRecipeOrderItemsList(recipeOrderItemsList);
//                            recipeOrder.setCreateTime(Dates.now());
//                            recipeOrder.setUpdateTime(Dates.now());
//                            recipeOrder.setPayType((short) 0);
//                            recipeOrder.setOrderAmount(recipeOrderPrice);
//                            recipeOrder.setCanteenId(recipeCars.get(0).getCanteenId());
//                            recipeOrder.setAssociatorId(associator.getAssociatorId());
//                            recipeOrder.setPickType(recipeCars.get(0).getRecipeType());
//                            recipeOrders.add(recipeOrder);
//                        }
//                        List<RecipeOrderItems> recipeOrderItemsOutList = mapRecipeOrderItems.get("no");
//
//                        if (recipeOrderItemsOutList.size() > 0) {
//                            for (RecipeOrderItems recipeOrderItems : recipeOrderItemsOutList) {
//                                Car car = new Car();
//                                car.setMsg(recipeOrderItems.getRecipe().getRecipeName());
//                                car.setPickTime(pickTime);
//                                cars.add(car);
//                            }
//                        }
//                    }
//                    carService.deleteCar(carList, associator,time);//删除购物车中的商品
//                }
//                List<Map<String, Object>> orderList = new ArrayList<>();
//                //对商品进行下单处理
//                if (goodOrders.size() > 0) {
//                    List<GoodOrder> goodOrderList = addGoodOrder(goodOrders);
//                    for (int i = 0; i < goodOrderList.size(); i++) {
//                        Map<String, Object> goodMap = new HashedMap();
//                        GoodOrder goodOrder = goodOrderList.get(i);
//                        goodMap.put("orderId", goodOrder.getOrderId());
//                        goodMap.put("priceAll", goodOrder.getOrderAmount());
//                        goodMap.put("pickTime", goodOrder.getPickingTime());
//                        List<Car> myCars = new ArrayList<>();
//                        for (int j = 0; j < goodOrder.getGoodOrderItems().size(); j++) {
//                            GoodOrderItems goodOrderItems = goodOrder.getGoodOrderItems().get(j);
//                            Good good = goodMapper.getGoodByGoodId(goodOrderItems.getGoodId());
//                            Car car = new Car();
//                            car.setNumber(goodOrder.getGoodOrderItems().get(j).getQuantity());
//                            car.setImg(good.getImageurl());
//                            car.setMsg(good.getGoodName() + " 【 " + good.getStandard() + " 】");
//                            car.setPrice(String.valueOf(good.getPrice()));
//                            myCars.add(car);
//                        }
//                        goodMap.put("good", myCars);
//                        orderList.add(goodMap);
//                    }
//                }
//                //对菜谱进行下单处理
//                if (recipeOrders.size() > 0) {
//                    List<RecipeOrder> recipeOrderList = addRecipeOrder(recipeOrders);
//                    if (recipeOrderList != null && recipeOrderList.size() > 0) {
//                        for (int i = 0; i < recipeOrderList.size(); i++) {
//                            Map<String, Object> repiceMap = new HashedMap();
//                            RecipeOrder recipeOrder = recipeOrderList.get(i);
//                            repiceMap.put("orderId", recipeOrder.getOrderId());
//                            repiceMap.put("priceAll", recipeOrder.getOrderAmount());
//                            //对就餐时间进行分类
//                            String recipeType;
//                            if (recipeOrder.getPickType() !=null) {
//                                recipeType = RecipeType.getRecipeType(recipeOrder.getPickType()).getName();
//                            }else{
//                                recipeType=RecipeType.other.getName();
//                            }
//                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                            String dateString = formatter.format(recipeOrder.getEatTime());
//                            repiceMap.put("pickTime", dateString + "【 " + recipeType + " 】");
//                            List<Car> myCars = new ArrayList<>();
//                            for (int j = 0; j < recipeOrder.getRecipeOrderItemsList().size(); j++) {
//                                RecipeOrderItems RecipeOrderItems = recipeOrder.getRecipeOrderItemsList().get(j);
//                                Recipe recipe = recipeMapper.getRecipeByRecipeId(RecipeOrderItems.getRecipeId());
//                                recipe.setNum(recipeOrder.getRecipeOrderItemsList().get(j).getQuantity());
//                                Car car = new Car();
//                                car.setNumber(recipeOrder.getRecipeOrderItemsList().get(j).getQuantity());
//                                car.setImg(recipe.getImageurl());
//                                car.setMsg(recipe.getRecipeName());
//                                car.setPrice(String.valueOf(recipe.getGuidePrice()));
//                                myCars.add(car);
//                            }
//                            repiceMap.put("good", myCars);
//                            orderList.add(repiceMap);
//                        }
//                    }
//                }
//                //返回前台数据
//                Associator a = new Associator();
//                a.setAccount(associator.getAccount());
//                a.setTelephone(associator.getTelephone());
//                orderMap.put("address",address);
//                orderMap.put("associator", a);
//                orderMap.put("orders", orderList);
//                //没有库存的商品信息
//                if (cars.size() > 0) {
//                    orderMap.put("noOrders", cars);
//                } else {
//                    orderMap.put("noOrders", 0);
//                }
//                lsResponse.setData(orderMap);
//            } else {
//               lsResponse.checkSuccess(false,CodeMessage.PARAMS_ERR.name());
//            }
//        } catch (Exception e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
//            log.error(e.toString());
//        }
//        return lsResponse;
//    }


    public LsResponse addGenerateOrder(Orders orders, Associator associator,Integer time,Double outFee) {
        LsResponse lsResponse = new LsResponse();
        Map<String, Object> orderMap = new HashedMap();
        String address;
        try {
             List<Map<String,Object>> carOrders=orders.getOrders();
            if(orders.getNeedOut()){ //获取取货地址
                address=orders.getNewAddress();
            }else{
                address = (String) carOrders.get(0).get("address");
            }
            System.out.println(orders+"-------------------");
            if (carOrders!= null && carOrders.size() > 0) {
                List<Car> cars = new ArrayList<>();
                List<GoodOrder> goodOrders = new ArrayList<>();//商品订单
                List<RecipeOrder> recipeOrders = new ArrayList<>();//菜谱订单
                for (int i = 0; i < carOrders.size(); i++) {  //对数据进行解析处理
                    Map<String, Object> map = carOrders.get(i);

                    String pickTimeString = (String) map.get("pickTime");//获取取货时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    ParsePosition pos = new ParsePosition(0);
                    Date pickTime = formatter.parse(pickTimeString, pos);
                    Float goodOrderPrice = 0f;//商品订单总价
                    Double recipeOrderPrice = 0d;//菜品订单总价
                    String carListString = JSONArray.toJSONString(map.get("commodity"));//获取商品下单信息
                    List<Car> carList = JSON.parseArray(carListString, Car.class);//将json数据解析为购物车对象
                    List<Car> goodCars = new ArrayList<>();//商品购物车提交信息
                    List<Car> recipeCars = new ArrayList<>();//菜品购物车提交信息
                    //对购物车进行商品或者菜品分类处理
                    for (Car car : carList) {
                        if (!Dates.isAfter(Dates.now(), Dates.addMinutes(Dates.startOfDay(pickTime), time))) {  //判断下单是否超时
                            if (car.getGoodId().startsWith("g")) {
                                goodCars.add(car);
                            } else {
                                recipeCars.add(car);
                            }
                        } else {
                            cars.add(car);
                        }
                    }
                    //对商品订单进行整理
                    if (goodCars.size() > 0) {
                        Map<String, List<GoodOrderItems>> mapGoodOrders = getGoodOrderItems(goodCars);
                        List<GoodOrderItems> goodOrderItemsOutList = mapGoodOrders.get("no");
                        List<GoodOrderItems> goodOrderItemses = mapGoodOrders.get("yes");
                        if (goodOrderItemses.size() > 0) {
                            //商品订单应付款数
                            for (GoodOrderItems goodOrderItems : goodOrderItemses) {
//                                这里有个潜在的风险，浮点数的运算，在不同计算机里可能出现不同情况，解决方案是，应用BigDecimal类进行精细运算
//                                如下所示,但建议对BigDecimal进行封装，写成一个工具类，便于运算使用
                                BigDecimal bg1=new BigDecimal(goodOrderItems.getOrderAmount()).multiply(new BigDecimal(goodOrderItems.getQuantity()));
                                goodOrderPrice=new BigDecimal(goodOrderPrice).add(bg1).floatValue();
//                                goodOrderPrice = goodOrderPrice + goodOrderItems.getOrderAmount() * goodOrderItems.getQuantity();
                            }
                            GoodOrder goodOrder = new GoodOrder();
                            goodOrder.setStatus((short) 0);
                            goodOrder.setGoodOrderItems(goodOrderItemses);
                            goodOrder.setCreateTime(Dates.now());
                            goodOrder.setUpdateTime(Dates.now());
                            goodOrder.setPayType((short) 0);
                            goodOrder.setOrderAmount(goodOrderPrice);
                            goodOrder.setPickingTime(pickTime);
                            goodOrder.setCanteenId(goodCars.get(0).getCanteenId());
                            goodOrder.setAssociatorId(associator.getAssociatorId());
                            if(orders.getNeedOut())
                            {
                                goodOrder.setOutFee(outFee);
                            }else{
                                goodOrder.setOutFee(0d);
                            }
                            goodOrders.add(goodOrder);

                        }
                        if (goodOrderItemsOutList.size() > 0) {
                            for (GoodOrderItems goodOrderItems : goodOrderItemsOutList) {
                                Car car = new Car();
                                car.setMsg(goodOrderItems.getGood().getGoodName());
                                car.setPickTime(pickTime);
                                cars.add(car);
                            }
                        }
                    }
                    //对菜品订单进行整理
                    if (recipeCars.size() > 0) {
                        Map<String, List<RecipeOrderItems>> mapRecipeOrderItems = getRecipeOrderItems(recipeCars);
                        List<RecipeOrderItems> recipeOrderItemsList = mapRecipeOrderItems.get("yes");
                        if (recipeOrderItemsList.size() > 0) {
                            //菜品订单应付款数
                            for (RecipeOrderItems recipeOrderItems : recipeOrderItemsList) {
                                //潜在风险，解决方案同上
                                BigDecimal bg1=new BigDecimal(recipeOrderItems.getOrderAmount()).multiply(new BigDecimal(recipeOrderItems.getQuantity()));
                                recipeOrderPrice=new BigDecimal(recipeOrderPrice).add(bg1).doubleValue();
                                // recipeOrderPrice = recipeOrderPrice + recipeOrderItems.getQuantity() * recipeOrderItems.getOrderAmount();
                            }
                            RecipeOrder recipeOrder = new RecipeOrder();
                            recipeOrder.setEatTime(pickTime);
                            recipeOrder.setStatus((short) 0);
                            recipeOrder.setRecipeOrderItemsList(recipeOrderItemsList);
                            recipeOrder.setCreateTime(Dates.now());
                            recipeOrder.setUpdateTime(Dates.now());
                            recipeOrder.setPayType((short) 0);
                            recipeOrder.setOrderAmount(recipeOrderPrice);
                            recipeOrder.setCanteenId(recipeCars.get(0).getCanteenId());
                            recipeOrder.setAssociatorId(associator.getAssociatorId());
                            recipeOrder.setPickType(recipeCars.get(0).getRecipeType());
                            if(orders.getNeedOut()) {recipeOrder.setOutFee(outFee);}else{recipeOrder.setOutFee(0d);}//设置外送费率
                            recipeOrders.add(recipeOrder);
                        }
                        List<RecipeOrderItems> recipeOrderItemsOutList = mapRecipeOrderItems.get("no");

                        if (recipeOrderItemsOutList.size() > 0) {
                            for (RecipeOrderItems recipeOrderItems : recipeOrderItemsOutList) {
                                Car car = new Car();
                                car.setMsg(recipeOrderItems.getRecipe().getRecipeName());
                                car.setPickTime(pickTime);
                                cars.add(car);
                            }
                        }
                    }
                    carService.deleteCar(carList, associator,time);//删除购物车中的商品
                }
                List<Map<String, Object>> orderList = new ArrayList<>();
                //对商品进行下单处理
                if (goodOrders.size() > 0) {
                    List<GoodOrder> goodOrderList = addGoodOrder(goodOrders);
                    for (int i = 0; i < goodOrderList.size(); i++) {
                        Map<String, Object> goodMap = new HashedMap();
                        GoodOrder goodOrder = goodOrderList.get(i);
                        goodMap.put("orderId", goodOrder.getOrderId());
                        goodMap.put("priceAll", goodOrder.getOrderAmount());
                        goodMap.put("pickTime", goodOrder.getPickingTime());
                        if(orders.getNeedOut()){
                            goodMap.put("outFee",outFee);
                        }else{
                            goodMap.put("outFee",0d);
                        }
                        List<Car> myCars = new ArrayList<>();
                        for (int j = 0; j < goodOrder.getGoodOrderItems().size(); j++) {
                            GoodOrderItems goodOrderItems = goodOrder.getGoodOrderItems().get(j);
                            Good good = goodMapper.getGoodByGoodId(goodOrderItems.getGoodId());
                            Car car = new Car();
                            if(orders.getNeedOut()){
                            car.setOutFee(outFee);
                            }else{
                                car.setOutFee(0d);
                            }
                            car.setNumber(goodOrder.getGoodOrderItems().get(j).getQuantity());
                            car.setImg(good.getImageurl());
                            car.setMsg(good.getGoodName() + " 【 " + good.getStandard() + " 】");
                            car.setPrice(String.valueOf(good.getPrice()));
                            myCars.add(car);
                        }
                        goodMap.put("good", myCars);
                        orderList.add(goodMap);
                    }
                }
                //对菜谱进行下单处理
                if (recipeOrders.size() > 0) {
                    List<RecipeOrder> recipeOrderList = addRecipeOrder(recipeOrders);
                    if (recipeOrderList != null && recipeOrderList.size() > 0) {
                        for (int i = 0; i < recipeOrderList.size(); i++) {
                            Map<String, Object> repiceMap = new HashedMap();
                            RecipeOrder recipeOrder = recipeOrderList.get(i);
                            repiceMap.put("orderId", recipeOrder.getOrderId());
                            repiceMap.put("priceAll", recipeOrder.getOrderAmount());
                            //对就餐时间进行分类
                            String recipeType;
                            if (recipeOrder.getPickType() !=null) {
                                recipeType = RecipeType.getRecipeType(recipeOrder.getPickType()).getName();
                            }else{
                                recipeType=RecipeType.other.getName();
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = formatter.format(recipeOrder.getEatTime());
                            repiceMap.put("pickTime", dateString + "【 " + recipeType + " 】");
                            if(orders.getNeedOut()){
                                repiceMap.put("outFee",outFee);
                            }else{
                                repiceMap.put("outFee",0d);
                            }
                            List<Car> myCars = new ArrayList<>();
                            for (int j = 0; j < recipeOrder.getRecipeOrderItemsList().size(); j++) {
                                RecipeOrderItems RecipeOrderItems = recipeOrder.getRecipeOrderItemsList().get(j);
                                Recipe recipe = recipeMapper.getRecipeByRecipeId(RecipeOrderItems.getRecipeId());
                                recipe.setNum(recipeOrder.getRecipeOrderItemsList().get(j).getQuantity());
                                Car car = new Car();
                                car.setNumber(recipeOrder.getRecipeOrderItemsList().get(j).getQuantity());
                                car.setImg(recipe.getImageurl());
                                car.setMsg(recipe.getRecipeName());
                                car.setPrice(String.valueOf(recipe.getGuidePrice()));
                                myCars.add(car);
                            }
                            repiceMap.put("good", myCars);
                            orderList.add(repiceMap);
                        }
                    }
                }
                //返回前台数据
                Associator a = new Associator();
                a.setAccount(associator.getAccount());
                a.setTelephone(associator.getTelephone());
                orderMap.put("address",address);
                orderMap.put("associator", a);
                orderMap.put("orders", orderList);
                //没有库存的商品信息
                if (cars.size() > 0) {
                    orderMap.put("noOrders", cars);
                } else {
                    orderMap.put("noOrders", 0);
                }
                lsResponse.setData(orderMap);
            } else {
                lsResponse.checkSuccess(false,CodeMessage.PARAMS_ERR.name());
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }



    //添加菜谱订单
    private List<RecipeOrder> addRecipeOrder(List<RecipeOrder> recipeOrders) {
        List<RecipeOrder> orderNumbers = new ArrayList<>();
        if (recipeOrders != null) {
            for (int i = 0; i < recipeOrders.size(); i++) {
                //生成菜品点单号
                Integer id = recipeOrderMapper.selectOrderTmpLast();
                if (id != null) {
                    id = id + 100001;
                } else {
                    id = 100001;
                }
                String rid = String.valueOf(id);
                if (rid.length() > 6) {
                    rid = rid.substring(rid.length() - 6, rid.length());
                }
                String recipeOrderId = Dates.format(Dates.now(), "yyyyMMddHHmmss") + rid + OrderType.recipeType;
                RecipeOrder recipeOrder = recipeOrders.get(i);
                recipeOrder.setOrderId(recipeOrderId);
                recipeOrder.setCreateTime(Dates.now());
                recipeOrder.setUpdateTime(Dates.now());
                recipeOrder.setStatus((short) 1);
                for (int j = 0; j < recipeOrder.getRecipeOrderItemsList().size(); j++) {
                    RecipeOrderItems recipeOrderItems = recipeOrder.getRecipeOrderItemsList().get(j);
                    recipeOrderItems.setOrderId(recipeOrderId);
                    recipeOrderItemsMapper.insertItem(recipeOrderItems);
                    Integer num= recipePlanMapper.getPlanCountRecipe(recipeOrderItems.getRecipeId(),recipeOrder.getPickType()-1,recipeOrderItems.getPlanId());
                    if(num!=null&&num>=recipeOrderItems.getQuantity())
                    {
                        num=num-recipeOrderItems.getQuantity();
                        recipePlanMapper.updateRecipePlanCount(recipeOrderItems.getRecipeId(),recipeOrder.getPickType()-1,recipeOrderItems.getPlanId(),num);
                    }
                }
                recipeOrderMapper.insertOrder(recipeOrder);
                orderNumbers.add(recipeOrder);
            }
        }
        return orderNumbers;
    }

    //添加商品订单
    private List<GoodOrder> addGoodOrder(List<GoodOrder> goodOrders) {
        List<GoodOrder> orderNumbers = new ArrayList<>();
        if (goodOrders != null) {
            for (int i = 0; i < goodOrders.size(); i++) {
                //生成商品点单号
                Integer id = goodOrderMapper.selectOrderTmpLast();
                if (id != null) {
                    id = goodOrderMapper.selectOrderTmpLast();
                    id = id + 100001;
                } else {
                    id = 10000001;
                }
                String gid = String.valueOf(id);
                if (gid.length() > 6) {
                    gid = gid.substring(gid.length() - 6, gid.length());
                }
                String goodOrderId = Dates.format(Dates.now(), "yyyyMMddHHmmss") + gid + OrderType.goodType;
                GoodOrder goodOrder = goodOrders.get(i);
                goodOrder.setOrderId(goodOrderId);
                goodOrder.setUpdateTime(Dates.now());
                goodOrder.setCreateTime(Dates.now());
                goodOrder.setStatus((short) 1);
                for (int j = 0; j < goodOrder.getGoodOrderItems().size(); j++) {
                    GoodOrderItems goodOrderItems = goodOrder.getGoodOrderItems().get(j);
                    goodOrderItems.setOrderId(goodOrderId);
                    goodOrderItemsMapper.insertSelective(goodOrderItems);
                    Integer num=goodPlanMapper.getGoodPlanCount(goodOrderItems.getPlanId(),goodOrderItems.getGoodId());
                    if(num!=null&&num>=goodOrderItems.getQuantity())
                    {
                        goodPlanMapper.updateGoodPlanCount(goodOrderItems.getPlanId(),goodOrderItems.getGoodId(),num-goodOrderItems.getQuantity());
                    }
                }
                goodOrderMapper.insertGoodOrderTmp(goodOrder, "t_good_order_tmp");
                orderNumbers.add(goodOrder);
            }
        }
        return orderNumbers;
    }

    //得到菜品订单详情信息
    private Map<String, List<RecipeOrderItems>> getRecipeOrderItems(List<Car> cars) {
        Map<String, List<RecipeOrderItems>> map = new HashedMap();
        List<RecipeOrderItems> recipeOrderItemsList = new ArrayList<>();
        List<RecipeOrderItems> recipeOrderItemsOutList = new ArrayList<>();
        for(Car car:cars)
        {
            Integer count=recipePlanMapper.getPlanCountRecipe(car.getGoodId(),car.getRecipeType()-1,car.getPlanId());
            Recipe recipe=recipeMapper.getRecipeByRecipeId(car.getGoodId());
            RecipeOrderItems recipeOrderItems=new RecipeOrderItems();
            if(count!=null&&count>=car.getNumber())
            {
                recipeOrderItems.setOrderAmount(recipe.getGuidePrice());
                recipeOrderItems.setRecipeId(car.getGoodId());
                recipeOrderItems.setQuantity(car.getNumber());
                recipeOrderItems.setPlanId(car.getPlanId());
                recipeOrderItems.setDescription(car.getText());
                recipeOrderItemsList.add(recipeOrderItems);
            }else{
                recipeOrderItems.setRecipe(recipe);
                recipeOrderItemsOutList.add(recipeOrderItems);
            }
        }
        map.put("yes", recipeOrderItemsList);
        map.put("no", recipeOrderItemsOutList);
        return map;
    }

    //得到商品订单详情信息
    private Map<String, List<GoodOrderItems>> getGoodOrderItems(List<Car> cars) {
        Map<String, List<GoodOrderItems>> map = new HashedMap();
        List<GoodOrderItems> goodOrderItemsList = new ArrayList<>();
        List<GoodOrderItems> goodOrderItemsOutList = new ArrayList<>();
        for(Car car:cars)
        {
            Integer count=goodPlanMapper.getGoodPlanCount(car.getPlanId(),car.getGoodId());
            Good good=goodMapper.getGoodByGoodId(car.getGoodId());
            GoodOrderItems goodOrderItems = new GoodOrderItems();
            if(count!=null&&count>=car.getNumber())
            {
                goodOrderItems.setGoodId(car.getGoodId());
                goodOrderItems.setPlanId(car.getPlanId());
                goodOrderItems.setOrderAmount(Float.parseFloat(car.getPrice()));
                goodOrderItems.setDescription(car.getText());
                goodOrderItems.setQuantity(car.getNumber());
                goodOrderItemsList.add(goodOrderItems);
            }else{
                goodOrderItems.setGood(good);
                goodOrderItemsOutList.add(goodOrderItems);
            }
        }
        map.put("yes", goodOrderItemsList);
        map.put("no", goodOrderItemsOutList);
        return map;
    }

    @Override
    public LsResponse timeOrder(List<String> carOrders, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (carOrders != null && carOrders.size() > 0) {
                String orderId = carOrders.get(0).toString().trim();
                char id = orderId.charAt(orderId.length() - 1);
                Date date;
                if (id == '2') {
                    RecipeOrder recipeOrder = recipeOrderMapper.selectTempOrder(orderId);
                    date = recipeOrder.getCreateTime();
                } else {
                    GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(orderId, "t_good_order_tmp");
                    date = goodOrder.getCreateTime();
                }
                Long time = Dates.timeInterval(Dates.now(), Dates.addMinutes(date, 30));
                lsResponse.setData(time);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ORDER_TIME_CAN_ERR.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }


    @Override
    public LsResponse removeOrder(List<String> carOrders) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (carOrders != null && carOrders.size() > 0) {
                for (String orderId : carOrders) {
                    char id = orderId.charAt(orderId.length() - 1);
                    if (id == '1') {
                        GoodOrder goodOrder = goodOrderMapper.getGoodOrder(orderId, "t_good_order_tmp");
                        List<GoodOrderItems> goodOrderItemses = goodOrderItemsMapper.getGoodOrderItemsList(orderId);
                        if (goodOrderItemses != null && goodOrderItemses.size() > 0) {
                            for (GoodOrderItems goodOrderItems : goodOrderItemses) {
                                String goodId = goodOrderItems.getGoodId();
                                Integer count = goodPlanMapper.getGoodPlanCount(goodOrderItems.getPlanId(), goodId);
                                if (count == null) {
                                    count = 0;
                                }
                                goodPlanMapper.updateGoodPlanCount(goodOrderItems.getPlanId(), goodId, count + goodOrderItems.getQuantity());
                            }
                            goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(orderId, "t_good_order_tmp");
                            goodOrderMapper.insertGoodOrderTmp(goodOrder, "t_good_order_cancel");
                        }
                    } else {
                        RecipeOrder recipeOrder = recipeOrderMapper.selectTempOrder(orderId);
                        List<RecipeOrderItems> recipeOrderItemsList = recipeOrder.getRecipeOrderItemsList();
                        if (recipeOrderItemsList != null && recipeOrderItemsList.size() > 0) {
                            for (RecipeOrderItems recipeOrderItems : recipeOrderItemsList) {
                                String recipeId = recipeOrderItems.getRecipeId();
                                Integer count = recipePlanMapper.getPlanCountRecipe(recipeId, recipeOrder.getPickType(), recipeOrderItems.getPlanId());
                                if (count == null) {
                                    count = 0;
                                }
                                recipePlanMapper.updateRecipePlanCount(recipeId, recipeOrder.getPickType(),
                                        recipeOrderItems.getPlanId(), count + recipeOrderItems.getQuantity());
                            }
                        }
                        recipeOrderMapper.deleteOrderByOrderId(orderId);
                        recipeOrderMapper.insertOrder(recipeOrder, "t_recipe_order_cancel");
                    }
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ORDER_TIME_CAN_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }
    @Override
    public LsResponse getOrdersCount(Associator associator,Boolean flag) {
        LsResponse lsResponse=new LsResponse();
        Map<String,Object>map=new HashedMap();
        try {
            Integer goodTemCount=goodOrderMapper.getCountH5(associator.getAssociatorId(),associator.getCanteenId(),1,"t_good_order_tmp");
            Integer goodPaidCount=goodOrderMapper.getCountH5(associator.getAssociatorId(),associator.getCanteenId(),2,"t_good_order_paid");
            Integer goodOverCount=null;
            Integer recipeOverCount=null;
            if(flag){
                goodOverCount=goodOrderMapper.getCountH5(associator.getAssociatorId(),associator.getCanteenId(),3,"t_good_order_over");
                recipeOverCount=recipeOrderMapper.selectOverOrderListCount(associator);}
            Integer recipeTemCount=recipeOrderMapper.selectTempOrderListCount(associator);
            Integer recipePaidCount=recipeOrderMapper.selectPaidOrderListCount(associator);
            if(goodTemCount==null) {goodTemCount=0;}
            if(goodPaidCount==null){goodPaidCount=0;}
            if(goodOverCount==null){goodOverCount=0;}
            if(recipeTemCount==null){recipeTemCount=0;}
            if(recipePaidCount==null){recipePaidCount=0;}
            if(recipeOverCount==null){recipeOverCount=0;}
            map.put("goodTemCount",goodTemCount);
            map.put("goodPaidCount",goodPaidCount);
            map.put("goodOverCount",goodOverCount);
            map.put("recipeTemCount",recipeTemCount);
            map.put("recipePaidCount",recipePaidCount);
            map.put("recipeOverCount",recipeOverCount);
            lsResponse.setData(map);
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

}
