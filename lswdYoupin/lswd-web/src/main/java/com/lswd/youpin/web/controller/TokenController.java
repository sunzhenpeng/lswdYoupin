package com.lswd.youpin.web.controller;

import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by H61M-K on 2017/6/21.
 */
@Api(value = "token", tags = "token", description = "shiro 测试")
@Controller
@RequestMapping(value = "token")
public class TokenController {
    @Autowired
    private UserService userService;
    @ApiOperation(value = "验证token过没过期", notes = "验证token过没过期", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/isToken", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse isToken(@RequestBody String token) {
        LsResponse lsResponse=userService.isToken(token);
        return lsResponse;
    }

}
