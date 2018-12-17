package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.dao.PersonInfoMapper;
import com.lswd.youpin.model.PersonInfo;
import com.lswd.youpin.model.lsyp.MemberCard;
import com.lswd.youpin.model.lsyp.Members;
import com.lswd.youpin.model.vo.PersonInfoCount;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MembersService;
import com.lswd.youpin.service.PersonInfoService;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by H61M-K on 2018/2/7.
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    private static final Logger logger = LoggerFactory.getLogger(PersonInfoServiceImpl.class);
    public static final String CANTEENID = "LSCT100026";
    public static final String MEMBERERROR = "TRUNCATE会员失败";
    public static final String CARDERROR = "TRUNCATE会员餐卡失败";
    public static final String PERSONINFONULL = "未获取到personInfo信息";
    public static final String CARDINSERTERROR = "插入会员餐卡失败";
    public static final String MEMBERINSERTERROR = "插入会员失败";

    @Autowired
    private PersonInfoMapper personInfoMapper;
    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private MembersService membersService;

    @Override
    public List<PersonInfo> getAll() {
        return personInfoMapper.getAll();
    }

    @Override
    public LsResponse getStuCountClass() {
        LsResponse lsResponse = LsResponse.newInstance();
        List<PersonInfoCount> counts = personInfoMapper.getStuCountClass();
        if (counts != null && counts.size() > 0) {
            lsResponse.setData(counts);
        }
        return lsResponse;
    }


    /**
     * 获取每个地区的人数 ：市辖区是370201、青岛市南区是370202、青岛市北区是370203、四方区是370205、崂山区是370212、李沧区是370213
     * 黄岛区是370211，城阳区是370214、胶州市是370281、即墨市是370282、平度市是370283、胶南市是370284、莱西市是370285
     *
     * @return
     */
