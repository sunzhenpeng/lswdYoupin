package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.MemberRefundBillMapper;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MemberPayBill;
import com.lswd.youpin.model.lsyp.MemberRefundBill;
import com.lswd.youpin.model.vo.TotalCountMoney;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MemberRefundBillService;
import com.lswd.youpin.utils.StringsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/12/08.
 */
@Service
public class MemberRefundBillServiceImpl implements MemberRefundBillService{
    private final Logger logger = LoggerFactory.getLogger(MemberRefundBillServiceImpl.class);

    @Autowired
    private MemberRefundBillMapper memberRefundBillMapper;


    @Override
    public LsResponse getMemberRefundBillList(String keyword, String canteenId, String date, Integer pageNum, Integer pageSize, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] canteenIds = user.getCanteenIds().split(",");
        String[] dateStr = new String[2];
        Integer offSet = 0;
        try {
            keyword = StringsUtil.encodingChange(keyword);
            canteenId = StringsUtil.encodingChange(canteenId);
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
            return lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        try {
            TotalCountMoney total = memberRefundBillMapper.getMemberRefundBillListCount(keyword, dateStr[0], dateStr[1], canteenId, canteenIds);
            List<MemberRefundBill> memberRefundBills = memberRefundBillMapper.getMemberRefundBillList(keyword, dateStr[0], dateStr[1], canteenId, canteenIds, offSet, pageSize);
            if (memberRefundBills != null && memberRefundBills.size() > 0) {
                lsResponse.setTotalCount(total.getTotalCount());
                lsResponse.setMessage(total.getTotalMoney().toString());
                lsResponse.setData(memberRefundBills);
            } else {
                lsResponse.setAsFailure();
                if (keyword != null && !keyword.equals("")) {
                    lsResponse.setMessage("该会员没有退款记录");
                } else if (canteenId != null && !canteenId.equals("")) {
                    lsResponse.setMessage("该餐厅没有会员的退款记录");
                } else {
                    lsResponse.setMessage("无退款记录");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }
}
