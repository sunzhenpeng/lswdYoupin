package com.lswd.youpin.web.controller;

import com.lswd.youpin.Thin.CarThin;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.Car;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liuhao on 2017/6/19.
 */
@Api(value = "car", tags = "car", description = "会员购物车")
@Controller
@RequestMapping(value = "/car")
public class CarController {

    @Autowired
    private CarService carService;
    @Autowired
    private CarThin carThin;

    @ApiOperation(value = "向购物车中添加商品", notes = "向购物车中添加商品", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse add(@RequestBody Car car, HttpServletRequest request)
    {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = carThin.add(car,associator);
        return lsResponse;
    }
    @ApiOperation(value = "删除购物车中的商品", notes = "删除购物车中的商品", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse delete(@RequestBody List<Car> cars, HttpServletRequest request)
    {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse = carThin.deleteCar(cars, associator);
        return lsResponse;
    }
    @ApiOperation(value = "获取购物车商品列表", notes = "获取购物车商品列表", nickname = "liuhao", httpMethod = "GET")
    @RequestMapping(value = "/carList", method = RequestMethod.GET)
    @ResponseBody
    public LsResponse getCarList(@ApiParam(value = "pageNum", required = true) @RequestParam("pageNum") Integer pageNum,
                                 @ApiParam(value = "pageSize", required = true) @RequestParam("pageSize") Integer pageSize,
                                 HttpServletRequest request)
    {
        Associator associator=(Associator) request.getAttribute("associator");
        LsResponse lsResponse = carThin.getCarList(associator,pageNum,pageSize);
        return  lsResponse;
    }
    @ApiOperation(value = "购物车下单详情", notes = "购物车下单详情", nickname = "liuhao", httpMethod = "POST")
    @RequestMapping(value = "/seeCarOrder", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse seeCarOrder(@RequestBody List<Car> cars, HttpServletRequest request)
    {
        Associator associator = (Associator) request.getAttribute("associator");
        LsResponse lsResponse=carThin.seeCarOrder(cars,associator);
        return  lsResponse;
    }


}
