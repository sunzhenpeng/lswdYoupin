package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.MemberCardMapper;
import com.lswd.youpin.dao.lsyp.MemberPayBillMapper;
import com.lswd.youpin.dao.lsyp.MemberRefundBillMapper;
import com.lswd.youpin.dao.lsyp.MembersMapper;
import com.lswd.youpin.minghua.Declare;
import com.lswd.youpin.minghua.MWUtils;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MemberCard;
import com.lswd.youpin.model.lsyp.MemberPayBill;
import com.lswd.youpin.model.lsyp.MemberRefundBill;
import com.lswd.youpin.model.lsyp.Members;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MembersService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.lang.reflect.Member;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/12/08.
 */
@Service
public class MembersServiceImpl implements MembersService {

    private final Logger logger = LoggerFactory.getLogger(MembersServiceImpl.class);

    @Autowired
    private MembersMapper membersMapper;
    @Autowired
    private MemberCardMapper memberCardMapper;
    @Autowired
    private MemberPayBillMapper memberPayBillMapper;
    @Autowired
    private MemberRefundBillMapper memberRefundBillMapper;
    @Autowired
    private RedisManager redisManager;

    @Override
    public LsResponse getMembersList(User user, String keyword, Integer pageNum, Integer pageSize, String canteenId, Integer typeId) {
        LsResponse lsResponse = new LsResponse();
        String[] canteenIds = user.getCanteenIds().split(",");
        try {
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            keyword = StringsUtil.encodingChange(keyword);
            canteenId = StringsUtil.encodingChange(canteenId);
            int total = membersMapper.getMembersListCount(keyword, typeId, canteenId, canteenIds);
            List<Members> MembersList = membersMapper.getMembersList(keyword, typeId, canteenId, canteenIds, offSet, pageSize);
            if (MembersList != null && MembersList.size() > 0) {
                lsResponse.setData(MembersList);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.setSuccess(false);
                if (keyword != null && !"".equals(keyword)) {
                    lsResponse.setMessage("该会员不存在");
                } else if (canteenId != null && !"".equals(canteenId)) {
                    lsResponse.setMessage("该餐厅没有会员信息");
                } else if (typeId != null) {
                    lsResponse.setMessage("该类型会员不存在");
                } else {
                    lsResponse.setMessage("没有会员信息");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    /**
     * 读卡， 判断该卡是否已经被注册
     *
     * @param user
     * @return
     */
    @Override
    public synchronized LsResponse existCardUID(User user) {
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
                MemberCard MembersCard = memberCardMapper.getMembersByCardUid(cardUid);//转换卡号
                if (MembersCard != null) {
                    lsResponse.checkSuccess(false, "该卡已被注册，请换卡重试");
                } else {
                    lsResponse.setData(cardUid);
                }
            } else {
                lsResponse.checkSuccess(false, "卡号为空，请联系管理员");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        } finally {
            if (epen != null) {
                MWUtils.disconnectDev(epen);
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse addMembersWeb(Members members, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        MemberCard memberCard = new MemberCard();
        if (members.getMemberName().equals("") || members.getMemberName() == null) {
            lsResponse.checkSuccess(false, "请输入会员姓名");
        }
        if (members.getTelephone().equals("") || members.getTelephone() == null) {
            lsResponse.checkSuccess(false, "请输入会员手机号");
        }
        String cardUid = members.getMemberCard().getCarduid();//得到会员卡号
        try {
            memberCard.setCarduid(cardUid);//设置会员卡号uid的转换格式
            memberCard.setServicetime(Dates.now());
            Integer aCardFlag = memberCardMapper.insertSelective(memberCard);
            if (aCardFlag > 0 && aCardFlag != null) {
                Integer cardid = memberCardMapper.getCardidByCardUid(cardUid);
                Integer id = membersMapper.getMembersMaxId();//查找数据dh_members库表中的最大id
                if (id == null) {
                    id = 0;
                }
                members.setMemberId("JST" + String.valueOf(100001 + id));//设置会员编号，其规则为1000001+自增id
                members.setCardid(cardid);//设置会员卡主键
                members.setCreateTime(Dates.now());//设置会员创建时间
                members.setUpdateTime(Dates.now());//设置会员更新时间
                members.setIsUse(true);
                members.setIsDelete(false);
                Integer insertFlag = membersMapper.insertSelective(members);
                if (insertFlag != null && insertFlag > 0) {
                    lsResponse.setMessage("结算台会员添加成功");
                } else {
                    lsResponse.checkSuccess(false, "结算台会员添加失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务处理，如果卡表插入成功，会员表插入失败，rollback
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateMembersWeb(Members members, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        MemberCard memberCard = new MemberCard();
        if (members.getMemberName().equals("") || members.getMemberName() == null) {
            lsResponse.checkSuccess(false, "请输入会员姓名");
        }
        if (members.getTelephone().equals("") || members.getTelephone() == null) {
            lsResponse.checkSuccess(false, "请输入会员手机号");
        }
        String cardUid = members.getMemberCard().getCarduid();//得到会员卡号
        try {
            memberCard.setCardid(members.getCardid());//设置主键id
            memberCard.setCarduid(cardUid);//设置会员卡号uid的转换格式
            memberCard.setServicetime(Dates.now());
            Integer cardFlag = memberCardMapper.updateeMemberCard(memberCard);
            if (cardFlag > 0 && cardFlag != null) {
                members.setUpdateUser(user.getUsername());
                members.setUpdateTime(Dates.now());//设置会员修改时间
                Integer flag = membersMapper.updateByPrimaryKeySelective(members);
                if (flag != null && flag > 0) {
                    lsResponse.setMessage("结算台会员修改成功");
                } else {
                    lsResponse.checkSuccess(false, "结算台会员修改失败");
                }
            } else {
                lsResponse.checkSuccess(false, "结算台会员餐卡修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务处理，如果卡表插入成功，会员表插入失败，rollback
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteMembersWeb(Integer id) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (id == null) {
                return lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
            }
            Members member = membersMapper.getBalanceById(id);
            if (member.getBalance() > 0) {
                return lsResponse.checkSuccess(false, "该会员卡余额大于0，请先退款后删除");
            } else {
                Integer b = membersMapper.deleteByPrimaryKeyUpdate(id);//修改会员表中的状态
                if (b != null && b > 0) {
                    memberCardMapper.deleteByPrimaryKey(member.getCardid());
                    lsResponse.setMessage("结算台会员删除成功");
                } else {
                    lsResponse.checkSuccess(false, "结算台会员删除失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务处理，如果会员表删除成功，卡表删除失败，rollback
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMembersInfoWeb(Integer id, User user) {
        LsResponse lsResponse = new LsResponse();
        MemberCard memberCard;
        try {
            Members members = membersMapper.selectByPrimaryKey(id);
            if (members != null) {
                memberCard = memberCardMapper.selectByPrimaryKey(members.getCardid());
                if (memberCard != null) {
                    members.setMemberCard(memberCard);
                }
                lsResponse.setData(members);
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("查看结算台会员详情失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse giveMembersRecharge(String memberId, Float money, User user) {
        LsResponse lsResponse = new LsResponse();
        MemberPayBill memberPayBill = new MemberPayBill();
        try {
            Members member = membersMapper.getBalanceByMemberId(memberId);
            if (!member.getIsUse()){
                return lsResponse.checkSuccess(false,"该会员的状态处于禁用状态,如需充值，请先修改其状态");
            }
            if (memberId == null || memberId.equals("") || money == null) {
                return lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
            }
            if (money > 9999) {
                return lsResponse.checkSuccess(false, "充值金额不能多于9999");
            }
            if (money == 0) {
                return lsResponse.checkSuccess(false, "充值金额不能为0");
            }

            memberPayBill.setOutTradeNo(RandomUtil.getRandomCodeStr(6));//设置会员充值编号
            memberPayBill.setMemberId(memberId);//设置会员编号
            memberPayBill.setMoney(money);//设置充值的金额
            memberPayBill.setPayType(0);//0：表示线下支付，1：微信，2：支付宝
            memberPayBill.setPayTime(Dates.now());//设置充值时间
            memberPayBill.setUserId(user.getUserId());//设置操作员的编号
            memberPayBill.setUserName(user.getUsername());//设置操作员的名称
            Integer insertFlag = memberPayBillMapper.insertSelective(memberPayBill);
            if (insertFlag != null && insertFlag > 0) {
                Integer updateFlag = memberCardMapper.addCardBalance(memberId, money);
                if (updateFlag != null && updateFlag > 0) {
                    lsResponse.setMessage("为会员编号：" + memberId + "的会员充值成功");

                    PrinterJob job = PrientUtils.getPrinterJob();
                    PrientBean bean = new PrientBean();
                    bean.setMemberName(member.getMemberName());
                    bean.setMemberTel(member.getTelephone());
                    bean.setOldCardBalance(member.getBalance());
                    bean.setChargeMoney(money);
                    bean.setNewCardBalance(member.getBalance() + money);
                    bean.setChargeTime(Dates.now("yyyy-MM-dd HH:mm:dd"));
//                    bean.setChargeTime(new Date());
                    redisManager.set("PrientFlag".getBytes(), "0".getBytes());
                    redisManager.set("PrientBean".getBytes(), SerializeUtils.serialize(bean));
                    try {
                        if (PrientUtils.isPrint() == null){
                            return lsResponse.checkSuccess(true,"充值成功，但是无法找到打印机，打印失败");
                        }
                        job.setPrintService(PrientUtils.isPrint()[0]);//根据打印机名字获取打印机
                        job.print();
                    } catch (PrinterException e) {
                        e.printStackTrace();
                        lsResponse.checkSuccess(true,"充值成功，打印出现问题，请联系管理员");
                    }
                } else {
                    lsResponse.checkSuccess(false, "为会员编号：" + memberId + "的会员充值失败,请联系管理员");
                }
            } else {
                lsResponse.checkSuccess(false, "为会员编号：" + memberId + "的会员充值失败,请联系管理员");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务处理
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse refundMembersMoney(String memberId, Float money, User user) {
        LsResponse lsResponse = new LsResponse();
        MemberRefundBill memberRefundBill = new MemberRefundBill();
        try {
            if (memberId == null || memberId.equals("") || money == null) {
                return lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
            }
            Members members = membersMapper.getBalanceByMemberId(memberId);//查询会员现有余额
            if (members == null) {
                return lsResponse.checkSuccess(false, "退款失败，请联系管理员");
            } else {
                if (members.getBalance() < money) {
                    return lsResponse.checkSuccess(false, "卡内余额不足，不允许退款");
                } else {
                    memberRefundBill.setMoney(money);
                }
            }
            memberRefundBill.setOutTradeNo(RandomUtil.getRandomCodeStr(6));//设置会员退款编号
            memberRefundBill.setMemberId(memberId);//设置会员编号
            memberRefundBill.setRefundType(0);//0：表示线下退款
            memberRefundBill.setRefundTime(Dates.now());//设置退款时间
            memberRefundBill.setMoney(money);//设置退款时间
            memberRefundBill.setUserId(user.getUserId());//设置操作员编号
            memberRefundBill.setUserName(user.getUsername());//设置操作员的名称
            Integer insertFlag = memberRefundBillMapper.insertSelective(memberRefundBill);
            if (insertFlag != null && insertFlag > 0) {
                Integer updateFlag = memberCardMapper.subCardBalance(memberId, money);
                if (updateFlag != null && updateFlag > 0) {
                    lsResponse.setMessage("为会员编号为：" + memberId + "的会员退款成功");
                } else {
                    lsResponse.checkSuccess(false, "为会员编号为：" + memberId + "的会员退款失败");
                }
            } else {
                lsResponse.checkSuccess(false, "为会员编号为：" + memberId + "的会员退款失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务处理
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public synchronized LsResponse getMembersByUID(User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        Declare.mwrf epen = null;
        short i = 1;
        String cardUid = "";
        try {
            epen = MWUtils.getDLLConn();
            if (epen == null) {
                return lsResponse.checkSuccess(false, "读卡失败，请联系管理员");
            }
            i = MWUtils.DevConnectShort(epen);
            if (i == 1) {
                return lsResponse.checkSuccess(false, "设备连接失败!请联系管理员");
            }
            cardUid = MWUtils.getUid(epen);
            if (cardUid.length() > 0) {
                Members members = membersMapper.getMembersByUID(cardUid);
                if (members != null) {
                    lsResponse.setData(members);
                } else {
                    lsResponse.checkSuccess(false, "该会员不存在");
                }
            } else {
                lsResponse.checkSuccess(false, "请放卡，重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        } finally {
            if (epen != null) {
                MWUtils.disconnectDev(epen);
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateMemberStatus(Integer id, Boolean flag) {
        LsResponse lsResponse = new LsResponse();
        try {
            Members members = new Members();
            members.setId(id);
            if (flag) {
                members.setIsUse(true);
            } else {
                members.setIsUse(false);
            }
            Integer b = membersMapper.updateMemberStatus(members);
            if (b != null && b > 0) {
                lsResponse.setMessage("操作成功");
            } else {
                lsResponse.checkSuccess(false, "操作失败，请联系管理员");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public Members getMemberByPhone(String phone,String username) {
        logger.info("根据手机号码查询用户信息=====telephone=======" + phone);
        try {
            Members member = membersMapper.getMemberByPhoneAndUserName(phone,username);
            return member;
        } catch (Exception e) {
            logger.info("根据手机号码查询用户信息失败=={}", e.getMessage());
        }
        return null;
    }


    /*---------------会员信息同步，用到的 start-----------------*/
    @Override
    public Integer deleteMembersAll() {
        return membersMapper.deleteMembersAll();
    }

    @Override
    public Integer deleteMemberCardAll() {
        return membersMapper.deleteMemberCardAll();
    }

    @Override
    public Integer insertMemberCard(MemberCard memberCard) {
        return membersMapper.insertMemberCard(memberCard);
    }

    @Override
    public Integer insertMember(Members members) {
        return membersMapper.insertMember(members);
    }

     /*---------------会员信息同步，用到的 end-----------------*/
}
