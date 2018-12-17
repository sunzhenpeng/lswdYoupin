package com.lswd.youpin.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.lswd.youpin.common.util.Verifycode;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.commons.SmsMessage;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.SmsService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuhao on 2017/6/30.
 */
@Service
public class SmsServiceImpl implements SmsService{
    private final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
    @Autowired
    private RedisManager redisManager;
    @Override
    public LsResponse sendMsg(String phone) {
        LsResponse lsResponse = new LsResponse();
        try {
            int count = 0;
            if (redisManager.get(phone.getBytes()) != null) {
                String codes = new String(redisManager.get(phone.getBytes()));
                String[] smsCodes = codes.split(",");
                count = Integer.parseInt(smsCodes[1]);
            }
            if (count < SmsMessage.count) {
                String code = new String(Verifycode.generateRandomArray(6));
                SendSmsResponse response = SmsUtil.sendSms(phone, code);
                if ("ok".equalsIgnoreCase(response.getCode())) {
                    code = code + "," + String.valueOf(count + 1);
                    redisManager.set(phone.getBytes(), code.getBytes(), 86400);
                } else {
                    lsResponse.setSuccess(false);
                }
                lsResponse.setErrorCode(response.getCode());
                lsResponse.setMessage(response.getMessage());
                lsResponse.setData(response.getRequestId());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.SMS_SEND_ERR.name());
            }
        } catch (ClientException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SMS_SEND_INTERSACE_ERR.name());
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse sendTzMsg(String phone,String orderId,String time,String address) {
        LsResponse lsResponse = new LsResponse();
        try {
            SendSmsResponse response = SmsUtil.sendSmsTz(phone, orderId,time,address);
            lsResponse.setErrorCode(response.getCode());
            lsResponse.setMessage(response.getMessage());
            lsResponse.setData(response.getRequestId());
        } catch (ClientException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SMS_SEND_INTERSACE_ERR.name());
            lsResponse.setData(e.toString());
        }catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }


}
