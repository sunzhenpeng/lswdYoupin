package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.minghua.Declare;
import com.lswd.youpin.minghua.MWUtils;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.model.vo.TotalCountMoney;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterOrderService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.CountPrientBean;
import com.lswd.youpin.utils.PrientUtils;
import com.lswd.youpin.utils.SerializeUtils;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/12/07.
 */
@Service
public class CounterOrderServiceImpl implements CounterOrderService {
    private final Logger logger = LoggerFactory.getLogger(CounterServiceImpl.class);

    @Autowired
    private CounterOrderMapper counterOrderMapper;
    @Autowired
    private CounterOrderItemsMapper counterOrderItemsMapper;
    @Autowired
    private MembersMapper membersMapper;
    @Autowired
    private CounterMapper counterMapper;
    @Autowired
    private MemberTypeMapper memberTypeMapper;
    @Autowired
    private MemberCardMapper memberCardMapper;
    @Autowired
    private RedisManager redisManager;

    @Override
    public LsResponse generateCounterOrder(CounterOrder counterOrder, CounterUser counterUser) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (counterOrder == null) {
            return lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
        }
        if (counterOrder.getCounterMenus() == null || counterOrder.getCounterMenus().size() == 0) {
            return lsResponse.checkSuccess(false, "请先选择菜品信息");
        }
        if (counterOrder.getNumberTable() == null) {
            return lsResponse.checkSuccess(false, "请填写牌号");
        }
        if (counterOrder.getNumberPeople() == null) {
            return lsResponse.checkSuccess(false, "请填写就餐人数");
        }
        String orderId = "BT" + Dates.now("yyyyMMddHHmmss");//设置订单编号
        counterOrder.setOrderId(orderId);
        if (counterOrder.getPayType() != 0) {
            counterOrder.setBalance(0F);//设置余额
            counterOrder.setDiscountAmount(0F);//设置折扣金额
            counterOrder.setReceivedAmount(counterOrder.getReceivableAmount());//设置实收金额
        }else {
            Members members = membersMapper.getBalanceByMemberId(counterOrder.getMemberId());
            if (members == null){
                return lsResponse.checkSuccess(false,"订单生成失败，请联系管理员");
            }
            counterOrder.setBalance(members.getBalance() - counterOrder.getReceivedAmount());//设置订单余额为     目前的余额-订单的实收金额
        }
        if (counterOrder.getOrderName().equals("堂食")){
            counterOrder.setOrderName("吧台堂食");
        }
        counterOrder.setPayTime(Dates.now());//设置订单付款时间
        counterOrder.setCreateTime(Dates.now());//设置订单付款时间
        counterOrder.setStatus((short) 0);//设置订单状态
        if (counterUser != null) {
            counterOrder.setCounterUser(counterUser.getUsername());//设置吧台操作员
        }
        Integer itemFlag = 0;
        try {
            Integer orderFlag = counterOrderMapper.insertSelective(counterOrder);
            if (orderFlag > 0) {
                for (CounterMenu menu : counterOrder.getCounterMenus()) {
                    CounterOrderItems items = new CounterOrderItems();
                    items.setOrderId(orderId);
                    items.setMenuId(menu.getMenuId());
                    items.setMenuName(menu.getMenuName());
                    items.setMenuVipPrice(menu.getVipPrice());
                    items.setMenuMarketPrice(menu.getMarketPrice());
                    items.setMenuTypeName(menu.getMenuTypeName());
                    items.setQuantity(menu.getNumber());
                    if (counterOrder.getPayType() != 0) {
                        items.setOrderAmount(menu.getNumber() * menu.getMarketPrice());
                    } else {
                        items.setOrderAmount(menu.getNumber() * menu.getVipPrice());
                    }
                    items.setDescription(counterOrder.getMemberName() + "正在购买" + menu.getMenuName() + "，一共消费" + items.getOrderAmount());
                    itemFlag = counterOrderItemsMapper.insertSelective(items);
                }
                if (itemFlag > 0) {
                    if (counterOrder.getPayType() == 0) {
                        itemFlag = memberCardMapper.subCardBalance(counterOrder.getMemberId(), counterOrder.getReceivedAmount());
                        if (itemFlag > 0) {
                            lsResponse.setMessage("消费成功，欢迎下次使用");

/*--------------------------------------------------------------打印消费小票，start-------------------------------------------------------------------------*/
                            PrinterJob job = PrientUtils.getPrinterJob();
                            List<CountPrientBean> list = new ArrayList<>();
                            for (CounterMenu menu : counterOrder.getCounterMenus()) {
                                CountPrientBean bean = new CountPrientBean();
                                bean.setRecipeName(menu.getMenuName());
                                bean.setCount(menu.getNumber());
                                if (counterOrder.getPayType() != 0) {
                                    bean.setMoney(menu.getVipPrice());
                                } else {
                                    bean.setMoney(menu.getMarketPrice());
                                }
                                bean.setTotalMoney(counterOrder.getReceivedAmount());
                                list.add(bean);
                            }
                            redisManager.set("PrientFlag".getBytes(), "1".getBytes());
                            redisManager.set("CountPrientBean".getBytes(), SerializeUtils.serialize(list));
                            try {
                                if (PrientUtils.isPrint() == null){
                                    return lsResponse.checkSuccess(true,"消费成功，但是无法找到打印机，打印失败");
                                }
                                job.setPrintService(PrientUtils.isPrint()[0]);//根据打印机名字获取打印机
                                job.print();
                            } catch (PrinterException e) {
                                e.printStackTrace();
                                lsResponse.checkSuccess(true,"消费成功，打印出现问题，请联系管理员");
                            }
/*--------------------------------------------------------------打印消费小票，  end-------------------------------------------------------------------------*/

                        } else {
                            lsResponse.checkSuccess(false, "扣款失败，请联系管理员尽快处理1");
                        }
                    }
                    lsResponse.setMessage("消费成功，欢迎下次使用");
                } else {
                    lsResponse.checkSuccess(false, "吧台订单生成失败，请联系管理员尽快处理2");
                }
            } else {
                lsResponse.checkSuccess(false, "吧台订单生成失败，请联系管理员尽快处理3");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            lsResponse.checkSuccess(false, "吧台订单生成失败，请联系管理员尽快处理4");
        }
        return lsResponse;
    }

