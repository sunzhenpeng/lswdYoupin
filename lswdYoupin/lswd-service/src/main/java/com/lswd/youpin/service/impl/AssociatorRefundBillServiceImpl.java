package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.AssociatorRefundBillMapper;
import com.lswd.youpin.dao.UserMapper;
import com.lswd.youpin.minghua.Declare;
import com.lswd.youpin.minghua.MWUtils;
import com.lswd.youpin.model.AssociatorPayBill;
import com.lswd.youpin.model.AssociatorRefundBill;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.vo.TotalCountMoney;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorRefundBillService;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by zhenguanqi on 2017/11/30.
 */
@Service
public class AssociatorRefundBillServiceImpl implements AssociatorRefundBillService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(AssociatorRefundBillServiceImpl.class);

    @Autowired
    private AssociatorRefundBillMapper associatorRefundBillMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public LsResponse getAssociatorRefundBillList(String keyword, String canteenId, String date, Integer pageNum, Integer pageSize, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<String> canteenIds = new ArrayList<>();
        String[] dateStr = new String[2];
        Integer offSet = 0;
//        Integer total = 0;
        try {
            keyword=StringsUtil.encodingChange(keyword);
            canteenId=StringsUtil.encodingChange(canteenId);
            if (canteenId != null && !"".equals(canteenId)) {
                canteenIds.add(canteenId);
            } else {
                canteenId = "";
                String[] canteens = user.getCanteenIds().split(",");
                for (String can : canteens) {
                    canteenIds.add(can);
                }
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
            TotalCountMoney total = associatorRefundBillMapper.getAssociatorRefundBillListCount(keyword, canteenIds, dateStr[0], dateStr[1], user, canteenId);
            List<AssociatorRefundBill> associatorRefundBills = associatorRefundBillMapper.getAssociatorRefundBillList(keyword, canteenIds, dateStr[0], dateStr[1], user, canteenId, offSet, pageSize);
//            Integer all = associatorRefundBillMapper.getAssociatorRefundBillListAll(keyword, canteenIds, dateStr[0], dateStr[1], user, canteenId);
            if (associatorRefundBills != null && associatorRefundBills.size() > 0) {
                for (AssociatorRefundBill bill : associatorRefundBills) {
                    User user1 = userMapper.getUserByUserId(bill.getUserId());
                    bill.setUserName(user1.getUsername());
                }
                lsResponse.setTotalCount(total.getTotalCount());
                lsResponse.setMessage(total.getTotalMoney().toString());
//                lsResponse.setMessage(all.toString());
                lsResponse.setData(associatorRefundBills);
            } else {
                lsResponse.setAsFailure();
//                lsResponse.checkSuccess(false,CodeMessage.EMPTY_DATA.getMsg());
                if (keyword != null && !keyword.equals("")) {
                    lsResponse.setMessage("该会员没有充值记录");
                } else {
                    lsResponse.setMessage("该列表暂无数据");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.getMsg());
        }
        return lsResponse;
    }

}
