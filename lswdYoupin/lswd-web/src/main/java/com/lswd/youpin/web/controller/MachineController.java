package com.lswd.youpin.web.controller;
import com.lswd.youpin.lsy.MachineService;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Machine;
import com.lswd.youpin.response.LsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by sunzhenpeng on 20180612.
 */
@Api(value = "machine", tags = "machine", description = "设备管理")
@Controller
@RequestMapping(value = "/machine")
public class MachineController {

    @Autowired
    MachineService machineService;

    @ApiOperation(value = "设备列表", notes = "设备列表", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getMachineList", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getMachineList(@ApiParam(value = "canteenId") @RequestParam(value = "canteenId", required = false) String canteenId,
                                     @ApiParam(value = "keyword") @RequestParam(value = "keyword", required = false) String keyword,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
        User u = (User) request.getAttribute("user");
        //User u = new User(8,"LSCTadmin","eadf534549bff575e43ccd8aba172f6f",1,"LSCT100026,LSCT100030,LSCT100031","LSCT1001");
        return machineService.getMachineList(u,canteenId,keyword);
    }

   /* @ApiOperation(value = "设备详情", notes = "设备详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getMachineById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getMachineById(@ApiParam(value = "id") @RequestParam(value = "id", required = true) Integer id,
                                    HttpServletRequest request) throws UnsupportedEncodingException {
        return machineService.getMachineById(id);
    }
*/
    @ApiOperation(value = "设备详情", notes = "设备详情", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getMachineById", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getMachineById(@ApiParam(value = "id") @RequestParam(value = "id", required = false) Integer id,
                                     HttpServletRequest request) throws UnsupportedEncodingException {
        return machineService.getMachineById(id);
    }

    @ApiOperation(value = "设备是否可用", notes = "设备是否可用", nickname = "szp", httpMethod = "GET")
    @RequestMapping(value = "/getUseByMachineNo", method = RequestMethod.GET,produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public LsResponse getUseByMachineNo(@ApiParam(value = "id") @RequestParam(value = "machineNo", required = false) String machineNo,
                                        HttpServletRequest request) throws UnsupportedEncodingException {
        return machineService.getUseByMachineNo(machineNo);
    }

    @ApiOperation(value = "新建部门", notes = "新建部门", nickname = "szp", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdateMachine", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addOrUpdateMachine(@RequestBody Machine machine, HttpServletRequest request) {
        LsResponse lsResponse = null;
        try {
            lsResponse = machineService.addOrUpdateMachine(machine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }


    @ApiOperation(value = "删除设备", notes = "删除设备", nickname = "szp", httpMethod = "DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public LsResponse deleteMachine(@PathVariable Integer id, HttpServletRequest request) {
        return machineService.delMachine(id);
    }

/*
    @ApiOperation(value = "新建或者修改设备", notes = "新建或者修改设备", nickname = "lrl", httpMethod = "POST")
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public LsResponse addMachine(@RequestBody Machine machine, HttpServletRequest request) {
        User u=(User)request.getAttribute("user");
        return machineService.addOrUpdateMachine(machine,u);

    }*/






}