package com.lswd.youpin.dao.lsyp;

import com.lswd.youpin.model.lsyp.Evaluate;
import com.lswd.youpin.model.lsyp.EvaluateImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liuhao on 2017/7/1.
 */
public interface EvaluateMapper {
    Integer insertGoodEvaluate(Evaluate evaluate);

    Integer insertRecipeEvaluate(Evaluate evaluate);


    List<Evaluate> selcetEvaluateGoodList(@Param(value = "evaluateId") String evaluateId, @Param(value = "pageSize") Integer pageSize,
                                          @Param(value = "offSet") int offSet);

    Integer selectEvaluateGoodCount(@Param(value = "evaluateId") String evaluateId);

    List<Evaluate> selcetEvaluateRecipeList(@Param(value = "evaluateId") String evaluateId, @Param(value = "pageSize") Integer pageSize,
                                            @Param(value = "offSet") int offSet);

    Integer selectEvaluateRecipeCount(@Param(value = "evaluateId") String evaluateId);

    Integer updateByGoodId(Evaluate evaluate);

    Integer updateByRecipeId(Evaluate evaluate);

    Integer addGoodImage(@Param(value = "evaluateImgs") List<EvaluateImg> evaluateImgs,
                         @Param(value = "evaluateId") Integer evaluateId);

    Integer addRecipeImage(@Param(value = "evaluateImgs") List<EvaluateImg> evaluateImgs,
                           @Param(value = "evaluateId") Integer evaluateId);

}
