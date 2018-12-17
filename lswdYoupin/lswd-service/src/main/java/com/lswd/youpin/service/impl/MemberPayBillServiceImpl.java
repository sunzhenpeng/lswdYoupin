package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.MemberPayBillMapper;
import com.lswd.youpin.minghua.Declare;
import com.lswd.youpin.minghua.MWUtils;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.MemberCard;
import com.lswd.youpin.model.lsyp.MemberPayBill;
import com.lswd.youpin.model.vo.TotalCountMoney;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.MemberPayBillService;
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
public class MemberPayBillServiceImpl implements MemberPayBillService {
    private final Logger logger = LoggerFactory.getLogger(MemberRefundBillServiceImpl.class);

    @Autowired
    private MemberPayBillMapper memberPayBillMapper;

    @Override
    public LsResponse getMemberPayBillList(String keyword, String canteenId, String date, Integer payType,Integer pageNum, Integer pageSize, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] canteenIds = user.getCanteenIds().split(",");
        String[] dateStr = new String[2];
        Integer offSet = 0;
        try {
            keyword = StringsUtil.encodingChange(keyword);
            canteenId = StringsUtil.encodingChange(canteenId);
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (date != null && !"".equals(date)) {
                dateStr = date.split(" - ");
                dateStr[0] = dateStr[0].replace("/", "-");
                dateStr[1] = dateStr[1].replace("/", "-");
            } else {
                dateStr[0] = Dates.format(Dates.getBeforeDate(new Date(), 30), "yyyy-MM-dd");
                dateStr[1] = Dates.format(new Date(), "yyyy-MM-dd");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        try {
            TotalCountMoney total = memberPayBillMapper.getMemberPayBillListCount(keyword, dateStr[0], dateStr[1], canteenId, canteenIds,payType);
            List<MemberPayBill> memberPayBills = memberPayBillMapper.getMemberPayBillList(keyword, dateStr[0], dateStr[1], canteenId, canteenIds, payType,offSet, pageSize);
            if (memberPayBills != null && memberPayBills.size() > 0) {
                lsResponse.setTotalCount(total.getTotalCount());
                lsResponse.setMessage(total.getTotalMoney().toString());
                lsResponse.setData(memberPayBills);
            } else {
                lsResponse.setAsFailure();
                if (keyword != null && !keyword.equals("")) {
                    lsResponse.setMessage("该会员没有充值记录");
                } else if (canteenId != null && !canteenId.equals("")) {
                    lsResponse.setMessage("该餐厅没有会员的充值记录");
                } else {
                    lsResponse.setMessage("无充值记录");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public synchronized LsResponse readCardUid() {
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
}
