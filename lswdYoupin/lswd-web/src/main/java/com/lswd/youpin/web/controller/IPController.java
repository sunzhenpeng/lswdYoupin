package com.lswd.youpin.web.controller;


import com.lswd.youpin.common.util.PropertiesUtils;
import com.lswd.youpin.response.LsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by zhenguanqi on 2017/12/23.
 * 未用到！！！
 */
@Controller
@Api(value = "ip", tags = "ip", description = "获取本机ip地址111")
@RequestMapping("ip")
public class IPController {

    @ApiOperation(value = "获取本机的ip地址111", notes = "获取本机的ip地址111", nickname = "zhenguanqi", httpMethod = "POST")
    @RequestMapping(value = "/getLocalHostIP", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse getLocalHostIP(HttpServletRequest request) {
        LsResponse lsResponse = LsResponse.newInstance();
        String ip = null;
        try {
            ip = PropertiesUtils.getLocalHostIP();
            if (ip != null) {
                String ipall = "http://" + ip + ":8080/lsyp";
                lsResponse.setData(ipall);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }
}
