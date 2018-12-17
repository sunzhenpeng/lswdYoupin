package com.lswd.youpin.Thin.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.Thin.WxThin;
import com.lswd.youpin.commons.ConstantsCode;
import com.lswd.youpin.service.WxService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.wxpay.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by liruilong on 2017/8/10.
 */
@Component
public class WxThinImpl implements WxThin {

    @Autowired
    private WxService wxService;

    private final Logger log = LoggerFactory.getLogger(WxThinImpl.class);

    /**
     * 微信支付回调函数
     * 支付完成后，微信会把相关支付结果和用户信息发送给商户，商户需要接收处理，并返回应答。
     * 对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知，
     * 尽可能提高通知的成功率，但微信不保证通知最终能成功。 （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒）。
     * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
     * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
     * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
     * 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失
     *
     * @return
     */
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("执行微信支付异步返回通知函数");
        try {
            InputStream inputStream;
            StringBuffer sb = new StringBuffer();
            inputStream = request.getInputStream();
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            in.close();
            inputStream.close();
            // 解析xml成map
            Map<String, String> m = new HashMap<>();
            m = CommonUtil.parseXml(sb.toString());
            // 过滤空 设置 TreeMap
            SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
            Iterator it = m.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = m.get(parameter);
                String v = "";
                if (null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            log.info("回调返回参数=======" + packageParams);
            String jsonAttach = String.valueOf(packageParams.get("attach"));
            JSONObject jsonObject = JSON.parseObject(jsonAttach);
            String canteenId = String.valueOf(jsonObject.get("canteenId"));
            DataSourceHandle.setDataSourceType(canteenId.substring(0, 4));
            wxService.wxNotify(packageParams, request, response);
        } catch (Exception e) {
            log.info("数据库切换异常={}", e.getMessage());
        }
    }
}
