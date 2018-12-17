package com.lswd.youpin.quartz;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.Tenant;
import com.lswd.youpin.model.lsyp.GoodOrder;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.*;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Created by liuhao on 2017/7/3.
 */
public class OrderQuartz {

    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private AssociatorService associatorService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private CanteenService canteenService;

    public void doSomething() {
        System.out.println("-------------------");
        if(!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType()))
        {
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
        }
        List<Tenant> tenants= tenantService.getTenantAll();
        try {
            if(tenants!=null&&tenants.size()>0)
            {
                for(Tenant tenant:tenants)
                {
                    System.out.println(tenant.getTenantId().substring(0,4)+"*************");
                    DataSourceHandle.setDataSourceType(tenant.getTenantId().substring(0,4));
                    List<GoodOrder> goodOrderList = goodOrderService.selectGoodPaidAllList(Dates.startOfDay(Dates.now()));
                    System.out.println(goodOrderList.size()+"-----------------");
                    if(goodOrderList!=null&&goodOrderList.size()>0)
                    {
                       goodOrder(goodOrderList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goodOrder(List<GoodOrder> goodOrderList) {
        if (goodOrderList != null && goodOrderList.size() > 0) {
            for (int i = 0; i < goodOrderList.size(); i++) {
                GoodOrder goodOrder = goodOrderList.get(i);
                String associatorId=goodOrder.getAssociatorId();
                try {
                    DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                    Associator associator= (Associator) associatorService.getAssociatorByAssociatorId(associatorId).getData();
                    LsResponse ls= canteenService.getCanteenByCanteenId(goodOrder.getCanteenId());
                    if(ls.getSuccess()&&ls.getData()!=null)
                    {
                        Canteen canteen=(Canteen) ls.getData();
                        if(canteen.getWarning()) {
                            System.out.println("发送短信提醒用户取货" + canteen.getPickingTime() + "--" + associator.getTelephone() + "---" + canteen.getAddress() + "--" + goodOrder.getPickingTime());
                            smsService.sendTzMsg(associator.getTelephone(),goodOrder.getOrderId(),canteen.getPickingTime(),canteen.getAddress());
                        }
                    }

//                    if (Dates.timeInterval(Dates.now(), goodOrder.getPickingTime()) < 43200 &&
//                            Dates.timeInterval(Dates.now(), goodOrder.getPickingTime()) > 0&&canteen.getWarning()) {
//
//                        System.out.println("发送短信提醒用户取货"+  canteen.getPickingTime()+"--"+associator.getTelephone()+"---"+canteen.getAddress());
//
//                        //smsService.sendTzMsg(associator.getTelephone(),goodOrder.getOrderId(),canteen.getPickingTime(),canteen.getAddress());
//
//                        //发送短信提醒用户取货，
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                else if (Dates.timeInterval(goodOrder.getPickingTime(), Dates.now()) > 86400 &&
//                        Dates.timeInterval(goodOrder.getPickingTime(), Dates.now()) < 259200) {
//                        System.out.println("发送短信通知用户货已超期，请取货");
//                       // smsService.sendTzMsg(associator.getTelephone(),goodOrder.getOrderId(),1,canteen.getAddress());
//                       //发送短信通知用户货已超期，请取货
//                } else if (Dates.timeInterval(goodOrder.getPickingTime(), Dates.now()) > 259200) {
//                    System.out.println("发送短信通知用户你长时间未取货，订单已经完成");
//                    System.out.println(associator.getTelephone()+"---"+goodOrder.getOrderId()+"----"+canteen.getAddress());
//                    LsResponse lsResponse= smsService.sendTzMsg(associator.getTelephone(),goodOrder.getOrderId(),2,canteen.getAddress());
//                    System.out.println(lsResponse.getMessage());
//                    System.out.println(lsResponse.getErrorCode());
//                    break;
////                    goodOrderService.applyRefundGoodOrderH5(goodOrder.getOrderId());
//                    //将订单变为已完成
//                    //发送短信通知用户你长时间未取货，订单已经完成
//                }

            }
        }
    }

//    public static  void main(String [] args)
//    {
//        System.out.println(ShiroKit.md5("123456","LSYPzgq"));
//    }


}
