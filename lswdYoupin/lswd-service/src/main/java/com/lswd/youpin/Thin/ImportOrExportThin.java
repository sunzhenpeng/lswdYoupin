package com.lswd.youpin.Thin;


import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhenguanqi on 2017/8/8.
 */
public interface ImportOrExportThin {

    LsResponse exportExeclList(User user,Integer flag,HttpServletResponse response);//该方法主要是用来 根据餐厅编号查询餐厅，获取该餐厅对应的regionId
}
