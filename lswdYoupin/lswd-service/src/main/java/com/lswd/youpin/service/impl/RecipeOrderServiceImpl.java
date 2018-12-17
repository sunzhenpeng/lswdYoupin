package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.RecipeOrderItemsMapper;
import com.lswd.youpin.dao.lsyp.RecipeOrderMapper;
import com.lswd.youpin.dao.lsyp.RecipePlanMapper;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.lsyp.RecipeOrder;
import com.lswd.youpin.model.lsyp.RecipeOrderItems;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhao on 2017/6/21.
 */
@Service
public class RecipeOrderServiceImpl implements RecipeOrderService {
    private final Logger log = LoggerFactory.getLogger(RecipeOrderServiceImpl.class);

    @Autowired
    private RecipeOrderMapper recipeOrderMapper;
    @Autowired
    private RecipeOrderItemsMapper recipeOrderItemsMapper;
    @Autowired
    private RecipePlanMapper recipePlanMapper;

    @Override
    public LsResponse getRecipeOrderList(String keyword, Integer pageNum, Integer pageSize, Integer flag,
                                         List<String> canteenIds, Integer dataTime,Integer payType) {
        LsResponse lsResponse = new LsResponse();
        try {
            int offSet = 0;
            if (keyword != null && !"".equals(keyword)) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = "%" + keyword + "%";
            } else {
                keyword = "";
            }
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (dataTime != null && dataTime != 0) {
                dataTime = dataTime * 30;
            }
            switch (flag) {
                case 0:
                    lsResponse = selectAll(keyword, pageSize, offSet, canteenIds, dataTime,payType);
                    break;
                case 1:
                    lsResponse = selectTempAll(keyword, pageSize, offSet, canteenIds, dataTime,payType);
                    break;
                case 2:
                    lsResponse = selectPaidAll(keyword, pageSize, offSet, canteenIds, dataTime,payType);
                    break;
                case 3:
                    lsResponse = selectOverAll(keyword, pageSize, offSet, canteenIds, dataTime,payType);
                    break;
                case 4:
                    lsResponse = selectRefundAll(keyword, pageSize, offSet,canteenIds, dataTime,payType);
                    break;
                default:
                    lsResponse.checkSuccess(false, CodeMessage.RECIPEORDER_NO_FLAG.name());
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }


    //查找全部订单列表
    private LsResponse selectAll(String keyword, Integer pageSize, Integer offSet,  List<String> canteenIds, Integer dataTime,Integer payType) {
        LsResponse lsResponse = new LsResponse();
        try {
            int total = recipeOrderMapper.selectOrdersCount(keyword, canteenIds, dataTime,payType);
            List<RecipeOrder> recipeOrders = recipeOrderMapper.selectOrdersAll(keyword, pageSize, offSet, canteenIds, dataTime,payType);
            lsResponse=getRecipeOrders(recipeOrders,total);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    private LsResponse getRecipeOrders(List<RecipeOrder> recipeOrders, int total) {
        LsResponse lsResponse=new LsResponse();
        if (recipeOrders != null && recipeOrders.size() > 0) {
            List<RecipeOrder>recipeOrderList=new ArrayList<>();
            for(RecipeOrder recipeOrder:recipeOrders)
            {
                List<RecipeOrderItems> recipeOrderItemses=recipeOrderItemsMapper.getRecipeOrderItemsList(recipeOrder.getOrderId());
                if(recipeOrderItemses!=null&&recipeOrderItemses.size()>0)
                {
                    recipeOrder.setRecipeOrderItemsList(recipeOrderItemses);
                    recipeOrderList.add(recipeOrder);
                }else{
                    total--;
                }
            }
            lsResponse.setData(recipeOrderList);
            lsResponse.setTotalCount(total);
        } else {
            lsResponse.checkSuccess(false, CodeMessage.RECIPEORDER_NO_ERR.name());
        }
        return  lsResponse;
    }


    //查找未付款订单列表
    private LsResponse selectTempAll(String keyword, Integer pageSize, Integer offSet,  List<String> canteenIds, Integer dataTime,Integer payType) {
        LsResponse lsResponse = new LsResponse();
        try {
            int total = recipeOrderMapper.selectOrderTmpCount(keyword, canteenIds, dataTime,payType);
            List<RecipeOrder> recipeOrdersByTemp = recipeOrderMapper.selectOrderTmpAll(keyword, pageSize, offSet, canteenIds, dataTime,payType);
            lsResponse=getRecipeOrders(recipeOrdersByTemp,total);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    //查找全部已支付订单列表
    private LsResponse selectPaidAll(String keyword, Integer pageSize, Integer offSet,  List<String> canteenIds, Integer dataTime,Integer payType) {
        LsResponse lsResponse = new LsResponse();
        try {
            int total = recipeOrderMapper.selectPaidCount(keyword, canteenIds, dataTime,payType);
            List<RecipeOrder> recipeOrdersPaid = recipeOrderMapper.selectPaidAll(keyword, pageSize, offSet, canteenIds, dataTime,payType);
            lsResponse=getRecipeOrders(recipeOrdersPaid,total);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    //查找已完成订单列表
    private LsResponse selectOverAll(String keyword, Integer pageSize, Integer offSet, List<String> canteenIds, Integer dataTime,Integer payType) {
        LsResponse lsResponse = new LsResponse();
        try {
            int total = recipeOrderMapper.selectOverCount(keyword, canteenIds, dataTime,payType);
            List<RecipeOrder> recipeOrdersOver = recipeOrderMapper.selectOverAll(keyword, pageSize, offSet, canteenIds, dataTime,payType);
            lsResponse=getRecipeOrders(recipeOrdersOver,total);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    //查找已退款订单列表
    private LsResponse selectRefundAll(String keyword, Integer pageSize, Integer offSet, List<String> canteenIds, Integer dataTime,Integer payType) {
        LsResponse lsResponse = new LsResponse();
        try {
            int total = recipeOrderMapper.selectRefundCount(keyword, canteenIds, dataTime,payType);
            List<RecipeOrder> recipeOrdersRefund = recipeOrderMapper.selectRefundAll(keyword, pageSize, offSet, canteenIds, dataTime,payType);
            lsResponse=getRecipeOrders(recipeOrdersRefund,total);
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeOrder(Integer status, String orderId) {
        LsResponse lsResponse = new LsResponse();
        switch (status) {
            case 1:
                lsResponse = selectTempOrder(orderId);
                break;
            case 2:
                lsResponse = selectPaidOrder(orderId);
                break;
            case 3:
                lsResponse = selectOverOrder(orderId);
            break;
            case 4:
                lsResponse = selectRefundOrder(orderId);break;
            case 5:
                lsResponse = selectRefundOrder(orderId);
                break;
            case 6:
                lsResponse = selectOverOrder(orderId);
                break;
            default:
                lsResponse.checkSuccess(false, CodeMessage.RECIPEORDER_NO_FLAG.name());
        }
        return lsResponse;
    }

    private LsResponse selectRefundOrder(String orderId) {
        LsResponse lsResponse = new LsResponse();
        RecipeOrder recipeOrder = recipeOrderMapper.selectRefundOrder(orderId);
        lsResponse.setData(recipeOrder);
        return lsResponse;
    }

    private LsResponse selectOverOrder(String orderId) {
        LsResponse lsResponse = new LsResponse();
        RecipeOrder recipeOrder = recipeOrderMapper.selectOverOrder(orderId);
        lsResponse.setData(recipeOrder);
        return lsResponse;
    }

    private LsResponse selectPaidOrder(String orderId) {
        LsResponse lsResponse = new LsResponse();
        RecipeOrder recipeOrder = recipeOrderMapper.selectPaidOrder(orderId);
        lsResponse.setData(recipeOrder);
        return lsResponse;
    }

    private LsResponse selectTempOrder(String orderId) {
        LsResponse lsResponse = new LsResponse();
        RecipeOrder recipeOrder = recipeOrderMapper.selectTempOrder(orderId);
        lsResponse.setData(recipeOrder);
        return lsResponse;
    }

    @Override
    public LsResponse getAsrecipeOrderList(Associator associator, Integer pageNum, Integer pageSize, Integer flag) {
        LsResponse lsResponse = new LsResponse();
        int offSet = 0;
        if (pageSize != null && pageNum != null) {
            offSet = (pageNum - 1) * pageSize;
        }
        switch (flag) {
            case 0:
                lsResponse = selectOrderList(associator, offSet, pageSize);
                break;
            case 1:
                lsResponse = selectTempOrderList(associator, offSet, pageSize);
                break;
            case 2:
                lsResponse = selectPaidOrderList(associator, offSet, pageSize);
                break;
            case 3:
                lsResponse = selectOverOrderList(associator, offSet, pageSize);
                break;
            case 4:
                lsResponse = selectRefundOrderList(associator, offSet, pageSize);
                break;
            case 5:
                lsResponse = selectCanclOrderList(associator, offSet, pageSize);
                break;
            case 6:
                lsResponse = selectEvaluateOrderList(associator, offSet, pageSize);
                break;
            default:
                lsResponse.checkSuccess(false, CodeMessage.RECIPEORDER_NO_FLAG.name());
        }
        return lsResponse;
    }

    private LsResponse selectCanclOrderList(Associator associator, int offSet, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        Integer total = recipeOrderMapper.selectCanclOrderListCount(associator);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.selectCancelOrderList(associator, offSet, pageSize);
        lsResponse.setData(recipeOrders);
        if (total == null) {
            total = 0;
        }
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse selectEvaluateOrderList(Associator associator, int offSet, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        Integer total = recipeOrderMapper.selectEvaluateOrderListCount(associator);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.selectEvaluateOrderList(associator, offSet, pageSize);
        lsResponse.setData(recipeOrders);
        if (total == null) {
            total = 0;
        }
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse selectOrderList(Associator associator, int offSet, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        Integer total = recipeOrderMapper.selectOrderListCount(associator);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.selectOrderList(associator, offSet, pageSize);
        lsResponse.setData(recipeOrders);
        if (total == null) {
            total = 0;
        }
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse selectRefundOrderList(Associator associator, int offSet, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        Integer total = recipeOrderMapper.selectRefundOrderListCount(associator);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.selectRefundOrderList(associator, offSet, pageSize);
        lsResponse.setData(recipeOrders);
        if (total == null) {
            total = 0;
        }
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse selectOverOrderList(Associator associator, int offSet, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        Integer total = recipeOrderMapper.selectOverOrderListCount(associator);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.selectOverOrderList(associator, offSet, pageSize);
        lsResponse.setData(recipeOrders);
        if (total == null) {
            total = 0;
        }
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse selectPaidOrderList(Associator associator, int offSet, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        Integer total = recipeOrderMapper.selectPaidOrderListCount(associator);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.selectPaidOrderList(associator, offSet, pageSize);
        lsResponse.setData(recipeOrders);
        if (total == null) {
            total = 0;
        }
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse selectTempOrderList(Associator associator, int offSet, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        Integer total = recipeOrderMapper.selectTempOrderListCount(associator);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.selectTempOrderList(associator, offSet, pageSize);
        lsResponse.setData(recipeOrders);
        if (total == null) {
            total = 0;
        }
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    @Override
    public LsResponse deleteOrder(Associator associator, String orderId) {
        LsResponse lsResponse = new LsResponse();
        try {
            RecipeOrder recipeOrder = recipeOrderMapper.selectTempOrder(orderId);
            Integer b = recipeOrderMapper.deleteOrderByOrderId(orderId);
            if (b != null && b > 0) {
                recipeOrder.setStatus((short) 5);
                recipeOrder.setPayType((short) 0);
                recipeOrder.setUpdateTime(Dates.now());
                Integer c = recipeOrderMapper.insertCancelOrder(recipeOrder, associator);
                if (c != null && c > 0) {
                    for(RecipeOrderItems items:recipeOrder.getRecipeOrderItemsList())
                    {
                        Integer num= recipePlanMapper.getPlanCountRecipe(items.getRecipe().getRecipeId(),recipeOrder.getPickType()-1,items.getPlanId());
                        System.out.println(num);
                        if(num==null)
                        {
                            num=0;
                        }
                        recipePlanMapper.updateRecipePlanCount(items.getRecipe().getRecipeId(),recipeOrder.getPickType()-1,items.getPlanId(),num+items.getQuantity());
                    }
                    lsResponse.setMessage(CodeMessage.ORDER_CANCEL_SUCCESS.name());
                }
            } else {
                lsResponse.checkSuccess(false,CodeMessage.ORDER_CANCEL_ERR.name());
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getOrderListWx(Integer pageNum, Integer pageSize, List<String> canteenIds, Date startDate,
                                     Date endDate, TenantAssociator tenantAssociator, Integer flag) {
        LsResponse lsResponse = new LsResponse();
        int offSet = 0;
        if (pageSize != null && pageNum != null) {
            offSet = (pageNum - 1) * pageSize;
        }
        switch (flag) {
            case 0:
                lsResponse = getOrderListWxAll(offSet, pageSize, canteenIds, startDate, endDate);
                break;
            case 1:
                lsResponse = getOrderListWxTmp(offSet, pageSize, canteenIds, startDate, endDate);
                break;
            case 2:
                lsResponse = getOrderListWxPaid(offSet, pageSize, canteenIds, startDate, endDate);
                break;
            case 3:
                lsResponse = getOrderListWxOver(offSet, pageSize, canteenIds, startDate, endDate);
                break;
            case 4:
                lsResponse = getOrderListWxRefund(offSet, pageSize, canteenIds, startDate, endDate);
                break;
            default:
                lsResponse.checkSuccess(false, CodeMessage.RECIPEORDER_NO_FLAG.name());
        }
        return lsResponse;
    }

    private LsResponse getOrderListWxRefund(int offSet, Integer pageSize, List<String> canteenIds, Date startDate,
                                            Date endDate) {
        LsResponse lsResponse = new LsResponse();
        int total = recipeOrderMapper.getOrderListWxRefundCount(canteenIds, startDate, endDate);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.getOrderListWxRefund(offSet, pageSize, canteenIds, startDate, endDate);
        lsResponse.setData(recipeOrders);
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse getOrderListWxOver(int offSet, Integer pageSize, List<String> canteenIds, Date startDate,
                                          Date endDate) {
        LsResponse lsResponse = new LsResponse();
        int total = recipeOrderMapper.getOrderListWxOverCount(canteenIds, startDate, endDate);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.getOrderListWxOver(offSet, pageSize, canteenIds, startDate, endDate);
        lsResponse.setData(recipeOrders);
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse getOrderListWxPaid(int offSet, Integer pageSize, List<String> canteenIds, Date startDate,
                                          Date endDate) {
        LsResponse lsResponse = new LsResponse();
        int total = recipeOrderMapper.getOrderListWxPaidCount(canteenIds, startDate, endDate);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.getOrderListWxPaid(offSet, pageSize, canteenIds, startDate, endDate);
        lsResponse.setData(recipeOrders);
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse getOrderListWxTmp(int offSet, Integer pageSize, List<String> canteenIds, Date startDate,
                                         Date endDate) {
        LsResponse lsResponse = new LsResponse();
        int total = recipeOrderMapper.getOrderListWxTmpCount(canteenIds, startDate, endDate);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.getOrderListWxTmp(offSet, pageSize, canteenIds, startDate, endDate);
        lsResponse.setData(recipeOrders);
        lsResponse.setTotalCount(total);
        return lsResponse;
    }

    private LsResponse getOrderListWxAll(int offSet, Integer pageSize, List<String> canteenIds, Date startDate,
                                         Date endDate) {
        LsResponse lsResponse = new LsResponse();
        int total = recipeOrderMapper.getOrderListWxAllCount(canteenIds, startDate, endDate);
        List<RecipeOrder> recipeOrders = recipeOrderMapper.getOrderListWxAll(offSet, pageSize, canteenIds, startDate, endDate);
        lsResponse.setData(recipeOrders);
        lsResponse.setTotalCount(total);
        return lsResponse;
    }


    /**
     * 根据订单编号打开菜品评价页面
     *
     * @param recipeOrderId
     * @return
     */
    @Override
    public LsResponse openRecipeCommentH5(String recipeOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            recipeOrderId = new String(recipeOrderId.getBytes("iso8859-1"), "utf-8");
            log.info("订单编号为" + recipeOrderId + "的，正在准备评论菜品");
            RecipeOrder recipeOrder = recipeOrderMapper.getOrderInOverByOrderId(recipeOrderId);
            if (recipeOrder != null) {
                List<RecipeOrderItems> items = recipeOrderItemsMapper.getRecipeOrderItemsList(recipeOrderId);
                if (items != null && items.size() > 0) {
                    recipeOrder.setRecipeOrderItemsList(items);
                    log.info("订单编号为" + recipeOrderId + "的，查看需要评论的菜品成功，共" + items.size() + "个商品");
                    lsResponse.setMessage("这是需要评价的菜品");
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("该订单没有菜品");
                    log.error("该订单没有菜品");
                }
                lsResponse.setData(recipeOrder);
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteByOrderNo(String orderNo, String table) {
        LsResponse lsResponse = new LsResponse();
        try {
            int i = recipeOrderMapper.deleteByOrderNo(orderNo, table);
            lsResponse.setData(i);
        } catch (Exception e) {
            log.info("商品订单删除失败={}", e.getMessage());
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public LsResponse insertOrderRefund(RecipeOrder recipeOrder) {
        LsResponse lsResponse = new LsResponse();
        try {
            int i = recipeOrderMapper.insertOrderRefund(recipeOrder);
            lsResponse.setData(i);
        } catch (Exception e) {
            log.info("商品订添加删除失败={}", e.getMessage());
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeOrderByOrderNo(String orderNo, String table) {
        LsResponse lsResponse = new LsResponse();
        try {
            RecipeOrder recipeOrder = recipeOrderMapper.getRecipeOrder(orderNo, table);
            lsResponse.setData(recipeOrder);
        } catch (Exception e) {
            log.info("商品查询删除失败={}", e.getMessage());
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public Integer updateStatus(String orderId) {
        return recipeOrderMapper.updateStatus(orderId);
    }

    @Override
    public LsResponse removeOrder(Associator associator, String orderId) {
        LsResponse lsResponse = new LsResponse();
        try {
                RecipeOrder recipeOrder=recipeOrderMapper.selectOrderByOrderId(orderId);
                recipeOrder.setUpdateTime(Dates.now());
                String table;
                if(recipeOrder.getStatus()==(short) 3||recipeOrder.getStatus()==(short) 6)
                {
                    table="t_recipe_order_over";
                }else if(recipeOrder.getStatus()==(short)4){
                    table="t_recipe_order_refund";
                }else{
                    table="t_recipe_order_cancel";
                }
                recipeOrder.setStatus((short) 0);
                Integer c=recipeOrderMapper.deleteStatus(recipeOrder,table);
                if (c != null && c > 0) {
                    lsResponse.setMessage(CodeMessage.ORDER_DELETE_SUCCESS.name());
                } else {
                lsResponse.checkSuccess(false,CodeMessage.ORDER_DELETE_ERR.name());
            }
        } catch (Exception e){
            e.printStackTrace();
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return lsResponse;
    }

}
