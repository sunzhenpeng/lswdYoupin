package com.lswd.youpin.utils;

/**
 * Created by liruilong on 2017/8/3.
 */
public class DataSourceSwitch {

    public static void dbSwitch(String url, String tenantId) {
        String[] urls = url.split("\\/");
        String prefix = urls[2];
        if (prefix.equals("role") || prefix.equals("user") || prefix.equals("canteen") || prefix.equals("resource")
                || prefix.equals("tenant") || prefix.equals("associator") || prefix.equals("weixin") || prefix.equals("account") || prefix.equals("counterUser")||prefix.equals("machine")||prefix.equals("appResource")||prefix.equals("img")||prefix.equals("resType")||prefix.equals("banner")||prefix.equals("video")||prefix.equals("pdf")||prefix.equals("page")||prefix.equals("pageImg")||prefix.equals("position")||prefix.equals("employees")) {
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        } else {
            String tenantCode = tenantId.substring(0, 4);
            DataSourceHandle.setDataSourceType(tenantCode);
        }
    }
}
