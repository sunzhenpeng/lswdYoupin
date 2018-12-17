package com.lswd.youpin.web.utils;

import com.lswd.youpin.lsy.MachineService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

/**
 * Created by szp on 2018/9/4.
 */
public class ParamHandle {

    @Autowired
    private MachineService machineService;

    public static String  restaurantIdHandle(String machineNo, String restaurantId) {

        if (restaurantId != null && !(restaurantId.equals(""))) {
            // String tmp = URLDecoder.decode(keyword, "UTF-8");
            try {
                restaurantId = new String(restaurantId.getBytes("iso8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
         //machineNo = machineMapper.getMachineNoByCanteenId(restaurantId);
        }
        return machineNo;
    }
}
