package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.RecipeOrderThin;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Car;
import com.lswd.youpin.model.lsyp.RecipeOrder;
import com.lswd.youpin.model.lsyp.RecipeOrderItems;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorService;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.RecipeOrderService;
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
 * Created by liuhao on 2017/7/18.
 */
@Component
public class RecipeOrderThinImpl implements RecipeOrderThin {
    private final Logger log = LoggerFactory.getLogger(RecipeOrderThinImpl.class);
    @Autowired
    private RecipeOrderService recipeOrderService;
    @Autowired
    private AssociatorService associatorService;
    @Autowired
    private CanteenService canteenService;
    @Autowired
    private WeiXinService weiXinService;

    @Override
    public LsResponse getRecipeOrderList(String keyword, Integer pageNum, Integer pageSize, Integer flag, String canteenId,
                                         Integer dataTime,Integer payType,User user) {
        LsResponse lsResponse=new LsResponse();
        try {
            String dataSocre=DataSourceHandle.getDataSourceType();
            List<String> canteenIds=new ArrayList<>();
            if(canteenId!=null&&!"".equals(canteenId))
            {
                canteenIds.add(canteenId);
            }else{
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                for(String canId:user.getCanteenIds().split(","))
                {
                    LsResponse ls=canteenService.getCanteenByCanteenId(canId);
                    if(ls.getData()!=null){
                    canteenIds.add(canId);
                    }
                }
            }
            if(canteenIds.size()>0){
            DataSourceHandle.setDataSourceType(dataSocre);
            lsResponse = recipeOrderService.getRecipeOrderList(keyword, pageNum, pageSize, flag, canteenIds, dataTime,payType);
            if (lsResponse.getData()!=null) {
                List<RecipeOrder> recipeOrderList = new ArrayList<>();
                List<RecipeOrder> recipeOrders = (List<RecipeOrder>) lsResponse.getData();
                if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                for (int i = 0; i < recipeOrders.size(); i++) {
                    RecipeOrder recipeOrder = recipeOrders.get(i);
                    String aId = recipeOrder.getAssociatorId();
                    LsResponse ls= associatorService.getAssociatorByAssociatorId(aId);

                    if(ls.getData()!=null)
                    {
                        Associator associator=(Associator) ls.getData();
                        recipeOrder.setAssociatorName(associator.getAccount());
                    }else{
                        recipeOrder.setAssociatorName("游客");
                    }
                    recipeOrderList.add(recipeOrder);
                }
                lsResponse.setData(recipeOrderList);
            }
            }else{
                lsResponse.setData(new ArrayList<>());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }


    @Override
    public LsResponse getRecipeOrder(Associator associator, Integer status, String orderId) {
        LsResponse lsResponse=new LsResponse();
        Map<String,Object>map=new HashedMap();
        try {
            lsResponse=recipeOrderService.getRecipeOrder(status,orderId);
            RecipeOrder recipeOrder=(RecipeOrder) lsResponse.getData();
            if(recipeOrder!=null)
            {
                recipeOrder.setCreateTimeString(Dates.format(recipeOrder.getCreateTime()));
                map.put("order", recipeOrder);
                switch (status) {
                    case 1:
                        map.put("statusName", "待付款");
                        break;
                    case 2:
                        map.put("statusName", "待取货");
                        break;
                    case 3:
                        map.put("statusName", "交易完成");
                        break;
                    case 4:
                        map.put("statusName", "已退款");
                        break;
                    case 5:
                        map.put("statusName", "已取消");
                        break;
                    case 6:
                        map.put("statusName", "已评价");
                        break;
                    default:map.put("statusName","");break;
                }
                if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(associator.getCanteenId()).getData();
                map.put("address", canteen.getAddress());
                map.put("evaluate", canteen.getAssess());
                map.put("name", associator.getAccount());
                map.put("canteenName", canteen.getCanteenName());
                lsResponse.setData(map);
            }else{
                lsResponse.checkSuccess(false, CodeMessage.RECIPEORDER_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAsrecipeOrderList(Associator associator, Integer pageNum, Integer pageSize,Integer flag) {
        LsResponse lsResponse= new LsResponse();
        try {
            lsResponse = recipeOrderService.getAsrecipeOrderList(associator,pageNum,pageSize,flag);
            List<RecipeOrder>recipeOrders=( List<RecipeOrder>)lsResponse.getData();
            if(recipeOrders!=null&&recipeOrders.size()>0)
            {
                Map<String,Object>map=new HashedMap();
                map.put("orders",recipeOrders);
                if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                Canteen canteen=(Canteen) canteenService.getCanteenByCanteenId(associator.getCanteenId()).getData();
                map.put("evaluate",canteen.getAssess());
                lsResponse.setData(map);
            }else{
                lsResponse.setData(new ArrayList<>());
                lsResponse.checkSuccess(false,CodeMessage.RECIPEORDER_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getOrderListWx(Integer pageNum, Integer pageSize, String canteenId, String dataTime, TenantAssociator tenantAssociator,Integer flag) {
        LsResponse lsResponse = new LsResponse();
        Map<String, Object> map = new HashedMap();
        List<String> canteenIds=new ArrayList<>();
        Date startDate;
        Date endDate;
        try {
            if (dataTime == null||"".equals(dataTime)) {
                startDate = Dates.startOfDay(Dates.now());
                endDate = Dates.endOfDay(Dates.now());
            }else{
                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
                ParsePosition pos = new ParsePosition(0);
                Date date = time.parse(dataTime, pos);
                startDate = Dates.startOfDay(date);
                endDate = Dates.endOfDay(date);
            }
            map.put("startDate", Dates.addMonths(startDate,1));
            map.put("endDate", Dates.addMonths(startDate,-1));
            String dataSource = DataSourceHandle.getDataSourceType();
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse= weiXinService.lookCanteen(tenantAssociator.getAssociatorId());
            if(lsResponse.getSuccess()){
                List<Canteen> canteens = (List<Canteen>) lsResponse.getData();
                if (canteens != null && canteens.size() > 0) {
                    DataSourceHandle.setDataSourceType(dataSource);
                    if (canteenId == null || "".equals(canteenId)) {
                        for (int i = 0; i < canteens.size(); i++) {
                            canteenIds.add(canteens.get(i).getCanteenId());
                        }
                    } else {
                        canteenIds.add(canteenId);
                    }
                    lsResponse= recipeOrderService.getOrderListWx(pageNum, pageSize, canteenIds, startDate, endDate,tenantAssociator,flag);
                    List<RecipeOrder>orderList=(List<RecipeOrder>)lsResponse.getData();
                    List<RecipeOrder>orders=new ArrayList<>();
                    if(orderList!=null&&orderList.size()>0){
                        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                        for (RecipeOrder recipeOrder : orderList) {
                            Canteen canteen=new Canteen();
                            List<RecipeOrderItems>recipeOrderItemses=new ArrayList<>();
                            for (String can : canteenIds) {
                               Canteen  c= (Canteen) canteenService.getCanteenByCanteenId(can).getData();
                                if (can.equals(recipeOrder.getCanteenId())) {
                                    canteen.setCanteenName(c.getCanteenName());
                                    canteen.setCanteenId(c.getCanteenId());
                                    break;
                                }
                            }
                            for (RecipeOrderItems recipeOrderItems:recipeOrder.getRecipeOrderItemsList())
                            {
                                recipeOrderItems.setCanteen(canteen);
                                recipeOrderItemses.add(recipeOrderItems);
                            }
                            recipeOrder.setRecipeOrderItemsList(recipeOrderItemses);
                            orders.add(recipeOrder);
                        }
                        lsResponse.setSuccess(true);
                    }else{
                        lsResponse.setSuccess(false);
                    }
                    map.put("orderList", orders);
                    lsResponse.setData(map);
                }else{
                    lsResponse.setSuccess(false);
                    lsResponse.setData("");
                }
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setData("");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }

        return lsResponse;
    }

    @Override
    public LsResponse getRecipeOrderWx(Integer status, String orderId) {
        LsResponse lsResponse = new LsResponse();
        Map<String, Object> map = new HashedMap();
        try {
            lsResponse = recipeOrderService.getRecipeOrder(status, orderId);
            RecipeOrder recipeOrder = (RecipeOrder) lsResponse.getData();
            if (recipeOrder != null) {
                map.put("order", recipeOrder);
                if (status == 1) {
                    map.put("statusName", "待付款");
                } else if (status == 2) {
                    map.put("statusName", "待取货");
                } else if (status == 3) {
                    map.put("statusName", "交易完成");
                } else if (status == 4) {
                    map.put("statusName", "已退款");
                } else if (status == 5) {
                    map.put("statusName", "已取消");
                }
                String associatorId = recipeOrder.getAssociatorId();
                String canteenId = recipeOrder.getCanteenId();
                if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                Associator associator = (Associator) associatorService.getAssociatorByAssociatorId(associatorId).getData();
                if (associator != null) {
                    map.put("account", associator.getAccount());
                    map.put("associatorTel",associator.getTelephone());
                } else {
                    map.put("account", "抱歉出错了");
                }
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(canteenId).getData();
                if (canteen != null) {
                    map.put("address", canteen.getAddress());
                    map.put("canteenName", canteen.getCanteenName());
                    map.put("order",recipeOrder);
                } else {
                    map.put("address", "");
                    map.put("canteenName", "");
                }
                lsResponse.setData(map);
            }
        } catch (Exception e) {
          log.error(e.toString());
          lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse openRecipeCommentH5(String recipeOrderId) {
        LsResponse lsResponse = null;
        try {
            lsResponse = recipeOrderService.openRecipeCommentH5(recipeOrderId);
            RecipeOrder recipeOrder = (RecipeOrder)lsResponse.getData();
            if (recipeOrder != null){
                if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(recipeOrder.getCanteenId()).getData();
                if (canteen != null){
                    recipeOrder.setCanteenLogo(canteen.getImageUrl());
                    recipeOrder.setCanteenName(canteen.getCanteenName());
                }
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse lookOrder(String orderId,Associator associator) {
        LsResponse lsResponse=new LsResponse();
        try {
            RecipeOrder recipeOrder=(RecipeOrder) recipeOrderService.getRecipeOrder(1,orderId).getData();
            String canteenId=recipeOrder.getCanteenId();
            String dataSource=DataSourceHandle.getDataSourceType();
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType()))
            {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            Canteen cantene=(Canteen) canteenService.getCanteenByCanteenId(canteenId).getData();
            System.out.println(cantene);
            if(Dates.timeInterval(recipeOrder.getCreateTime(),Dates.now())<1800)
            {
                  Map<String,Object>map=new HashedMap();
                  map.put("address",cantene.getAddress());
                  Associator a=new Associator();
                  a.setAccount(associator.getAccount());
                  a.setTelephone(associator.getTelephone());
                  map.put("associator",a);
                  List<Map<String,Object>>orderList=new ArrayList<>();
                  Map<String,Object>repiceMap=new HashedMap();
                  repiceMap.put("pickTime",recipeOrder.getEatTime());
                  repiceMap.put("orderId",recipeOrder.getOrderId());
                  repiceMap.put("priceAll",recipeOrder.getOrderAmount());
                  List<Car> myCars = new ArrayList<>();
                 System.out.println(recipeOrder.getRecipeOrderItemsList());
                for (int i = 0; i < recipeOrder.getRecipeOrderItemsList().size(); i++) {
                    RecipeOrderItems item = recipeOrder.getRecipeOrderItemsList().get(i);
                    System.out.print(item);
                    Car car = new Car();
                    car.setNumber(item.getQuantity());
                    car.setImg(item.getRecipe().getImageurl());
                    car.setMsg(item.getRecipe().getRecipeName());
                    car.setChecked(true);
                    car.setPrice(String.valueOf(item.getOrderAmount()));
                    myCars.add(car);
                }
                repiceMap.put("good",myCars);
                orderList.add(repiceMap);
                map.put("orders",orderList);
                map.put("back","food.payment");
                lsResponse.setData(map);
            }else{
                DataSourceHandle.setDataSourceType(dataSource);
                recipeOrderService.deleteOrder(associator, orderId);
                lsResponse.checkSuccess(false, CodeMessage.ORDER_TIME_OUT.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

}
