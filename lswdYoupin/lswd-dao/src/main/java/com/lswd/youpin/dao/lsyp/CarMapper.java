package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liuhao on 2017/6/19.
 */
public interface CarMapper {
    int insertCar(Car car);
    int updateById(Car car);
    List<Car> selectByAssociatorId(@Param(value = "associator") Associator associator,@Param(value = "pageSize") Integer pageSize, @Param(value = "offSet") Integer offSet);
    int getCarCount(Associator associator);
    Car selectCarByGid(Car car);
    int deleteById(Car car);

}
