package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.commons.RecipeType;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.lsyp.Car;
import com.lswd.youpin.model.lsyp.Good;
import com.lswd.youpin.model.lsyp.Recipe;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CarService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuhao on 2017/6/19.
 */
@Service
public class CarServiceImpl implements CarService {
    private final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private GoodPlanMapper goodPlanMapper;
    @Autowired
    private RecipePlanMapper recipePlanMapper;
    @Autowired
    private RedisManager redisManager;

    @Override
    public LsResponse add(Car car, String endTime,Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (car != null) {
                if (car.getPickTime() == null||car.getPickTime().equals("")) {
                    String date = Dates.now("yyyy-MM-dd");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    ParsePosition pos = new ParsePosition(0);
                    car.setPickTime(formatter.parse(date, pos));
                }
                String[] times = endTime.split(":");
                Integer time = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
                if (!Dates.isAfter(Dates.now(), Dates.addMinutes(Dates.startOfDay(car.getPickTime()), time))) {
                    int num;
                    Boolean goodType = false;
                    if (car.getGoodId().startsWith("g")) {
                        car.setType(false);
                        num = goodPlanMapper.getGoodPlanCount(car.getPlanId(), car.getGoodId());
                        goodType = goodPlanMapper.getGoodFlag(car.getPlanId(), car.getGoodId());
                    } else {
                        car.setType(true);
                        num = recipePlanMapper.getPlanCountRecipe(car.getGoodId(), car.getRecipeType() - 1, car.getPlanId());
                    }
                    Car c = carMapper.selectCarByGid(car);
                        if(!goodType) {
                            if (c == null) {
                                lsResponse = addCar(car, num);
                            } else {
                                c.setUpdateTime(Dates.now());
                                if (car.getNumber() == 0) {
                                    lsResponse = delCar(c);
                                } else {
                                    lsResponse = updateCar(c,car, num);
                                }
                            }
                        }else{
                            String key=associator.getTelephone()+associator.getCanteenId()+Dates.addMinutes(Dates.startOfDay(car.getPickTime()), time);
                            byte []bytes=redisManager.get(key.getBytes());
                            if (bytes != null) {
                                if (car.getNumber() > 0) {
                                    lsResponse.checkSuccess(false, CodeMessage.CAR_ADD_ERR_COUNT.name());
                                    lsResponse.setData("");
                                } else {
                                    redisManager.del(key.getBytes());
                                    lsResponse.checkSuccess(true, CodeMessage.CAR_DELETE_COUNT.name());
                                    delCar(c);
                                }
                            } else {
                                if (car.getNumber() == 1) {
                                    if (num > 0) {
                                        int outTime = (int) Dates.timeInterval(Dates.now(), Dates.addMinutes(Dates.startOfDay(car.getPickTime()), time));
                                        redisManager.set(key.getBytes(), "ok".getBytes(), outTime);
                                        lsResponse = addCar(car, 1);
                                    } else {
                                        lsResponse.checkSuccess(false, CodeMessage.CAR_NO_COUNT.name());
                                        lsResponse.setData(num);
                                    }
                                } else {
                                    lsResponse.checkSuccess(false, CodeMessage.CAR_ADD_ERR_COUNT.name());
                                    lsResponse.setData("");
                                }
                            }
                        }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.CAR_TIME_OUT.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.CAR_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    private LsResponse updateCar(Car c,Car car,Integer num) {
        LsResponse lsResponse=new LsResponse();
        if (num >= car.getNumber() && car.getNumber() > 0 && num > 0) {
            c.setNumber(car.getNumber());
            Integer b = carMapper.updateById(c);
            if (b != null && b > 0) {
                lsResponse.setMessage(CodeMessage.CAR_ADD_SUCCESS.name());
                lsResponse.setData(car.getNumber());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.CAR_ADD_ERR.name());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.CAR_NO_COUNT.name());
            lsResponse.setData(num);
        }
        return  lsResponse;
    }

    private LsResponse delCar(Car c) {
        LsResponse lsResponse=new LsResponse();
        Integer b = carMapper.deleteById(c);
        if (b != null && b > 0) {
            lsResponse.setMessage(CodeMessage.CAR_ADD_SUCCESS.name());
            lsResponse.setData(0);
        } else {
            lsResponse.checkSuccess(false, CodeMessage.CAR_ADD_ERR.name());
        }
        return lsResponse;
    }

    private LsResponse addCar(Car car,Integer num) {
        LsResponse lsResponse=new LsResponse();
        car.setCreateime(Dates.now());
        car.setUpdateTime(Dates.now());
        car.setDelete(false);
        if (num >= car.getNumber() && num > 0 && car.getNumber() > 0) {
            Integer b = carMapper.insertCar(car);
            if (b != null && b > 0) {
                lsResponse.setMessage(CodeMessage.CAR_ADD_SUCCESS.name());
                lsResponse.setData(car.getNumber());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.CAR_ADD_ERR.name());
            }
        } else {
            lsResponse.checkSuccess(false, CodeMessage.CAR_NO_COUNT.name());
            lsResponse.setData(num);
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCar(List<Car> cars, Associator associator,Integer time) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (cars != null) {
                for (Car car : cars) {
                    Boolean goodType = goodPlanMapper.getGoodFlag(car.getPlanId(), car.getGoodId());
                    if(goodType!=null&&goodType)
                    {
                        String key=associator.getTelephone()+associator.getCanteenId()+Dates.addMinutes(Dates.startOfDay(car.getPickTime()), time);
                        byte[]bytes= redisManager.get(key.getBytes());
                        if(bytes!=null)
                        {
                            redisManager.del(key.getBytes());
                        }
                    }
                    carMapper.deleteById(car);
                }
                lsResponse.setMessage(CodeMessage.CAR_DELETE_SUCCESS.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse update(Car car) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (car != null) {
                car.setUpdateTime(Dates.now());
                Integer b = carMapper.updateById(car);
                if (b!=null&&b > 0) {
                    lsResponse.setMessage(CodeMessage.CAR_UPDATE_SUCCESS.name());
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.CAR_UPDATE_ERR.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.CAR_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCarList(Associator associator, Integer pageNum, Integer pageSize,String endTime) {
        LsResponse lsResponse = new LsResponse();
        try {
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            List<Car> carList = carMapper.selectByAssociatorId(associator, pageSize, offSet);
            List<Car> cars = new ArrayList<>();
            if (carList != null && carList.size() > 0) {
                for (int i = 0; i < carList.size(); i++) {
                    Car car = carList.get(i);
                    String []times=endTime.split(":");
                    Integer time=Integer.parseInt(times[0])*60+Integer.parseInt(times[1]);
                    if (!Dates.isAfter(Dates.now(),Dates.addMinutes(Dates.startOfDay(car.getPickTime()),time))) {
                        if (!car.getType()) {
                            Good g = goodMapper.getGoodByGoodId(car.getGoodId());
                            car.setImg(g.getImageurl());
                            car.setMsg(g.getGoodName() + " 【 " + g.getStandard() + " 】");
                            Float price=goodPlanMapper.getPrice(g.getGoodId(),car.getPlanId());
                            if(price==null) {price=0f;}
                            car.setPrice(String.valueOf(price));
                            car.setText(supplierMapper.selectBySupplierId(g.getSupplierId()).getName() + "直供");
                        } else {
                            Recipe r = recipeMapper.getRecipeByRecipeId(car.getGoodId());
                            car.setImg(r.getImageurl());
                            car.setMsg(r.getRecipeName());
                            car.setPrice(String.valueOf(r.getGuidePrice()));
                            String type;
                            if(car.getRecipeType()!=null)
                            {
                                type=RecipeType.getRecipeType(car.getRecipeType()).getName();
                            }else{
                                type=RecipeType.other.getName();
                            }
                            car.setText(type);
                        }
                        cars.add(car);
                    } else {
                        carMapper.deleteById(car);
                    }
                }
                lsResponse.setData(cars);
                Integer total = carMapper.getCarCount(associator);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.CAR_NO_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse removeAll(Associator associator) {
        LsResponse lsResponse = new LsResponse();

        try {
            Car car = new Car();
            car.setAssociatorId(associator.getAssociatorId());
            car.setUpdateTime(Dates.now());
            car.setDelete(true);
            Integer b = carMapper.deleteById(car);
            if (b!=null&&b > 0) {
                lsResponse.setMessage(CodeMessage.CAR_REMOVE_ALL_SUCCESS.name());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.CAR_REMOVE_ALL_ERR.name());
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse seeCarOrder(List<Car> cars) {
        LsResponse lsResponse = new LsResponse();
        List<List<Car>> carList = new ArrayList<>();
        Set<Date> dateSet = new HashSet<>();
        try {
            //根据取货时间分
            for (int i = 0; i < cars.size(); i++) {
                dateSet.add(cars.get(i).getPickTime());
            }
            for (Date date : dateSet) {
                List<Car> carOrders = new ArrayList<>();
                for (int i = 0; i < cars.size(); i++) {
                    if (date.equals(cars.get(i).getPickTime())) {
                        carOrders.add(cars.get(i));
                    }
                }
                if (carOrders.size() > 0) {
                    carList.add(carOrders);
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        lsResponse.setData(carList);
        return lsResponse;
    }

    @Override
    public List<List<Car>> getCarsByType(List<Car> cares, Canteen canteen) {
        List<List<Car>> carList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<Car> carOrders = new ArrayList<>();
            for (int j = 0; j < cares.size(); j++) {
                Car car = cares.get(j);
                car.setOutFee(canteen.getOutFee());
                if (i == car.getRecipeType()) {
                    carOrders.add(car);
                }
            }
            if (carOrders.size() > 0) {
                carList.add(carOrders);
            }
        }
        return carList;
    }

}
