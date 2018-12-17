package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.AddressThin;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorAddress;
import com.lswd.youpin.model.lsyp.Address;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AddressService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2017/6/7.
 */
@Api(value = "address", tags = "address", description = "地址管理")
@Controller
@RequestMapping(value = "/address")
public class AddressController {

    @Autowired
    private AddressService addressService;
   @Autowired
   private AddressThin addressThin;

    @ApiOperation(value = "新建地址", notes = "新建地址", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody Address address) {

        LsResponse lsResponse = addressService.addAddress(address);
        return lsResponse;
    }
    @ApiOperation(value = "删除地址", notes = "删除地址", nickname = "liuhao", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse delete(@PathVariable(value = "id") Integer id)
    {

        LsResponse lsResponse= addressThin.deleteAddress(id);
        return  lsResponse;
    }
    @ApiOperation(value = "修改地址", notes = "修改地址", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateAddress(@RequestBody Address address)
    {

        LsResponse lsResponse = addressService.updateAddress(address);
        return  lsResponse;
    }
    @ApiOperation(value = "地址列表", notes = "地址列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getaddressList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAddressList(@ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                     @ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                     @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize){
        if(!DataSourceConst.LSCT.equals(DataSourceHandle.getDataSourceType()))
        {
            DataSourceHandle.setDataSourceType(DataSourceConst.LSCT);
        }
        LsResponse lsResponse = addressService.getAddressList(keyword,pageNum,pageSize);
        return lsResponse;
    }

    @ApiOperation(value = "新建会员收货地址", notes = "新建会员收货地址", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addAddress(@RequestBody AssociatorAddress address, HttpServletRequest request) {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse = addressThin.addAssociatorAddress(address,associator);
        return lsResponse;
    }

    @ApiOperation(value = "会员收货地址列表", notes = "会员收货地址列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/getAsAddresList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getAsAddressList(HttpServletRequest request) {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse = addressThin.getAsAddressList(associator);
        return lsResponse;
    }

    @ApiOperation(value = "修改会员收货地址", notes = "修改会员收货地址", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/updateAsAddres", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateAsAddres(@RequestBody AssociatorAddress address, HttpServletRequest request) {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse = addressThin.updateAsAddres(associator,address);
        return lsResponse;
    }

    @ApiOperation(value = "获取会员默认地址", notes = "获取会员默认地址", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/checked", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getChecked(HttpServletRequest request) {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse = addressThin.getChecked(associator);
        return lsResponse;
    }

    @ApiOperation(value = "设置会员默认收货地址", notes = "设置会员默认收货地址", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/updateChecked", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse updateChecked(@RequestBody AssociatorAddress address, HttpServletRequest request) {
        Associator associator=(Associator)request.getAttribute("associator");
        LsResponse lsResponse = addressThin.updateChecked(address,associator);
        return lsResponse;
    }
    @ApiOperation(value = "删除会员说货地址", notes = "删除会员说货地址", nickname = "liuhao", httpMethod = "DELETE")
    @RequestMapping(value = "/deleteAsAddress/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteAsAddress(@PathVariable(value = "id")Integer id) {
        LsResponse lsResponse = addressThin.deleteAsAddress(id);
        return lsResponse;
    }



}
