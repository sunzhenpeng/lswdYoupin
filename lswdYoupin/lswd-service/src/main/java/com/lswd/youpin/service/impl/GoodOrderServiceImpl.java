package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.GoodOrderService;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhenguanqi on 2017/6/22.
 */
@Service
public class GoodOrderServiceImpl implements GoodOrderService {
    private final Logger log = LoggerFactory.getLogger(GoodOrderServiceImpl.class);
    private static final String T_GOOD_ORDER_TMP = "t_good_order_tmp";
    private static final String T_GOOD_ORDER_CANCEL = "t_good_order_cancel";
    private static final String T_GOOD_ORDER_PAID = "t_good_order_paid";
    private static final String T_GOOD_ORDER_REFUND = "t_good_order_refund";
    private static final String T_GOOD_ORDER_OVER = "t_good_order_over";
    private String tableName[] = new String[]{T_GOOD_ORDER_TMP, T_GOOD_ORDER_CANCEL, T_GOOD_ORDER_OVER, T_GOOD_ORDER_PAID, T_GOOD_ORDER_REFUND};

    @Autowired
    private GoodOrderMapper goodOrderMapper;
    @Autowired
    private GoodOrderItemsMapper goodOrderItemsMapper;
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private RecipeOrderMapper recipeOrderMapper;
    @Autowired
    private RecipeOrderItemsMapper recipeOrderItemsMapper;
    @Autowired
    private GoodPlanItemsMapper goodPlanItemsMapper;
    @Autowired
    private GoodPlanMapper goodPlanMapper;


