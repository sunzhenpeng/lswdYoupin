package com.lswd.youpin.lsy;

import com.lswd.youpin.model.lsy.Position;
import com.lswd.youpin.response.LsResponse;

public interface PositionService {
    LsResponse getPositionList(String keyword, Integer pageNum, Integer pageSize);

    LsResponse getPositionById(Integer id);

    LsResponse addOrUpdatePosition(Position employees);

    LsResponse delPosition(Integer id);

}
