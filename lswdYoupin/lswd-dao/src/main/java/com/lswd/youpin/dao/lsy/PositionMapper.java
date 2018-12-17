package com.lswd.youpin.dao.lsy;

import com.lswd.youpin.dao.lsygen.PositionMapperGen;
import com.lswd.youpin.model.lsy.Position;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PositionMapper extends PositionMapperGen {


    List<Position> getPositionList(@Param(value = "keyword") String keyword,
                                   @Param(value = "pageSize") Integer pageSize,
                                   @Param(value = "offset") Integer offset);


    List<Position> getAllPosition();

    Integer getPositionCount(@Param(value = "keyword") String keyword);


    Integer delPosition(@Param(value = "id") Integer id);




}