package com.lswd.youpin.quartz;

import com.lswd.youpin.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhenguanqi on 2018/2/11.
 */
public class PersonInfoQuartz {

    //public static final String CANTEENID = "LSCT100026";
//    public static final String MEMBERERROR = "TRUNCATE会员失败";
//    public static final String CARDERROR = "TRUNCATE会员餐卡失败";
//    public static final String PERSONINFONULL = "未获取到personInfo信息";
//    public static final String CARDINSERTERROR = "插入会员餐卡失败";
//    public static final String MEMBERINSERTERROR = "插入会员失败";

    @Autowired
    private PersonInfoService personInfoService;


    public void synPersonInfo() {
       //personInfoService.synPersonInfo();
    }
}
