package com.lswd.youpin.Thin.Impl;

import com.lswd.youpin.Thin.CarThin;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.lsyp.Car;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.CarService;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by liuhao on 2017/8/4.
 */
@Component
public class CarThinImpl implements CarThin {
    private final Logger log = LoggerFactory.getLogger(CarThinImpl.class);
    @Autowired
    private CanteenService canteenService;
    @Autowired
    private CarService carService;
    @Override
    public LsResponse seeCarOrder(List<Car> cars,Associator a) {
        LsResponse lsResponse=new LsResponse();
        Map<String,Object>map=new HashedMap();
        Associator associator=new Associator();
        associator.setAccount(a.getAccount());
        associator.setTelephone(a.getTelephone());
        map.put("associator",a);
        try {
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            if (cars != null && cars.size() > 0) {
                List<List<Car>> carList = (List<List<Car>>) carService.seeCarOrder(cars).getData();//按取货日期分
                List<Map<String, Object>> carOrders = new ArrayList<>();
                Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(a.getCanteenId()).getData();
                for (int i = 0; i < carList.size(); i++) {
                    List<Car> cares = carList.get(i);
                    List<List<Car>> carsByType = carService.getCarsByType(cares,canteen);//按照累型进行分类，商品、早餐、中餐、晚餐
                    for (int j = 0; j < carsByType.size(); j++) {

                        Map<String, Object> carOrder = new HashedMap();
                        carOrder.put("address", canteen.getAddress());
                        carOrder.put("pickTime", cares.get(0).getPickTime());
                        carOrder.put("commodity", carsByType.get(j));
                        carOrders.add(carOrder);
                    }
                }
                lsResponse.setData(map);
                map.put("orders", carOrders);
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }


    @Override
    public LsResponse add(Car car, Associator associator) {
        LsResponse lsResponse=new LsResponse();
        try {
            String dataScore=DataSourceHandle.getDataSourceType();
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            Canteen canteen=(Canteen)canteenService.getCanteenByCanteenId(associator.getCanteenId()).getData();
            String endTime=canteen.getDeadline();
            DataSourceHandle.setDataSourceType(dataScore);
            car.setAssociatorId(associator.getAssociatorId());
            lsResponse=carService.add(car,endTime,associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }

        return lsResponse;
    }

    @Override
    public LsResponse getCarList(Associator associator, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse=new LsResponse();
        String dataScore= null;
        try {
            dataScore = DataSourceHandle.getDataSourceType();
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            Canteen canteen=(Canteen)canteenService.getCanteenByCanteenId(associator.getCanteenId()).getData();
            String endTime=canteen.getDeadline();
            DataSourceHandle.setDataSourceType(dataScore);
            lsResponse=carService.getCarList(associator,pageNum,pageSize,endTime);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        DataSourceHandle.setDataSourceType(dataScore);
        return lsResponse;
    }

    @Override
    public LsResponse deleteCar(List<Car> cars, Associator associator) {
        LsResponse lsResponse=new LsResponse();
        try {
            String  dataScore = DataSourceHandle.getDataSourceType();
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            Canteen canteen=(Canteen)canteenService.getCanteenByCanteenId(associator.getCanteenId()).getData();
            String endTime=canteen.getDeadline();
            DataSourceHandle.setDataSourceType(dataScore);
            String[] times = endTime.split(":");
            Integer time = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
            lsResponse=carService.deleteCar(cars,associator,time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsResponse;
    }
}
