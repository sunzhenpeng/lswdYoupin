package com.lswd.youpin.web.controller;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CardPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liruilong on 2017/8/10.
 */
@Api(value = "card", tags = "card", description = "卡支付")
@Controller
@RequestMapping(value = "/card")
public class CardPayController {
    @Autowired
    private CardPayService cardPayService;
    @ApiOperation(value = "餐卡余额", notes = "餐卡余额", nickname = "liruilong", httpMethod = "GET")
    @RequestMapping(value = "/getBalance", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getBalance(HttpServletRequest request) {
        Associator associator=(Associator)request.getAttribute("associator");
        return cardPayService.getBalance(associator.getMemberId());
    }
}