    @Override
    public LsResponse getGoodOrderList(User user, String keyword, String associatorId, String canteenId, String orderTime,
                                       Integer dateflag, Integer payType, Integer flag, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        StringBuilder sb = new StringBuilder();
        Integer offset = null;
        try {
            String[] canIds = user.getCanteenIds().split(",");
            keyword = StringsUtil.encodingChange(keyword);
            associatorId = StringsUtil.encodingChange(associatorId);
            canteenId = StringsUtil.encodingChange(canteenId);
            orderTime = StringsUtil.encodingChange(orderTime);
            if (pageNum != null && !("").equals(pageNum)) {
                offset = (pageNum - 1) * pageSize;
            }
            if (flag == 0) { //查询全部订单
                lsResponse = getGoodOrderAllList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, offset, pageSize);
            } else if (flag == 1) {//查询已经提交的订单
                lsResponse = getGoodOrderTmpList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, offset, pageSize);
            } else if (flag == 2) {//查询已经支付成功的订单
                lsResponse = getGoodOrderPaidList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, offset, pageSize);
            } else if (flag == 3) {//查询已经完成交易的订单
                lsResponse = getGoodOrderOverList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, offset, pageSize);
            } else if (flag == 4) {//查询已经取消/退款的订单
                lsResponse = getGoodOrderRefundList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, offset, pageSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取商品订单出错", e.toString());
        }
        return lsResponse;
    }

    /**
     * 根据订单编号得到订单详情，根据订单详情表中的good_id查询商品的具体信息
     */
    public List<GoodOrderItems> getGoodOrderItems(String orderId) {
        List<GoodOrderItems> goodOrderItemses = goodOrderItemsMapper.getGoodOrderItemsList(orderId);
        if (goodOrderItemses != null && goodOrderItemses.size() > 0) {
            for (GoodOrderItems items : goodOrderItemses) {
                items.getGood().setPrice(items.getOrderAmount());
            }
            return goodOrderItemses;
        }
       /* if (goodOrderItemses.size() == 0) {
            return null;
        }*/
        return null;
    }


    /**
     * 获取所有的订单
     */
    public LsResponse getGoodOrderAllList(String[] canIds, String keyword, String associatorId, String canteenId, Integer dateflag, Integer payType, String orderTime, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        int total = 0;
        try {
            log.info(associatorId + "正在查看所有的商品订单，餐厅编号为" + canteenId);
            total = goodOrderMapper.getCount(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, "GoodOrderAll");
            List<GoodOrder> goodOrders = goodOrderMapper.getList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, "GoodOrderAll", offset, pageSize);
            if (goodOrders != null && goodOrders.size() > 0) {
                for (GoodOrder goodOrder : goodOrders) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
            }
            if (goodOrders != null && goodOrders.size() > 0) {
                lsResponse.setTotalCount(total);
                lsResponse.setData(goodOrders);
                log.info("成功获取所有的订单");
                lsResponse.setMessage("成功获取所有的订单");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("无订单");
                log.info("获取所有的订单失败，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setData(new ArrayList<>());
//            lsResponse.setAsFailure();
//            lsResponse.setMessage("订单全部列表查询失败，异常信息为：" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("订单全部列表查询失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 提交订单，未付款的订单列表
     */
    public LsResponse getGoodOrderTmpList(String[] canIds, String keyword, String associatorId, String canteenId, Integer dateflag, Integer payType, String orderTime, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = null;
        try {
            log.info(associatorId + "正在查看未付款的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCount(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, T_GOOD_ORDER_TMP);
            List<GoodOrder> tmpList = goodOrderMapper.getList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, T_GOOD_ORDER_TMP, offset, pageSize);
            if (tmpList != null && tmpList.size() > 0) {
                for (GoodOrder goodOrder : tmpList) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setTotalCount(total);
                lsResponse.setData(tmpList);
                log.info("未付款的列表成功查询出来");
                lsResponse.setMessage("未付款的列表成功查询出来");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("无订单");
                log.info("未付款的列表查询失败，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //lsResponse.setAsFailure();
            lsResponse.setData(new ArrayList<>());
            lsResponse.setErrorCode("500");
            //lsResponse.setMessage("提交订单，未付款的订单列表查询失败，异常信息为：" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("提交订单，未付款的订单列表查询失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 获取已经支付的订单列表
     */
    public LsResponse getGoodOrderPaidList(String[] canIds, String keyword, String associatorId, String canteenId, Integer dateflag, Integer payType, String orderTime, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        try {
            log.info(associatorId + "正在查看已经支付的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCount(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, T_GOOD_ORDER_PAID);
            List<GoodOrder> paidList = goodOrderMapper.getList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, T_GOOD_ORDER_PAID, offset, pageSize);
            if (paidList != null && paidList.size() > 0) {
                for (GoodOrder goodOrder : paidList) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setData(paidList);
                lsResponse.setTotalCount(total);
                log.info("已付款订单列表查询成功");
                lsResponse.setMessage("已付款订单列表查询成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("无订单");
                log.info("已付款订单列表查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setData(new ArrayList<>());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("已付款订单列表查询失败,失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 获取已经完成的订单列表
     */
    public LsResponse getGoodOrderOverList(String[] canIds, String keyword, String associatorId, String canteenId, Integer dateflag, Integer payType, String orderTime, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        try {
            log.info(associatorId + "正在查看已经完成的的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCount(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, T_GOOD_ORDER_OVER);
            List<GoodOrder> overList = goodOrderMapper.getList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, T_GOOD_ORDER_OVER, offset, pageSize);
            if (overList != null && overList.size() > 0) {
                for (GoodOrder goodOrder : overList) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setData(overList);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("已完成订单列表查询成功");
                log.info("已完成订单列表查询成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("无订单");
                log.info("已完成订单列表查询失败，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setData(new ArrayList<>());
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("已完成订单列表查询失败,失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 取消订单的订单列表！已付款，取消&&未付款，取消订单
     */
    public LsResponse getGoodOrderRefundList(String[] canIds, String keyword, String associatorId, String canteenId, Integer dateflag, Integer payType, String orderTime, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        try {
            log.info(associatorId + "正在查看取消&&退款的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCount(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, "GoodOrderExit");
            List<GoodOrder> goodOrders = goodOrderMapper.getList(canIds, keyword, associatorId, canteenId, dateflag, payType, orderTime, "GoodOrderExit", offset, pageSize);
            if (goodOrders != null && goodOrders.size() > 0) {
                for (GoodOrder goodOrder : goodOrders) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setTotalCount(total);
                lsResponse.setData(goodOrders);
                lsResponse.setMessage("已取消/退款订单查询成功");
                log.info("已取消/退款订单查询成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("无订单");
                log.info("已取消/退款订单查询失败，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setData(new ArrayList<>());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("已取消/退款订单查询失败,失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    /*----------------------------------------------------------------以上是WEB端显示的订单列表部分---------------------------------------------*/

    /**
     * 关于商品订单的一些数量统计，这个时间还没有处理好（未用到）
     */
    @Override
    public LsResponse getGoodOrderStatistics(Date startTime, Date endTime, String canteenId) {
        LsResponse lsResponse = LsResponse.newInstance();
        return lsResponse;
    }

    /**
     * 传递订单编号，根据订单编号查找数据库表，判断是否提交半个小时未付款
     * 返回true表示还没有过半个小时的支付时间！
     * 返回false表示已过超过半个小时的支付时间！
     *
     * @param createTime:订单创建时间
     * @return
     */
    public Boolean goodPayDateVerify(Date createTime) {
        Boolean flag = true;
        Long time = Dates.timeInterval(createTime, Dates.now());
        if (time > 1800) {
            flag = false;
        }
        //GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(orderID,T_GOOD_ORDER_TMP);//根据商品订单的编号查询tmp表，得到订单数据
        /*String payDate = Dates.now("yyyyMMddhhmmss");//用户支付时间
        String submitDate = Dates.format(createTime, "yyyyMMddhhmmss");//用户生成该订单的时间
        if (Long.valueOf(payDate) - Long.valueOf(submitDate) > 1800) {//如果用户提交半个小时之后没有付款，直接取消该订单
            flag = false;
        }*/
        return flag;
    }

    /**
     * 传递订单编号，根据订单编号查找数据库表，判断商品是否已经过期
     * 返回true表示商品还没有过期~
     * 返回false表示商品已经过期
     *
     * @param orderID:订单编号
     * @param packingDate：取货日期
     * @return
     */
    public Boolean GoodPickingDateVerify(String orderID, Date packingDate) {
        Boolean flag = true;
        List<GoodOrderItems> goodOrderItemses = goodOrderItemsMapper.getGoodOrderItemsList(orderID);
        for (GoodOrderItems items : goodOrderItemses) {
            Good good = goodMapper.getGoodByGoodId(items.getGoodId());
            String goodEndTime = Dates.format(good.getEndTime(), "yyyyMMddHHmmss");
            String packingString = Dates.format(packingDate, "yyyyMMddHHmmss");
            /*Long.valueOf(Dates.now("yyyyMMddHHmmss")*/
            if (Long.valueOf(goodEndTime) - Long.valueOf(packingString) < 0) {//表示商品已经过期
                goodMapper.updateGoodIsDeleteById(good.getId());//设置该商品的状态为已过期
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 根据订单编号获取订单详情（为用到）
     */
    @Override
    public LsResponse getGoodOrderDetails(String goodOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        return lsResponse;
    }

    @Override
    public List<GoodOrder> selectGoodPaidAllList(Date date) {
        return goodOrderMapper.selectGoodPaidAllList(date);
    }

    /*---------------------------------------------------H5页面显示部分-------------------------------------------------------------------------------*/
    @Override
    public LsResponse getGoodOrderListH5Show(String associatorId, Integer flag, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer offset = null;
        try {
            if (pageNum != null && !("").equals(pageNum)) {
                offset = (pageNum - 1) * pageSize;
            }
            canteenId = StringsUtil.encodingChange(canteenId);
            log.info("H5" + flag + " " + associatorId + " " + "餐厅编号" + canteenId + "");
            switch (flag) {
                //case 0: lsResponse = getGoodOrderAllList(null, "", associatorId, canteenId, null, 0,"", offset, pageSize);break;
                case 1:
                    lsResponse = getGoodOrderTmpListH5(associatorId, canteenId, offset, pageSize);
                    break;
                case 2:
                    lsResponse = getGoodOrderPaidListH5(associatorId, canteenId, offset, pageSize);
                    break;
                case 3:
                    lsResponse = getGoodOrderOverCommentListH5(associatorId, canteenId, offset, pageSize);
                    break;
                case 4:
                    lsResponse = getGoodOrderRefundListH5(associatorId, canteenId, offset, pageSize);
                    break;
                case 5:
                    lsResponse = getGoodOrderCancelListH5(associatorId, canteenId, offset, pageSize);
                    break;
                case 6:
                    lsResponse = getGoodOrderOverListH5(associatorId, canteenId, offset, pageSize);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取商品订单出错", e.toString());
        }
        return lsResponse;
    }

    public LsResponse getGoodOrderTmpListH5(String associatorId, String canteenId, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        try {
            log.info(associatorId + "正在查看刚提交了的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCountH5(associatorId, canteenId, 1, T_GOOD_ORDER_TMP);
            List<GoodOrder> paidList = goodOrderMapper.getListH5(associatorId, canteenId, 1, T_GOOD_ORDER_TMP, offset, pageSize);
            if (paidList != null && paidList.size() > 0) {
                for (GoodOrder goodOrder : paidList) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setData(paidList);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("正在查看刚提交了的订单列表，成功");
                log.info("正在查看刚提交了的订单列表，成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("正在查看刚提交了的订单列表，总条数为0");
                log.info("正在查看刚提交了的订单列表，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setAsFailure();
            lsResponse.setData(new ArrayList<>());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
//            lsResponse.setErrorCode("500");
//            lsResponse.setMessage("正在查看刚提交了的订单列表,失败原因为：" + e.toString());
            log.error("正在查看刚提交了的订单列表失败,失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    public LsResponse getGoodOrderPaidListH5(String associatorId, String canteenId, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        try {
            log.info(associatorId + "正在查看已经付款的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCountH5(associatorId, canteenId, 2, T_GOOD_ORDER_PAID);
            List<GoodOrder> paidList = goodOrderMapper.getListH5(associatorId, canteenId, 2, T_GOOD_ORDER_PAID, offset, pageSize);
            if (paidList != null && paidList.size() > 0) {
                for (GoodOrder goodOrder : paidList) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setData(paidList);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("正在查看已经付款的订单列表，成功");
                log.info("正在查看已经付款的订单列表，成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("正在查看已经付款的订单列表，总条数为0");
                log.info("正在查看已经付款的订单列表，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setData(new ArrayList<>());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
//            lsResponse.setAsFailure();
//            lsResponse.setMessage("正在查看已经付款的订单列表失败,失败原因为：" + e.toString());
            log.error("正在查看已经付款的订单列表失败,失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    public LsResponse getGoodOrderOverCommentListH5(String associatorId, String canteenId, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        try {
            log.info(associatorId + "正在查看已经完成的未评论的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCountH5(associatorId, canteenId, 3, T_GOOD_ORDER_OVER);
            List<GoodOrder> paidList = goodOrderMapper.getListH5(associatorId, canteenId, 3, T_GOOD_ORDER_OVER, offset, pageSize);
            if (paidList != null && paidList.size() > 0) {
                for (GoodOrder goodOrder : paidList) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setData(paidList);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("正在查看已经完成的未评论的订单列表");
                log.info("正在查看已经完成的未评论的订单列表");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("正在查看已经完成的未评论的订单列表，总条数为0");
                log.info("正在查看已经完成的未评论的订单列表");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setData(new ArrayList<>());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
//            lsResponse.setAsFailure();
//            lsResponse.setMessage("正在查看已经完成的未评论的订单列表失败,失败原因为：" + e.toString());
            log.error("正在查看已经完成的未评论的订单列表失败,失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    public LsResponse getGoodOrderRefundListH5(String associatorId, String canteenId, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        try {
            log.info(associatorId + "正在查看已经退款的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCountH5(associatorId, canteenId, 4, T_GOOD_ORDER_REFUND);
            List<GoodOrder> paidList = goodOrderMapper.getListH5(associatorId, canteenId, 4, T_GOOD_ORDER_REFUND, offset, pageSize);
            if (paidList != null && paidList.size() > 0) {
                for (GoodOrder goodOrder : paidList) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setData(paidList);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("正在查看已经退款的订单列表，成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("正在查看已经退款的订单列表，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setData(new ArrayList<>());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
//            lsResponse.setAsFailure();
//            lsResponse.setMessage("正在查看已经退款的订单列表失败,失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    public LsResponse getGoodOrderCancelListH5(String associatorId, String canteenId, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        try {
            log.info(associatorId + "正在查看已经取消的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCountH5(associatorId, canteenId, 5, T_GOOD_ORDER_CANCEL);
            List<GoodOrder> paidList = goodOrderMapper.getListH5(associatorId, canteenId, 5, T_GOOD_ORDER_CANCEL, offset, pageSize);
            if (paidList != null && paidList.size() > 0) {
                for (GoodOrder goodOrder : paidList) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setData(paidList);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("正在查看已经取消的订单列表，成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("正在查看已经取消的订单列表，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setData(new ArrayList<>());
//            lsResponse.setAsFailure();
//            lsResponse.setMessage("正在查看已经取消的订单列表失败,失败原因为：" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    public LsResponse getGoodOrderOverListH5(String associatorId, String canteenId, Integer offset, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        try {
            log.info(associatorId + "正在查看已经完成的并且是已经评论的订单列表，餐厅编号为：" + canteenId);
            total = goodOrderMapper.getCountH5(associatorId, canteenId, 6, T_GOOD_ORDER_OVER);
            List<GoodOrder> paidList = goodOrderMapper.getListH5(associatorId, canteenId, 6, T_GOOD_ORDER_OVER, offset, pageSize);
            if (paidList != null && paidList.size() > 0) {
                for (GoodOrder goodOrder : paidList) {
                    goodOrder.setGoodOrderItemses(getGoodOrderItems(goodOrder.getOrderId()));
                }
                lsResponse.setData(paidList);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("正在查看已经完成的并且是已经评论的订单列表，成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage("正在查看已经完成的并且是已经评论的订单列表，总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //lsResponse.setAsFailure();
            lsResponse.setErrorCode("500");
            lsResponse.setData(new ArrayList<>());
            //lsResponse.setMessage("正在查看已经完成的并且是已经评论的订单列表失败,失败原因为：" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    /**
     * 根据订单编号获取订单详情
     */
    @Override
    public LsResponse getGoodOrderDetailsH5(Integer status, String goodOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            goodOrderId = new String(goodOrderId.getBytes("iso8859-1"), "utf-8");
            if (goodOrderId != null) {
                log.info("正在查看订单详情，订单编号为：" + goodOrderId);
            }
            GoodOrder goodOrder = null;
            switch (status) {
                case 1:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_TMP);
                    break;
                case 2:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_PAID);
                    break;
                case 3:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_OVER);
                    break;
                case 4:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_REFUND);
                    break;
                case 5:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_CANCEL);
                    break;
                case 6:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_OVER);
                    break;
                default:
                    break;
            }
            if (goodOrder != null) {
                switch (goodOrder.getStatus()) {
                    case 1:
                        goodOrder.setStatusName("等待会员付款");
                        break;
                    case 2:
                        goodOrder.setStatusName("等待会员取货");
                        break;
                    case 3:
                        goodOrder.setStatusName("交易完成");
                        break;
                    case 6:
                        goodOrder.setStatusName("交易完成");
                        break;
                    case 4:
                        goodOrder.setStatusName("已退款");
                        break;
                    case 5:
                        goodOrder.setStatusName("已取消");
                        break;
                    default:
                        goodOrder.setStatusName("");
                        break;
                }
                goodOrder.setCreateTimeString(Dates.format(goodOrder.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                List<GoodOrderItems> orderItemses = goodOrderItemsMapper.getGoodOrderItemsList(goodOrderId);
                if (orderItemses != null && orderItemses.size() > 0) {
                    goodOrder.setGoodOrderItemses(orderItemses);
                }
                lsResponse.setData(goodOrder);
                log.info("订单详情查询成功,查看的是编号为" + goodOrderId + "的订单");
                lsResponse.setMessage("订单详情查询成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("订单号有误");
                log.info("正在查看订单详情，订单编号为：" + goodOrderId + "数据库没有查找到信息，status为" + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
//            lsResponse.setAsFailure();
//            lsResponse.setMessage("查看订单详情出现错误，错误信息为：" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("查看订单详情出现错误，订单编号为：" + goodOrderId + ",status = " + status + "错误信息为：" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 根据订单编号打开商品评价页面
     */
    @Override
    public LsResponse openGoodCommentH5(String goodOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            goodOrderId = new String(goodOrderId.getBytes("iso8859-1"), "utf-8");
            log.info("订单编号为" + goodOrderId + "的，正在准备评论商品");
            GoodOrder goodOrder = goodOrderMapper.getGoodOrder(goodOrderId, T_GOOD_ORDER_OVER);
            if (goodOrder != null) {
                List<GoodOrderItems> items = goodOrderItemsMapper.getGoodOrderItemsList(goodOrderId);
                if (items != null && items.size() > 0) {
                    goodOrder.setGoodOrderItems(items);
                    log.info("订单编号为" + goodOrderId + "的，查看需要评论的商品成功，共" + items.size() + "个商品");
                    lsResponse.setMessage("这是需要评价的商品");
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("该订单没有商品");
                    log.error("该订单没有商品");
                }
                lsResponse.setData(goodOrder);
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("订单编号错误，该订单不存在");
                log.error("订单编号错误，该订单不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
//            lsResponse.setAsFailure();
//            lsResponse.setMessage("打开商品评价页面时，失败，异常信息为" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("打开商品评价页面时，失败，异常信息为" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 立即付款（H5端）
     */
    @Override
    public LsResponse paidNowH5(String goodOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        Boolean flag = false;
        try {
            if (goodOrderId != null) {
                log.info("service层立即付款的方法正在执行，查询的商品订单编号为:" + goodOrderId);
            }
            GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_TMP);//根据商品订单的编号查询tmp表，得到订单数据
            if (goodOrder != null) {
                flag = goodPayDateVerify(goodOrder.getCreateTime()); //首先判断商品是否已过30分钟付款时间
                if (flag) {//true,表示还没到30分钟，true表示还没到30分钟的付款时间
                    flag = GoodPickingDateVerify(goodOrderId, goodOrder.getPickingTime());//再次判断商品的取货时间是否已经过期
                    if (flag) {//表示商品还没有过期，可以申请取消
                        List<GoodOrderItems> orderItems = goodOrderItemsMapper.getGoodOrderItemsList(goodOrderId);
                        if (orderItems != null && orderItems.size() > 0) {
                            goodOrder.setGoodOrderItems(orderItems);
                        }
                        lsResponse.setData(goodOrder);
                    } else {//表示商品已经过期，该订单已经自动取消
                        goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(goodOrder.getOrderId(), T_GOOD_ORDER_TMP);//根据订单的主键id删除tmp表中的数据；这是真删
                        goodOrder.setStatus((short) 5);
                        goodOrder.setUpdateTime(Dates.now());
                        goodOrderMapper.insertGoodOrder(goodOrder, T_GOOD_ORDER_CANCEL);//往订单取消表中添加一条数据
                        lsResponse.setAsFailure();
                        lsResponse.setMessage("该商品已过最晚提供日期，该订单已经自动取消");
                        log.error("该商品已过最晚提供日期，该订单已经自动取消");
                    }
                } else {//表示已过45分钟付款时间
                    goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(goodOrder.getOrderId(), T_GOOD_ORDER_TMP);//根据订单的主键id删除tmp表中的数据；这是真删
                    goodOrder.setStatus((short) 5);
                    goodOrder.setUpdateTime(Dates.now());
                    goodOrderMapper.insertGoodOrder(goodOrder, T_GOOD_ORDER_CANCEL);//在cancel表中插入该数据
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("您半小时内未付款，该订单已取消");
                    log.error("您45分钟内未付款，该订单已经自动取消");
                }
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("该订单不存在，请仔细查看该订单");
                log.error("该订单不存在，请仔细查看该订单,确认订单编号是否正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
//            lsResponse.setAsFailure();
//            lsResponse.setMessage("order paid failed,happen exception"+e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("service层立即付款的方法在执行的过程中发生异常，异常信息为:" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 申请退款（H5端）
     */
    @Override
    public LsResponse applyRefundGorRH5(String orderId, String deadLine) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (orderId != null && !("").equals(orderId)) {
                orderId = new String(orderId.getBytes("iso8859-1"), "utf-8");
            }
            if (deadLine == "") {
                deadLine = "2200";
                log.info("订单截止日期为空字符串，设置默认为  晚上十点");
            } else {
                log.info("该餐厅的订单截止日期为：" + deadLine);
            }
            Map<String, Object> map = new HashMap<>();
            String flag = orderId.substring(orderId.length() - 1, orderId.length());//判断是商品订单还是菜品订单
            if (flag.equals("1")) {
                GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(orderId, T_GOOD_ORDER_PAID);//根据订单的编号查询paid表，得到订单数据
                if (goodOrder != null) {
                    Date beforePickDate = Dates.getBeforeDate(goodOrder.getPickingTime(), 1);//得到取货时间,得到取件日期的前一天
                    String nowTime = Dates.now("yyyyMMddHHmm");//得到当前时间
                    String deadLineTime = new SimpleDateFormat("yyyyMMdd" + deadLine).format(beforePickDate);
                    //String nowtime = new SimpleDateFormat("yyyyMMdd173001").format(beforePickDate);//五点半之后，随机设定，为测试！
                    if (Long.valueOf(nowTime) <= Long.valueOf(deadLineTime)) {//当前日期在取件日期前一天的五点半之前，可以申请退款（该截止时间为餐厅设置的）
                        List<GoodOrderItems> orderItems = goodOrderItemsMapper.getGoodOrderItemsList(orderId);
                        if (orderItems != null && orderItems.size() > 0) {
                            map.put("payType", goodOrder.getPayType());
                            map.put("orderId", goodOrder.getOrderId());
                            map.put("orderAmount", goodOrder.getOrderAmount());
                            List<Map<Object, Object>> list = new ArrayList<>();
                            for (GoodOrderItems items : orderItems) {
                                Map<Object, Object> goodMap = new HashMap<>();
                                goodMap.put("name", items.getGood().getGoodName() + "【" + items.getGood().getStandard() + "】");
                                goodMap.put("price", items.getGood().getPrice());
                                goodMap.put("imageurl", items.getGood().getImageurl());
                                goodMap.put("quantity", items.getQuantity());
                                list.add(goodMap);
                            }
                            map.put("items", list);
                            lsResponse.setMessage("订单信息查询成功");
                            lsResponse.setData(map);
                        }
                        log.info("亲，申请退款,商品订单的数据查询成功");
                    } else {
                        lsResponse.setAsFailure();//当前日期在取件日期前一天的五点半之后，不可以申请退款
                        lsResponse.setMessage("亲，您已超过商家设定最晚申请退款时间，请您按时取您的宝贝");
                        log.error("亲，您已超过商家设定最晚申请退款时间，请您按时取您的宝贝");
                    }
                }
            } else if (flag.equals("2")) {
                RecipeOrder recipeOrder = recipeOrderMapper.getOrderInPaidByOrderId(orderId);
                if (recipeOrder != null) {
                    Date beforePickDate = Dates.getBeforeDate(recipeOrder.getEatTime(), 1);//得到就餐时间,得到就餐日期的前一天
                    String nowTime = Dates.now("yyyyMMddHHmm");//得到当前时间
                    String deadLineTime = new SimpleDateFormat("yyyyMMdd" + deadLine).format(beforePickDate);
                    if (Long.valueOf(nowTime) < Long.valueOf(deadLineTime)) {//当前日期在就餐日期前一天的五点半之前，可以申请退款（该截止时间为餐厅设置的）
                        List<RecipeOrderItems> orderItems = recipeOrderItemsMapper.getRecipeOrderItemsList(orderId);
                        if (orderItems != null && orderItems.size() > 0) {
                            map.put("payType", recipeOrder.getPayType());
                            map.put("orderId", recipeOrder.getOrderId());
                            map.put("orderAmount", recipeOrder.getOrderAmount());
                            List<Map<Object, Object>> list = new ArrayList<>();
                            for (RecipeOrderItems items : orderItems) {
                                Map<Object, Object> recipeMap = new HashMap<>();
                                recipeMap.put("name", items.getRecipe().getRecipeName());
                                recipeMap.put("price", items.getRecipe().getGuidePrice());
                                recipeMap.put("imageurl", items.getRecipe().getImageurl());
                                recipeMap.put("quantity", items.getQuantity());
                                list.add(recipeMap);
                            }
                            map.put("items", list);
                            lsResponse.setMessage("订单信息查询成功");
                            lsResponse.setData(map);
                        }
                        log.info("亲，申请退款,菜品订单的数据查询成功");
                    } else {
                        lsResponse.setAsFailure();//今天不可以退款今天以及之前的菜
                        lsResponse.setMessage("亲，您已超过商家设定最晚申请退款时间，请您按时就餐");
                        log.error("亲，您已超过商家设定最晚申请退款时间，请您按时就餐");
                    }
                }
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("亲，您好，你输入的订单号有误");
                log.error("亲，您好，你输入的订单号有误,订单尾号既不是1，也不是2，让我很迷");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
//            lsResponse.setAsFailure();
//            lsResponse.setMessage("exception happened！！！");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("申请退款，获取的信息数据时发生异常，异常信息为" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 确认收货,商品订单确认收货 （暂时没有用到）
     */
    @Override
    public LsResponse confirmGoodOrderH5(String goodOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        return lsResponse;
    }

    /**
     * 取消订单，无条件判断（H5）
     */
    public LsResponse cancelGoodOrderH5(String goodOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (goodOrderId != null && !("").equals(goodOrderId)) {
                GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_TMP);//根据订单的编号查询tmp表，得到订单数据
                if (goodOrder != null) {
                    log.info("取消商品订单，该商品订单的信息为：" + goodOrder);
                    goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(goodOrder.getOrderId(), T_GOOD_ORDER_TMP);//根据订单的主键id删除tmp表中的数据；是真的删
                    goodOrder.setPayType((short) 0);//这个本应该是不需要的,0表示未支付
                    goodOrder.setStatus((short) 5);
                    goodOrder.setUpdateTime(Dates.now());//设置更新时间
                    goodOrderMapper.insertGoodOrder(goodOrder, T_GOOD_ORDER_CANCEL);//在cancel表中插入该数据
                    log.info("取消订单成功");
                    String picking = Dates.format(goodOrder.getPickingTime(), "yyyyMMdd");
                    //String now = Dates.now("yyyyMMdd");
                    String pickingTime = Dates.format(goodOrder.getPickingTime(), "yyyy-MM-dd");
                    log.info("picking" + picking);
                    List<GoodOrderItems> orderItems = goodOrderItemsMapper.getGoodOrderItemsList(goodOrderId);//根据订单编号查询订单详情
                    for (GoodOrderItems items : orderItems) {
                        Integer quantity = items.getQuantity();
                        log.info("订单中该商品的数量为:" + quantity);
                        goodPlanItemsMapper.updateGoodPlanSurPlus(items.getPlanId(), items.getGoodId(), quantity);
                    }
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("订单取消失败");
                    log.info("订单取消失败");
                }
            } else {
                lsResponse.setMessage("订单号有误");
                lsResponse.setAsFailure();
                log.info("订单号有误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("取消订单时发生异常，exception happened！！！" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 取消订单（H5端）
     */
    public LsResponse cancelGoodOrderH5Condition(String goodOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        Boolean flag;
        try {
            GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_TMP);//根据订单的编号查询tmp表，得到订单数据
            if (goodOrder != null) {
                log.info("取消商品订单，该商品订单的信息为：" + goodOrder);
                //首先判断商品是否已过45分钟付款时间
                flag = goodPayDateVerify(goodOrder.getCreateTime());
                if (flag) {//表示还没到30分钟，true表示还没到30分钟的付款时间
                    flag = GoodPickingDateVerify(goodOrderId, goodOrder.getPickingTime());
                    goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(goodOrder.getOrderId(), T_GOOD_ORDER_TMP);//根据订单的主键id删除tmp表中的数据；是真的删
                    goodOrder.setPayType((short) 0);//这个本应该是不需要的,0表示未支付
                    goodOrder.setStatus((short) 5);
                    goodOrder.setUpdateTime(Dates.now());//设置更新时间
                    goodOrderMapper.insertGoodOrder(goodOrder, T_GOOD_ORDER_CANCEL);//在cancel表中插入该数据
                    if (flag) {//表示商品还没有过期，可以申请取消
                        lsResponse.setMessage("成功取消订单，欢迎再次使用！");
                    } else {//表示商品已经过期，该订单已经自动取消
                        lsResponse.setAsFailure();
                        lsResponse.setMessage("该商品已过期，订单已自动取消");
                        log.info("取消订单失败");
                    }
//                    goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(goodOrder.getOrderId(), T_GOOD_ORDER_TMP);//根据订单的主键id删除tmp表中的数据；是真的删
//                    goodOrder.setPayType((short) 0);//这个本应该是不需要的,0表示未支付
//                    goodOrder.setStatus((short) 5);
//                    goodOrder.setUpdateTime(Dates.now());//设置更新时间
//                    goodOrderMapper.insertGoodOrder(goodOrder, T_GOOD_ORDER_CANCEL);//往订单取消表中添加一条数据
                    log.info("取消订单成功");
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("您45分钟内未付款，该订单已经自动取消");
                    log.info("您45分钟内未付款，该订单已经自动取消");
                }
                String picking = Dates.format(goodOrder.getPickingTime(), "yyyyMMdd");
                String now = Dates.now("yyyyMMdd");
                String pickingTime = Dates.format(goodOrder.getPickingTime(), "yyyy-MM-dd");
//                long lg = Dates.timeInterval(goodOrder.getPickingTime(),Dates.now());
//                long.info("lglglglglglglglg==========="+lg);
                if (Long.valueOf(picking) == Long.valueOf(now)) {
                    log.info("true");
                    List<GoodOrderItems> orderItems = goodOrderItemsMapper.getGoodOrderItemsList(goodOrderId);
                    for (GoodOrderItems items : orderItems) {
                        GoodPlan goodPlan = goodPlanMapper.getGoodPlanContentByCanAndTime(goodOrder.getCanteenId(), pickingTime);
                        if (goodPlan != null) {
                            List<GoodPlanItems> goodPlanItems = goodPlanItemsMapper.getGoodPlanDetailsListByGoodPlanId(goodPlan.getGoodPlanId());
                            for (GoodPlanItems planItems : goodPlanItems) {
                                if (items.getGoodId().equals(planItems.getGoodId())) {
                                    Integer quantity = planItems.getSurplus() + items.getQuantity();
                                    log.info("相加之后的数量为：" + quantity);
                                    goodPlanItemsMapper.updateGoodPlanSurPlus(planItems.getGoodPlanId(), items.getGoodId(), quantity);
                                }
                            }
                        }
                    }
                }
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("订单取消失败，请仔细查看");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            //lsResponse.setAsFailure();
            lsResponse.setErrorCode("500");
            //lsResponse.setMessage("exception happened！！！" + e.toString());
            log.error("exception happened！！！" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 根据商品订单编号删除商品订单
     */
    @Override
    public LsResponse deleteGoodOrderH5(Integer status, String goodOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            log.info("正在删除商品订单编号为" + goodOrderId + ",status = " + status);
            int flag = 0;
            switch (status) {
                case 3:
                    flag = goodOrderMapper.deleteGoodOrderByGoodOrderId(goodOrderId, -3, T_GOOD_ORDER_OVER);
                    break;
                case 4:
                    flag = goodOrderMapper.deleteGoodOrderByGoodOrderId(goodOrderId, -4, T_GOOD_ORDER_REFUND);
                    break;
                case 5:
                    flag = goodOrderMapper.deleteGoodOrderByGoodOrderId(goodOrderId, -5, T_GOOD_ORDER_CANCEL);
                    break;
                case 6:
                    flag = goodOrderMapper.deleteGoodOrderByGoodOrderId(goodOrderId, -6, T_GOOD_ORDER_OVER);
                    break;
                default:
                    break;
            }
            if (flag > 0) {
               /* int itemsflag = goodOrderItemsMapper.deleteGoodOrderItems(goodOrderId);
                if (itemsflag > 0) {
                    lsResponse.setMessage("商品订单和商品订单详情删除成功");
                    log.info("商品订单和商品订单详情删除成功,订单编号为" + goodOrderId);
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("商品订单删除成功，但是商品订单想请删除失败");
                    log.info("商品订单删除成功，但是商品订单想请删除失败");
                }*/
                lsResponse.setMessage("商品订单删除成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("商品订单删除失败");
                log.info("商品订单删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setAsFailure();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("根据商品订单编号删除商品订单，删除失败,商品订单为" + goodOrderId + ",异常信息为" + e.toString());
        }
        return lsResponse;
    }

    /*------------------------------------------------------小程序端需要用到的方法---------------------------------------------------------*/
    /*小程序中的订单列表和web端商品订单列表用的是同一个mapper层的方法*/
    @Override
    public LsResponse getGoodOrderListWxShow(String[] canteenIds, Integer flag, String canteenId, String orderTime, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer offset = null;
        try {
            if (pageNum != null && !("").equals(pageNum)) {
                offset = (pageNum - 1) * pageSize;
            }
            canteenId = StringsUtil.encodingChange(canteenId);
            if (orderTime == "" || orderTime == null) {
                orderTime = Dates.now("yyyy-MM-dd");
            }
            switch (flag) {
                case 0:
                    lsResponse = getGoodOrderAllList(canteenIds, "", "", canteenId, null, null, orderTime, offset, pageSize);
                    break;
                case 1:
                    lsResponse = getGoodOrderTmpList(canteenIds, "", "", canteenId, null, null, orderTime, offset, pageSize);
                    break;
                case 2:
                    lsResponse = getGoodOrderPaidList(canteenIds, "", "", canteenId, null, null, orderTime, offset, pageSize);
                    break;
                case 3:
                    lsResponse = getGoodOrderOverList(canteenIds, "", "", canteenId, null, null, orderTime, offset, pageSize);
                    break;
                case 4:
                    lsResponse = getGoodOrderRefundList(canteenIds, "", "", canteenId, null, null, orderTime, offset, pageSize);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setAsFailure();
            lsResponse.setErrorCode("500");
            //lsResponse.setMessage(e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("获取商品订单出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse confirmOrderWx(String orderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            orderId=StringsUtil.encodingChange(orderId);
            String flag = orderId.substring(orderId.length() - 1, orderId.length());
            if (flag.equals("1")) {
                GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(orderId, T_GOOD_ORDER_PAID);//根据商品订单的编号查询tmp表，得到订单数据
                if (goodOrder == null) {
                    log.info("确认收货失败，原因是：该订单不存在");
                    lsResponse.setAsFailure();
                    return lsResponse.setMessage("确认收货失败，原因是：该订单不存在");
                }
                goodOrderMapper.deleteGoodOrderByGoodOrderIdTrue(orderId, T_GOOD_ORDER_PAID);//删除paid表中的数据
                goodOrder.setUpdateTime(Dates.now());//设置更新时间，设置的是over表中的数据
                goodOrder.setStatus((short) 3);
                int insflag = goodOrderMapper.insertGoodOrder(goodOrder, T_GOOD_ORDER_OVER);
                if (insflag > 0) {
                    log.info("订单编号为：" + orderId + "的商品订单，确认收货成功");
                    lsResponse.setMessage("确认收货成功");
                }
            } else if (flag.equals("2")) {
                RecipeOrder recipeOrder = recipeOrderMapper.getOrderInPaidByOrderId(orderId);
                if (recipeOrder == null) {
                    log.info("确认收货失败，原因是：该订单不存在");
                    lsResponse.setAsFailure();
                    return lsResponse.setMessage("确认收货失败，原因是：该订单不存在");
                }
                recipeOrderMapper.deleteOrderInPaidByOrderIdTrue(orderId);//删除paid表中的数据
                recipeOrder.setUpdateTime(Dates.now());//设置更新时间，设置的是over表中的数据
                recipeOrder.setStatus((short) 3);
                int insflag = recipeOrderMapper.insertOrderInOver(recipeOrder);
                if (insflag > 0) {
                    log.info("订单编号为：" + orderId + "的菜品订单，确认收货成功");
                    lsResponse.setMessage("确认收货成功");
                }
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("请传递正确的订单编号");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("确认收货失败，订单编号为：" + orderId + ",异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse confirmOrderInfoWx(String orderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            orderId=StringsUtil.encodingChange(orderId);
            String flag = orderId.substring(orderId.length() - 1, orderId.length());
            if (flag.equals("1")) {
                log.info("商品订单正在确认收货");
                GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(orderId, T_GOOD_ORDER_PAID);//根据商品订单的编号查询tmp表，得到订单数据
                if (goodOrder == null) {
                    log.info("扫码失败，原因是：该订单不存在");
                    return lsResponse.setMessage("扫码失败，原因是：该订单不存在");
                }
                GoodOrder g = goodOrderMapper.getGoodOrderByGoodOrderId(orderId, T_GOOD_ORDER_OVER);
                if (g != null) {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("该订单已经确认收货完成");
                    log.error("该订单已经确认收货完成");
                }
                List<GoodOrderItems> orderItems = goodOrderItemsMapper.getGoodOrderItemsList(orderId);
                if (orderItems != null && orderItems.size() > 0) {
                    for (GoodOrderItems items : orderItems) {
                        items.getGood().setPrice(items.getOrderAmount());
                    }
                    goodOrder.setGoodOrderItemses(orderItems);
                }
                lsResponse.setMessage("订单信息查询成功");
                lsResponse.setData(goodOrder);
            } else if (flag.equals("2")) {
                log.info("菜品订单正在确认收货");
                RecipeOrder recipeOrder = recipeOrderMapper.getOrderInPaidByOrderId(orderId);
                if (recipeOrder == null) {
                    log.info("扫码失败，原因是：该订单不存在");
                    return lsResponse.setMessage("扫码失败，原因是：该订单不存在");
                }
                RecipeOrder r = recipeOrderMapper.getOrderInOverByOrderId(orderId);
                if (r != null) {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("该订单正在确认收货");
                    log.error("该订单正在确认收货");
                }
                List<RecipeOrderItems> orderItems = recipeOrderItemsMapper.getRecipeOrderItemsList(orderId);
                if (orderItems != null && orderItems.size() > 0) {
                    for (RecipeOrderItems items : orderItems) {
                        items.getRecipe().setGuidePrice(items.getOrderAmount());
                    }
                    recipeOrder.setRecipeOrderItemsList(orderItems);
                }
                lsResponse.setMessage("订单信息查询成功");
                lsResponse.setData(recipeOrder);
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("请传递正确的订单编号");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("扫码失败，订单编号为：" + orderId + ",异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getGoodOrderDetailsWx(Integer status, String goodOrderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            goodOrderId=StringsUtil.encodingChange(goodOrderId);
            GoodOrder goodOrder = null;
            switch (status) {
                case 1:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_TMP);
                    break;
                case 2:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_PAID);
                    break;
                case 3:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_OVER);
                    break;
                case 6:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_OVER);
                    break;
                case 4:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_REFUND);
                    break;
                case 5:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_CANCEL);
                    break;
                case -3:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_OVER);
                    break;
                case -6:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_OVER);
                    break;
                case -4:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_REFUND);
                    break;
                case -5:
                    goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(goodOrderId, T_GOOD_ORDER_CANCEL);
                    break;
                default:
                    break;
            }
            if (goodOrder != null) {
                switch (goodOrder.getStatus()) {
                    case 1:
                        goodOrder.setStatusName("等待会员付款");
                        break;
                    case 2:
                        goodOrder.setStatusName("等待会员取货");
                        break;
                    case 3:
                        goodOrder.setStatusName("交易已经完成");
                        break;
                    case 6:
                        goodOrder.setStatusName("交易已经完成");
                        break;
                    case 4:
                        goodOrder.setStatusName("已退款");
                        break;
                    case 5:
                        goodOrder.setStatusName("已取消");
                        break;
                    case -3:
                        goodOrder.setStatusName("交易完成");
                        break;
                    case -4:
                        goodOrder.setStatusName("已退款（用户已删除）");
                        break;
                    case -5:
                        goodOrder.setStatusName("已取消（用户已删除）");
                        break;
                    case -6:
                        goodOrder.setStatusName("交易完成");
                        break;
                    default:
                        goodOrder.setStatusName("");
                        break;
                }
                goodOrder.setCreateTimeString(Dates.format(goodOrder.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                List<GoodOrderItems> orderItemses = goodOrderItemsMapper.getGoodOrderItemsList(goodOrderId);
                if (orderItemses != null && orderItemses.size() > 0) {
                    goodOrder.setGoodOrderItemses(orderItemses);
                }
                lsResponse.setData(goodOrder);
                log.info("订单详情查询成功,查看的是编号为" + goodOrderId + "的订单");
                lsResponse.setMessage("订单详情查询成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("订单号有误");
                log.info("正在查看订单详情，订单编号为：" + goodOrderId + "数据库没有查找到信息，status为" + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("查看订单详情出现错误，订单编号为：" + goodOrderId + ",status = " + status + "错误信息为：" + e.toString());
        }
        return lsResponse;
    }

    /*-------------------------------------------------------their   write-------------------------------------------------------------------------------*/
    @Override
    public LsResponse getGoodOrderByGoodOrderId(String orderNo, String table) {
        LsResponse lsResponse = new LsResponse();
        try {
            GoodOrder goodOrder = goodOrderMapper.getGoodOrderByGoodOrderId(orderNo, table);
            lsResponse.setData(goodOrder);
        } catch (Exception e) {
            log.info("商品订单获取失败={}", e.getMessage());
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    @Override
    public LsResponse insertGoodOrder(GoodOrder goodOrder, String table) {
        LsResponse lsResponse = new LsResponse();
        try {
            int i = goodOrderMapper.insertGoodOrder(goodOrder, table);
            lsResponse.setData(i);
        } catch (Exception e) {
            log.info("商品订单添加失败={}", e.getMessage());
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

    /*未用到*/
    @Override
    public LsResponse deleteGoodOrderByGoodOrderId(String orderNo, String table) {
        LsResponse lsResponse = new LsResponse();
       /* try {
            int i = goodOrderMapper.deleteGoodOrderByGoodOrderId(orderNo, table);
            lsResponse.setData(i);
        } catch (Exception e) {
            log.info("商品订单删除失败={}", e.getMessage());
            lsResponse.setAsFailure();
        }*/
        return lsResponse;
    }

    @Override
    public Integer updateStatus(String orderId) {
        return goodOrderMapper.updateStatus(orderId);
    }
}