    @Override
    public synchronized LsResponse readCardUid(Float receivedAmount) {
        LsResponse lsResponse = LsResponse.newInstance();
        Declare.mwrf epen = null;
        short i = 1;
        String cardUid = "";
        try {
            epen = MWUtils.getDLLConn();
            if (epen == null) {
                return lsResponse.checkSuccess(false, "读卡失败");
            }
            i = MWUtils.DevConnectShort(epen);
            if (i == 1) {
                return lsResponse.checkSuccess(false, "设备连接失败!");
            }
            cardUid = MWUtils.getUid(epen);
            if (cardUid.length() > 0) {
//                Members members = membersMapper.getMembersByUID("0x" + StringsUtil.getOppositeTwo(cardUid));//根据会员卡号查询数据库
                Members members = membersMapper.getMembersByUID(cardUid);//根据会员卡号查询数据库
                if (members != null) {
                    if (receivedAmount <= members.getMemberCard().getAccountbalance()) {
                        lsResponse.setData(members);
                        lsResponse.setMessage("继续执行");
                    } else {
                        members.setChae(members.getMemberCard().getAccountbalance() - receivedAmount);
                        lsResponse.setData(members);
                        lsResponse.checkSuccess(false, "该会员卡内余额不足，请充值");
                    }
                } else {
                    lsResponse.checkSuccess(false, "该会员不存在");
                }
            } else {
                lsResponse.checkSuccess(false, "读卡失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.getMsg());
        } finally {
            if (epen != null) {
                MWUtils.disconnectDev(epen);
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMemberTypeListAllBT() {
        LsResponse lsResponse = LsResponse.newInstance();
        lsResponse.setData(memberTypeMapper.getMemberTypeListAll());
        return lsResponse;
    }

    @Override
    public synchronized LsResponse readCardBackUid() {
        LsResponse lsResponse = LsResponse.newInstance();
        Declare.mwrf epen = null;
        short i = 1;
        String cardUid = "";
        try {
            epen = MWUtils.getDLLConn();
            if (epen == null) {
                return lsResponse.checkSuccess(false, "读卡失败");
            }
            i = MWUtils.DevConnectShort(epen);
            if (i == 1) {
                return lsResponse.checkSuccess(false, "设备连接失败!");
            }
            cardUid = MWUtils.getUid(epen);
            if (cardUid.length() > 0) {
                lsResponse.setData(cardUid);
//                lsResponse.setData("0x" + StringsUtil.getOppositeTwo(cardUid));
            } else {
                lsResponse.checkSuccess(false, "读卡失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.getMsg());
        } finally {
            if (epen != null) {
                MWUtils.disconnectDev(epen);
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMemberListBT(CounterUser counterUser, String memberName, String memberTel, String memberCardUid, Integer typeId, String counterId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer offSet = 0;
        try {
            if (counterId != null && !counterId.equals("")) {
                counterId = new String(counterId.getBytes("utf-8"), "utf-8");
            }
            if (memberName != null && !memberName.equals("")) {
                memberName = new String(memberName.getBytes("utf-8"), "utf-8");
            } else {
                memberName = "";
            }
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
        }
        try {
            Counter counter = counterMapper.getCounterByCounterId(counterId);
            if (counter == null) {
                return lsResponse.checkSuccess(false, "吧台不存在，请联系管理员");
            }
            Integer total = membersMapper.getMemberListBTCount(memberName, memberTel, memberCardUid, typeId, counter.getcanteenId());
            List<Members> members = membersMapper.getMemberListBT(memberName, memberTel, memberCardUid, typeId, counter.getcanteenId(), offSet, pageSize);
            if (members != null && members.size() > 0) {
                lsResponse.setTotalCount(total);
                lsResponse.setData(members);
            } else {
                lsResponse.setSuccess(false);
                if (memberName != null && !memberName.equals("")) {
                    lsResponse.setMessage("该会员不存在");
                } else if (memberTel != null && !memberTel.equals("")) {
                    lsResponse.setMessage("该会员不存在");
                } else if (memberCardUid != null && !memberCardUid.equals("")) {
                    lsResponse.setMessage("该会员不存在");
                } else if (typeId != null) {
                    lsResponse.setMessage("该类型会员不存在");
                } else {
                    lsResponse.setMessage("没有会员信息");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getOrderListBT(CounterUser counterUser, String counterId, String date, Integer payType, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] dateStr = new String[2];
        Integer offSet = 0;
        try {
            if (counterId != null && !counterId.equals("")) {
                counterId = new String(counterId.getBytes("iso8859-1"), "utf-8");
            }
            if (date != null && !"".equals(date)) {
                dateStr = date.split(" - ");
                dateStr[0] = dateStr[0].replace("/", "-");
                dateStr[1] = dateStr[1].replace("/", "-");
            } else {
                dateStr[0] = Dates.format(Dates.getBeforeDate(new Date(), 30), "yyyy-MM-dd");
                dateStr[1] = Dates.format(new Date(), "yyyy-MM-dd");
            }
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        try {
            TotalCountMoney total = counterOrderMapper.getOrderListCountBT(counterId, dateStr[0], dateStr[1], payType);
            List<CounterOrder> btOrderList = counterOrderMapper.getOrderListBT(counterId, dateStr[0], dateStr[1], payType, offSet, pageSize);
            if (btOrderList != null && btOrderList.size() > 0) {
                lsResponse.setData(btOrderList);
                lsResponse.setTotalCount(total.getTotalCount());
                lsResponse.setMessage(total.getTotalMoney().toString());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMemberOrderListBT(CounterUser counterUser, String counterId, String memberName, String memberCardUid, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer offSet = 0;
        try {
            if (counterId != null && !counterId.equals("")) {
                counterId = new String(counterId.getBytes("iso8859-1"), "utf-8");
            }
            if (memberName != null && !memberName.equals("")) {
//                memberName = new String(memberName.getBytes("iso8859-1"), "utf-8");
                memberName = new String(memberName.getBytes("utf-8"), "utf-8");
            } else {
                memberName = "";
            }
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        try {
            TotalCountMoney total = counterOrderMapper.getMemberOrderListCount(counterId, memberName, memberCardUid);
            List<CounterOrder> counterOrders = counterOrderMapper.getMemberOrderList(counterId, memberName, memberCardUid, offSet, pageSize);
            if (counterOrders != null && counterOrders.size() > 0) {
                lsResponse.setMessage(total.getTotalMoney().toString());//设置 实收金额总额
                lsResponse.setErrorCode(total.getTotalReceivableMoney().toString());//设置 应收金额总额
                lsResponse.setTotalCount(total.getTotalCount());//设置 总的订单个数
                lsResponse.setData(counterOrders);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMemberOrderItemsBT(CounterUser counterUser, String orderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (orderId != null && !orderId.equals("")) {
                orderId = new String(orderId.getBytes("iso8859-1"), "utf-8");
            } else {
                return lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        try {
            List<CounterOrderItems> counterOrderItems = counterOrderMapper.getMemberOrderItems(orderId);
            if (counterOrderItems != null && counterOrderItems.size() > 0) {
                lsResponse.setData(counterOrderItems);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

}