//    @Override
//    public LsResponse getRegionStudentCount() {
//        LsResponse lsResponse = LsResponse.newInstance();
//        Map<String, Integer> map = new HashMap<>();
//        List<PersonInfo> personInfos = personInfoMapper.getRegionStudentCount();
//        if (personInfos != null && personInfos.size() > 0) {
//            Integer count = 0;
//            for (PersonInfo personInfo : personInfos) {
//                if (personInfo.getIdNo() != null && !personInfo.getIdNo().equals("")){
//                    String flag = personInfo.getIdNo().trim().substring(0,6);
//                    if (flag.equals("370214") || flag.equals("370281") || flag.equals("370282") || flag.equals("370283") ||flag.equals("370284") ||flag.equals("370285")){
//                        String ageFlag = personInfo.getIdNo().trim().substring(6,10);
//                        Integer age = 2018 - Integer.valueOf(ageFlag);
//                        if (age <= 16){
//                            count++;
//                        }
//                    }
//                }else {
//                    continue;
//                }
//            }
//            map.put("六区之外，低于或等于16周岁的数量",count);
//            lsResponse.setData(map);
//        }
//        return lsResponse;
//    }

    /**
     * 获取每个地区的人数 ：市辖区是370201、青岛市南区是370202、青岛市北区是370203、四方区是370205、崂山区是370212、李沧区是370213
     * 黄岛区是370211，城阳区是370214、胶州市是370281、即墨市是370282、平度市是370283、胶南市是370284、莱西市是370285
     *
     * @return
     */
    @Override
    public LsResponse getRegionStudentCount() {
        LsResponse lsResponse = LsResponse.newInstance();
        Map<String, Integer> map = new HashMap<>();
        List<PersonInfo> personInfos = personInfoMapper.getRegionStudentCount();
        if (personInfos != null && personInfos.size() > 0) {
            Integer count1 = 0;
            Integer count2 = 0;
            Integer count3 = 0;
            Integer count4 = 0;
            Integer count5 = 0;
            Integer count6 = 0;
            Integer count7 = 0;
            Integer count8 = 0;
            for (PersonInfo personInfo : personInfos) {
                if (personInfo.getIdNo() != null && !personInfo.getIdNo().equals("")){
                    String flag = personInfo.getIdNo().trim().substring(0,6);
                    if (flag.equals("370211")){
                        count1 ++;
                    }else if (flag.equals("370214")){
                        count2 ++;
                    }else if (flag.equals("370281")){
                        count3 ++;
                    }else if(flag.equals("370282")){
                        count4++;
                    }else if (flag.equals("370283")){
                        count5++;
                    }else if (flag.equals("370284")){
                        count6++;
                    }else if (flag.equals("370285")){
                        count7++;
                    }else {
                        count8++;
                    }
                }else {
                    continue;
                }
            }
            map.put("1",count1);
            map.put("2",count2);
            map.put("3",count3);
            map.put("4",count4);
            map.put("5",count5);
            map.put("6",count6);
            map.put("7",count7);
            map.put("8",count8);
            lsResponse.setData(map);
        }
        return lsResponse;
    }

    @Override
    public void synPersonInfo() {
        logger.info("start正在同步会员信息-------------------------------------------------------------------");
        List<PersonInfo> personInfos = new ArrayList<>();
        try {
            DataSourceHandle.setDataSourceType("DSSS");
            if (true) {
                personInfos = personInfoMapper.getAll();
            }else {
                String sdf = "yyyy-MM-dd 09:00:00";
                Date time = Dates.getBeforeDate(new Date(),1);//获取之前一天
                String startTime = Dates.format(time,sdf);
                String endTime = Dates.format(new Date(),sdf);//获取现在时间
                personInfos = personInfoMapper.getPersonInfoTime(startTime,endTime);
            }
            if (personInfos != null && personInfos.size() > 0) {
                DataSourceHandle.setDataSourceType("LSCT");
                membersService.deleteMemberCardAll();//truncate会员餐卡信息
                membersService.deleteMembersAll();//truncate会员信息
                for (PersonInfo person : personInfos) {
//                    MemberCard memberCard = new MemberCard();
//                    memberCard.setServicetime(new Date());
//                    memberCard.setCarduid(person.getCardNo());
//                    memberCard.setAccountbalance(person.getBalance().floatValue());
//                    memberCard.setIsdeleted(person.getState() == 0 ? false : true);
//                    membersService.insertMemberCard(memberCard);//插入会员餐卡记录
                    Members members = new Members();
                    members.setId(person.getId());//设置主键id
                    members.setMemberId("JST" + person.getNumber());
                    members.setState(person.getState());//0:正常卡、1：挂失卡、2：注销卡、3：未办卡人员
                    members.setMemberTypeId(person.getState());//0:正常卡、1：挂失卡、2：注销卡、3：未办卡人员
                    members.setLeaveName(person.getLeaveName());//设置会员餐卡的等级
                    members.setMemberName(person.getName());
                    members.setTelephone(person.getPhone());
                    members.setCarduid(person.getIdNo());//会员卡号，卡号uid
//                    members.setCardid(memberCard.getCardid());//memberCard表中的主键
                    members.setSex(false);//设置男女，万旺系统中，会员的属性中没有这个字段
                    members.setIdentityCard(person.getIdNo());//设置会员的身份证件号码
                    members.setCanteenId(CANTEENID);//设置餐厅编号
                    members.setIsDelete(false);//是否删除，否
                    members.setIsUse(true);//是否使用，yes
                    members.setRemark(person.getNote());
                    members.setCreateUser(person.getOperator());//设置开卡操作人员
                    members.setCreateTime(person.getOpenTime());//设置开卡时间
                    members.setUpdateUser(person.getOperator());//设置修改用户人员
                    members.setUpdateTime(person.getLastAddMoneyTime());//设置最后充值时间
                    membersService.insertMember(members);
                }
                logger.info("end正在同步会员信息,数据同步成功-------------------------------------------------------------------");
            } else {
                logger.info("未从sql server数据库获取到会员信息-------------------------------------------------------------------end");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            logger.error("会员信息同步失败，失败原因为：" + e.toString());
        }
    }

}
