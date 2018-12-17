package com.lswd.youpin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by liruilong on 2016/9/27.
 */
@ApiIgnore
@Controller
public class ExceptionController {

    @RequestMapping(value = "/error_404", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String error_404() throws Exception {
        return null;
    }

    /**
     * 服务器异常
     *
     * @return String
     */
    @RequestMapping(value = "/error_500", produces = "application/json;charset=UTF-8")
     @ResponseBody
     public String error_500() {
        return null;
    }

    @RequestMapping(value = "/error_400", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String error_400() {
        return null;
    }

}
