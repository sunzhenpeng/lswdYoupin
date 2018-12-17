package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.lsyp.Car;
import com.lswd.youpin.response.LsResponse;

import java.util.List;

/**
 * Created by liuhao on 2017/6/19.
 */
public interface CarService {
    LsResponse add(Car car,String endTime,Associator associator);
    LsResponse update(Car car);

    LsResponse getCarList(Associator associator,Integer pageNum,Integer pageSize,String endTime);

    LsResponse removeAll(Associator associator);

    LsResponse deleteCar(List<Car> cars, Associator associator,Integer time);

    LsResponse seeCarOrder(List<Car> cars);

    List<List<Car>> getCarsByType(List<Car> cares, Canteen canteen);
}
