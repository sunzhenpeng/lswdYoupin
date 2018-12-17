package com.lswd.youpin.Thin;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.Car;
import com.lswd.youpin.response.LsResponse;

import java.util.List;

/**
 * Created by liuhao on 2017/8/4.
 */
public interface CarThin {
    LsResponse seeCarOrder(List<Car> cars, Associator associator);

    LsResponse add(Car car, Associator associator);

    LsResponse getCarList(Associator associator, Integer pageNum, Integer pageSize);

    LsResponse deleteCar(List<Car> cars, Associator associator);
}
