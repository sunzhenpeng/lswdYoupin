package com.lswd.youpin.service;

import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.Evaluate;
import com.lswd.youpin.model.lsyp.EvaluateAdd;
import com.lswd.youpin.response.LsResponse;

/**
 * Created by liuhao on 2017/7/1.
 */
public interface EvaluateService {
    LsResponse addEvaluate(EvaluateAdd evaluateAdd , Associator associator);

    LsResponse getEvaluateList(String evaluateId,Integer pageNum,Integer pageSize);

    LsResponse additional(Evaluate evaluate);


}
