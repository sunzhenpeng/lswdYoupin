package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.GoodOrderThin;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.GoodOrder;
import com.lswd.youpin.model.lsyp.GoodOrderItems;
import com.lswd.youpin.model.lsyp.RecipeOrder;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorService;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.GoodOrderService;
import com.lswd.youpin.service.WeiXinService;
import com.lswd.youpin.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/17.
 */
@Service
public class GoodOrderThinImpl implements GoodOrderThin{

    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private AssociatorService associatorService;
    @Autowired
    private CanteenService canteenService;
    @Autowired
    private WeiXinService weiXinService;

    @Override
    public LsResponse getGoodOrderList(User user, String keyword, String associatorId, String canteenId, String orderTime,Integer dateflag, Integer payType,Integer flag, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = null;
        try {
            lsResponse = goodOrderService.getGoodOrderList(user,keyword,associatorId,canteenId,orderTime,dateflag,payType,flag,pageNum,pageSize);
            List<GoodOrder> goodOrders = (List<GoodOrder>)lsResponse.getData();
            if (goodOrders.size() > 0 && goodOrders != null){
                if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                for (GoodOrder goodOrder : goodOrders){
                    switch (goodOrder.getStatus()){
                        case 4 : goodOrder.setStatusName("已退款");break;
                        case 5 : goodOrder.setStatusName("已取消");break;
                        case -4 : goodOrder.setStatusName("已退款,用户删除");break;
                        case -5 : goodOrder.setStatusName("已取消,用户删除");break;
                        default : goodOrder.setStatusName(""); break;
                    }
                    Associator associator = (Associator) associatorService.getAssociatorByAssociatorId(goodOrder.getAssociatorId()).getData();
                    if (associator != null){
                        goodOrder.setAssociatorName(associator.getAccount());
                    }
                    String pickingTime = Dates.format(goodOrder.getPickingTime(),"yyyy-MM-dd");
                    Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(goodOrder.getCanteenId()).getData();
                    if (canteen != null){
                        String canteenInterval = canteen.getPickingTime();
                        goodOrder.setPickintTimeInterval(pickingTime + " ("+canteenInterval+")");
                    }
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("没有订单信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getGoodOrderListH5Show(String associatorId, Integer flag, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = null;
        try {
            lsResponse = goodOrderService.getGoodOrderListH5Show(associatorId,flag,canteenId,pageNum,pageSize);
            List<GoodOrder> goodOrderList = (List<GoodOrder>)lsResponse.getData();
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            if (goodOrderList != null && goodOrderList.size() > 0){
                Associator associator = (Associator) associatorService.getAssociatorByAssociatorId(goodOrderList.get(0).getAssociatorId()).getData();
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(goodOrderList.get(0).getCanteenId()).getData();
                for (GoodOrder goodOrder :goodOrderList){
                    String pickingTime = Dates.format(goodOrder.getPickingTime(),"yyyy-MM-dd");
                    if (canteen != null){
                        goodOrder.setCanteenTEL(canteen.getTelephone());
                        goodOrder.setCanteenAddress(canteen.getAddress());
                        goodOrder.setComment(canteen.getAssess());
                        String canteenInterval = canteen.getPickingTime();
                        goodOrder.setPickintTimeInterval(pickingTime + " ("+canteenInterval+")");
                    }
                    if (associator != null){
                        goodOrder.setAssociator(associator);
                    }
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("没有查询到订单,订单的总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getGoodOrderDetailsH5(Integer status,String goodOrderId) {
        LsResponse lsResponse = null;
        try {
            lsResponse = goodOrderService.getGoodOrderDetailsH5(status,goodOrderId);
            GoodOrder goodOrder = (GoodOrder)lsResponse.getData();
            if (goodOrder != null){
                if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                Associator associator = (Associator) associatorService.getAssociatorByAssociatorId(goodOrder.getAssociatorId()).getData();
                if (associator != null){
                    goodOrder.setAssociatorName(associator.getAccount());
                }
                String pickingTime = Dates.format(goodOrder.getPickingTime(),"yyyy-MM-dd");
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(goodOrder.getCanteenId()).getData();
                if (canteen != null){
                    goodOrder.setCanteenName(canteen.getCanteenName());
                    goodOrder.setCanteenAddress(canteen.getAddress());
                    goodOrder.setCanteenTEL(canteen.getTelephone());
                    goodOrder.setComment(canteen.getAssess());
                    String canteenInterval = canteen.getPickingTime();
                    goodOrder.setPickintTimeInterval(pickingTime + " ("+canteenInterval+")");
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("没有查询到订单,订单的总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @Override
    public LsResponse paidNowH5(Associator associator, String goodOrderId) {
        LsResponse lsResponse = null;
        TemporaryGoodOrderPay temporaryGoodOrderPay = new TemporaryGoodOrderPay();
        List<TemporaryOrders> orders = new ArrayList<>();
        try {
            lsResponse = goodOrderService.paidNowH5(goodOrderId);
            GoodOrder goodOrder = (GoodOrder)lsResponse.getData();
            if (goodOrder != null){
                TemporaryOrders temporaryOrders = new TemporaryOrders(goodOrder.getPickingTime(),goodOrder.getOrderId(),goodOrder.getOrderAmount());
                List<TemporaryGood> goods = new ArrayList<>();
                List<GoodOrderItems> orderItems = goodOrder.getGoodOrderItems();
                if (orderItems.size() > 0 && orderItems != null) {
                    for (GoodOrderItems items : orderItems) {
                        TemporaryGood temporaryGood = new TemporaryGood();
                        temporaryGood.setNumber(items.getQuantity());
                        temporaryGood.setImg(items.getGood().getImageurl());
                        temporaryGood.setMsg(items.getGood().getGoodName()+" 【"+items.getGood().getStandard() +"】 ");
                        temporaryGood.setPrice(items.getGood().getPrice());
                        goods.add(temporaryGood);
                    }
                }
                if (goods != null && goods.size() >0){
                    temporaryOrders.setGood(goods);
                    orders.add(temporaryOrders);
                    temporaryGoodOrderPay.setOrders(orders);
                }
                if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(goodOrder.getCanteenId()).getData();
                if (canteen != null){
                    temporaryGoodOrderPay.setAddress(canteen.getAddress());
                }
                temporaryGoodOrderPay.setAssociator(associator);
                temporaryGoodOrderPay.setBack("commodity.payment");
                lsResponse.setMessage("这就是您想要的信息");
                lsResponse.setData(temporaryGoodOrderPay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @Override
    public LsResponse applyRefundGorRH5(String orderId,String canteenId) {
        LsResponse lsResponse = null;
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if(!DataSourceConst.LSWD.equals(dataSource)){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(canteenId).getData();
            String deadLine = "";
            if (canteen != null){
                deadLine = canteen.getDeadline().replace(":","").trim();
            }
            DataSourceHandle.setDataSourceType(dataSource);
            lsResponse = goodOrderService.applyRefundGorRH5(orderId,deadLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @Override
    public LsResponse openGoodCommentH5(String goodOrderId) {
        LsResponse lsResponse = null;
        try {
            lsResponse = goodOrderService.openGoodCommentH5(goodOrderId);
            GoodOrder goodOrder = (GoodOrder)lsResponse.getData();
            if (goodOrder != null){
                if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(goodOrder.getCanteenId()).getData();
                if (canteen != null){
                    goodOrder.setCanteenName(canteen.getCanteenName());
                    goodOrder.setCanteenLogo(canteen.getImageUrl());
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("没有查询到该订单");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getGoodOrderListWxShow(String associatorId, Integer flag, String canteenId, String orderTime,Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = null;
        String[] canteenIds = null;
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse= weiXinService.lookCanteen(associatorId);
            if(lsResponse.getSuccess()){
            List<Canteen>canteens=(List<Canteen>)lsResponse.getData();
            if (canteens != null&&canteens.size()>0){
                canteenIds = new String[canteens.size()];
                for (int i=0;i<canteenIds.length;i++){
                    canteenIds[i] = canteens.get(i).getCanteenId();
                }
            }
            if (canteenIds == null){
                lsResponse.setAsFailure();
                lsResponse.setMessage("不好意思，该用户暂未绑定餐厅，请到商家后台给该用户绑定餐厅");
                return lsResponse;
            }
            DataSourceHandle.setDataSourceType(dataSource);
            lsResponse = goodOrderService.getGoodOrderListWxShow(canteenIds,flag,canteenId,orderTime,pageNum,pageSize);
            List<GoodOrder> goodOrderList =(List<GoodOrder>)lsResponse.getData();
            for (GoodOrder goodOrder : goodOrderList){
                switch (goodOrder.getStatus()){
                    case 1:goodOrder.setStatusName("未支付，等待支付"); break;
                    case 2:goodOrder.setStatusName("已支付，待取货"); break;
                    case 3:goodOrder.setStatusName("交易完成"); break;
                    case 6:goodOrder.setStatusName("交易完成"); break;
                    case 4:goodOrder.setStatusName("已退款"); break;
                    case 5:goodOrder.setStatusName("已取消");break;
                    case -3:goodOrder.setStatusName("交易完成");break;
                    case -4:goodOrder.setStatusName("已退款（用户已删除）");break;
                    case -5:goodOrder.setStatusName("已取消（用户已删除）");break;
                    case -6:goodOrder.setStatusName("交易完成");break;
                    default:goodOrder.setStatusName(""); break;
                }
            }
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setMessage("不好意思，该用户暂未绑定餐厅，请到商家后台给该用户绑定餐厅");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @Override
    public LsResponse confirmOrderInfoWx(TenantAssociator tenantAssociator, String orderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] canteenIds = null;
        try {
            lsResponse = goodOrderService.confirmOrderInfoWx(orderId);
            if (lsResponse.getMessage().equals("扫码失败，原因是：该订单不存在")){
                Map<String,Boolean> map = new HashMap<>();
                map.put("info",false);
                lsResponse.setData(map);
                lsResponse.setAsFailure();
                lsResponse.setMessage("订单信息有误");
                return lsResponse;
            }
            if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            List<Canteen> canteens = (List<Canteen>) weiXinService.lookCanteen(tenantAssociator.getAssociatorId()).getData();
            if (canteens != null){
                canteenIds = new String[canteens.size()];
                for (int i=0;i<canteenIds.length;i++){
                    canteenIds[i] = canteens.get(i).getCanteenId();
                }
            }
            String flag = orderId.substring(orderId.length()-1,orderId.length());
            if (flag.equals("1")){
                GoodOrder goodOrder = (GoodOrder)lsResponse.getData();
                if (!ifexist(canteenIds,goodOrder.getCanteenId())){
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("不好意思，您暂无权限");
                    return lsResponse;
                }
                Associator associator = (Associator) associatorService.getAssociatorByAssociatorId(goodOrder.getAssociatorId()).getData();
                if (associator != null){
                    goodOrder.setAssociatorName(associator.getAccount());
                    goodOrder.setAssociatorTel(associator.getTelephone());
                }
                String pickingTime = Dates.format(goodOrder.getPickingTime(),"yyyy-MM-dd");
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(goodOrder.getCanteenId()).getData();
                if (canteen != null){
                    goodOrder.setCanteenName(canteen.getCanteenName());
                    String canteenInterval = canteen.getPickingTime();
                    goodOrder.setPickintTimeInterval(pickingTime + " ("+canteenInterval+")");
                }
            }else if (flag.equals("2")){
                RecipeOrder recipeOrder = (RecipeOrder)lsResponse.getData();
                switch (recipeOrder.getPayType()){
                    case 1:recipeOrder.setPayTypeName("早餐");break;
                    case 2:recipeOrder.setPayTypeName("午餐");break;
                    case 3:recipeOrder.setPayTypeName("晚餐");break;
                    default:break;
                }
                if (!ifexist(canteenIds,recipeOrder.getCanteenId())){
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("不好意思，您暂无权限");
                    return lsResponse;
                }
                Associator associator = (Associator) associatorService.getAssociatorByAssociatorId(recipeOrder.getAssociatorId()).getData();
                if (associator != null){
                    recipeOrder.setAssociatorName(associator.getAccount());
                    recipeOrder.setAssociatorTel(associator.getTelephone());
                }
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(recipeOrder.getCanteenId()).getData();
                if (canteen != null){
                    recipeOrder.setCanteenName(canteen.getCanteenName());
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("没有查询到该订单");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getGoodOrderDetailsWx(Integer status, String goodOrderId) {
        LsResponse lsResponse = null;
        try {
            lsResponse = goodOrderService.getGoodOrderDetailsWx(status,goodOrderId);
            GoodOrder goodOrder = (GoodOrder)lsResponse.getData();
            if (goodOrder != null){
                if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())){
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                }
                Associator associator = (Associator) associatorService.getAssociatorByAssociatorId(goodOrder.getAssociatorId()).getData();
                if (associator != null){
                    goodOrder.setAssociatorName(associator.getAccount());
                    goodOrder.setAssociatorTel(associator.getTelephone());
                }
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(goodOrder.getCanteenId()).getData();
                if (canteen != null){
                    goodOrder.setCanteenName(canteen.getCanteenName());
                    goodOrder.setCanteenAddress(canteen.getAddress());
                    goodOrder.setCanteenTEL(canteen.getTelephone());
                    goodOrder.setCanteenLeader(canteen.getLeader());
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("没有查询到该订单");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }

    private boolean ifexist(String[] array,String keyword){
        boolean flag = false;
        for (int i = 0;i < array.length; i++){
            if (array[i].equals(keyword)){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
