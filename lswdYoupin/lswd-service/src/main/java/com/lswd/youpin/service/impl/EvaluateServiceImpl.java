package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.EvaluateMapper;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.lsyp.Evaluate;
import com.lswd.youpin.model.lsyp.EvaluateAdd;
import com.lswd.youpin.model.lsyp.EvaluateImg;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.EvaluateService;
import com.lswd.youpin.service.GoodOrderService;
import com.lswd.youpin.service.RecipeOrderService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhao on 2017/7/1.
 */
@Service
public class EvaluateServiceImpl implements EvaluateService{
    private final Logger log = LoggerFactory.getLogger(EvaluateServiceImpl.class);
    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    private GoodOrderService goodOrderService;
    @Autowired
    private RecipeOrderService recipeOrderService;

    @Override
    public LsResponse addEvaluate(EvaluateAdd evaluateAdd , Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if(evaluateAdd!=null)
            {
                List<Evaluate>evaluates=evaluateAdd.getEvaluates();
                Boolean type=evaluateAdd.getType();
                for(Evaluate e:evaluates)
                {
                    Evaluate evaluate=e;
                    if(!type)
                    {
                        evaluate.setAssociatorName(associator.getAccount());
                    }else{
                        evaluate.setAssociatorName("游客");
                    }
                    evaluate.setAssociatorId(associator.getAssociatorId());
                    evaluate.setCreateTime(Dates.now());
                    evaluate.setUpdateTime(Dates.now());
                    evaluate.setDelete(false);
                    String goodOrRecipeId = evaluate.getGoodOrRecipeId();
                    if(goodOrRecipeId!=null&&!"".equals(goodOrRecipeId))
                    {
                        if("".equals(evaluate.getContent())||evaluate.getContent()==null)
                        {
                            evaluate.setContent("默认好评");
                        }
                        if ('g' == goodOrRecipeId.charAt(0)) {
                            Integer b = evaluateMapper.insertGoodEvaluate(evaluate);
                            if (b != null && b > 0) {
                                List<EvaluateImg> evaluateImgs = evaluate.getEvaluateImgs();
                                if(evaluateImgs!=null&&evaluateImgs.size()>0)
                                {
                                  evaluateMapper.addGoodImage(evaluateImgs, evaluate.getId());
                                }
                            }
                        } else {
                            Integer b = evaluateMapper.insertRecipeEvaluate(evaluate);
                            if (b != null && b> 0) {
                                List<EvaluateImg> evaluateImgs = evaluate.getEvaluateImgs();
                                if(evaluateImgs!=null&&evaluateImgs.size()>0){
                                evaluateMapper.addRecipeImage(evaluateImgs, evaluate.getId());
                                }
                            }
                        }
                    }
                }
                if(evaluateAdd.getEvaluates()!=null&&evaluateAdd.getEvaluates().size()>0)
                {
                   char goodOrRecipeId= evaluateAdd.getEvaluates().get(0).getGoodOrRecipeId().charAt(0);
                    if('g'==goodOrRecipeId)
                    {
                        goodOrderService.updateStatus(evaluateAdd.getOrderId());
                    }else{
                        recipeOrderService.updateStatus(evaluateAdd.getOrderId());
                    }
                }
            }else{
                lsResponse.setMessage("参数错误");
                lsResponse.setSuccess(false);
            }
    } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return lsResponse;
}

    @Override
    public LsResponse getEvaluateList(String evaluateId, Integer pageNum,Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        if (evaluateId != null && !"".equals(evaluateId)) {
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            try {
                if ('g' ==evaluateId.charAt(0)) {
                    List<Evaluate> evaluateList = evaluateMapper.selcetEvaluateGoodList(evaluateId, pageSize, offSet);
                    if (evaluateList != null && evaluateList.size() > 0) {
                        Integer total = evaluateMapper.selectEvaluateGoodCount(evaluateId);
                        List<Evaluate> evaluates=new ArrayList<>();
                        Map<String,Object> map=new HashedMap();
                        for(int i=0;i<evaluateList.size();i++)
                        {
                            Evaluate evaluate=evaluateList.get(i);
                            evaluate.setTx("https://web.lsypct.com/images/tx/20170912164804.jpg");
                            evaluates.add(evaluate);
                        }
                        map.put("evaluateType","商品评价");
                        map.put("evaluates",evaluates);
                        lsResponse.setData(map);
                        lsResponse.setTotalCount(total);
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.EVALUATE_NO_VIEW.name());
                    }
                } else if ('r' == evaluateId.charAt(0)) {

                    List<Evaluate> evaluateList = evaluateMapper.selcetEvaluateRecipeList(evaluateId, pageSize, offSet);
                    if (evaluateList != null && evaluateList.size() > 0) {
                        Integer total = evaluateMapper.selectEvaluateRecipeCount(evaluateId);
                        List<Evaluate> evaluates=new ArrayList<>();
                        Map<String,Object> map=new HashedMap();
                        for(int i=0;i<evaluateList.size();i++)
                        {
                            Evaluate evaluate=evaluateList.get(i);
                            evaluate.setTx("https://web.lsypct.com/images/7cbe740247efec1ebae6e25a05afa4fc.jpg");
                            evaluates.add(evaluate);
                        }
                        map.put("evaluateType","菜品评价");
                        map.put("evaluates",evaluates);
                        lsResponse.setData(map);
                        lsResponse.setTotalCount(total);
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.EVALUATE_NO_VIEW.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.EVALUATE_CAN_ERR.name());
                }
            } catch (Exception e) {
                lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
                log.error(e.toString());
            }
        }
        return lsResponse;
    }

    @Override
    public LsResponse additional(Evaluate evaluate) {
        LsResponse lsResponse=new LsResponse();
        if(evaluate!=null&&!"".equals(evaluate)){
            evaluate.setUpdateTime(Dates.now());
            try {
                if ('g' == evaluate.getGoodOrRecipeId().charAt(0)) {

                    Integer b=evaluateMapper.updateByGoodId(evaluate);
                    if(b!=null&&b>0)
                    {
                        lsResponse.setMessage("修改成功");
                    }

                } else if ('r' == evaluate.getGoodOrRecipeId().charAt(0)) {
                    Integer b=evaluateMapper.updateByRecipeId(evaluate);
                    if(b!=null&&b>0)
                    {
                        lsResponse.setMessage("修改成功");
                    }
                }
            } catch (Exception e) {
                lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
                log.error(e.toString());
            }

        }
        return lsResponse;
    }
}
