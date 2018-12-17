package com.lswd.youpin.web.controller;

import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuhao on 2017/6/30.
 */
@Api(value = "sms", tags = "sms", description = "短信验证码")
@Controller
@RequestMapping(value = "/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;
    
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/send/{phone}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse sendMsg(@PathVariable(value = "phone") String phone)
    {
        LsResponse lsResponse =smsService.sendMsg(phone);
        return lsResponse;
    }

}
