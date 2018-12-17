package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.WeiXinThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.TenantAssociator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by SAMA on 2017/6/9.
 */
@Api(value = "weixin", tags = "weixin", description = "微信管理")
@Controller
@RequestMapping(value = "/weixin")
public class WeiXinController {

  @Autowired
  private WeiXinThin weiXinThin;
    @ApiOperation(value = "追加商家微信用户信息", notes = "追加商家微信用户信息", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody TenantAssociator tenantAssociator, HttpServletRequest request) {

        User user= (User) request.getAttribute("user");
        System.out.println(user);
        LsResponse lsResponse = weiXinThin.tenantAssociator(tenantAssociator,user);
        return lsResponse;
    }
    @ApiOperation(value = "删除商家微信用户", notes = "删除商家微信用户", nickname = "liuhao", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse delete(@PathVariable(value = "id") Integer id, HttpServletRequest request)
    {
        User user=(User) request.getAttribute("user");
        LsResponse lsResponse=weiXinThin.deleteTenantAssociator(id,user);
        return  lsResponse;
    }
    @ApiOperation(value = "修改商家微信用户信息", notes = "修改商家微信用户信息", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse update(@RequestBody TenantAssociator tenantAssociator, HttpServletRequest request)
    {
        User user=(User) request.getAttribute("user");
        LsResponse lsResponse=weiXinThin.updateTenantAssociator(user,tenantAssociator);
        return  lsResponse;
    }

    @ApiOperation(value = "获取商家微信用户列表", notes = "获取商家微信用户列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getuserList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getTenantAssociatorList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                              @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                              @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                              HttpServletRequest request)
    {
        User user=(User) request.getAttribute("user");
        LsResponse lsResponse=weiXinThin.getTenantAssociatorList(user,keyword,pageNum,pageSize);
        return  lsResponse;
    }

    @ApiOperation(value = "查看商家微信用户详细信息", notes = "查看商家微信用户详细信息", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/look/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse lookTenantAssociator(@PathVariable(value = "id") Integer id)
    {
        LsResponse lsResponse=weiXinThin.lookTenantAssociator(id);
        return  lsResponse;
    }

    @ApiOperation(value = "微信小程序用户登录", notes = "微信小程序用户登录", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse login(@RequestBody TenantAssociator associator)
    {
        LsResponse lsResponse=weiXinThin.login(associator);
        return lsResponse;

    }

    @ApiOperation(value = "微信用户绑定餐厅详细信息", notes = "微信用户绑定餐厅详细信息", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/canteen/{associatorId}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse lookCanteen(@PathVariable(value = "associatorId") String  associatorId)
    {
        LsResponse lsResponse=weiXinThin.lookCanteen(associatorId);
        return lsResponse;

    }

    @ApiOperation(value = "返回微信用户手机号", notes = "返回微信用户手机号", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getAssociator", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAssociator(HttpServletRequest request)
    {
        LsResponse lsResponse=new LsResponse();
        TenantAssociator tenantAssociator=(TenantAssociator)request.getAttribute("tenantAssociator");
        TenantAssociator associator=(TenantAssociator)weiXinThin.lookTenantAssociator(tenantAssociator.getId()).getData();
        if(associator!=null)
        {
            lsResponse.setData(associator);
        }else{
            lsResponse.checkSuccess(false, CodeMessage.TENANTASSOCIATOR_NO_PHONE.name());
        }
        return  lsResponse;
    }

    @ApiOperation(value = "修改登录密码", notes = "修改登录密码", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/updatePasword", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updatePasword(@RequestBody String data, HttpServletRequest request)
    {
        TenantAssociator tenantAssociator=(TenantAssociator)request.getAttribute("tenantAssociator");
        LsResponse lsResponse=weiXinThin.updatePasword(data,tenantAssociator);
        return  lsResponse;
    }

    @ApiOperation(value = "修改绑定的手机号", notes = "修改绑定的手机号", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updatePhone(@RequestBody String data, HttpServletRequest request)
    {
        TenantAssociator tenantAssociator=(TenantAssociator)request.getAttribute("tenantAssociator");
        LsResponse lsResponse=weiXinThin.updatePhone(data,tenantAssociator);
        return  lsResponse;
    }
    @ApiOperation(value = "查找小程序账号是否注册", notes = "查找小程序账号是否注册", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getName/{name}", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getName(@PathVariable(value = "name") String name)
    {
        LsResponse lsResponse=weiXinThin.getName(name);
        return  lsResponse;
    }

}
