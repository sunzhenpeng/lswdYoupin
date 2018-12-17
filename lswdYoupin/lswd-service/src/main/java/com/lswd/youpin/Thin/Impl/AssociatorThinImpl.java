package com.lswd.youpin.Thin.Impl;

import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.Thin.AssociatorThin;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.AssociatorCanteen;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.Employee;
import com.lswd.youpin.model.lsyp.Members;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.AssociatorService;
import com.lswd.youpin.service.CanteenService;
import com.lswd.youpin.service.EmployeeService;
import com.lswd.youpin.service.MembersService;
import com.lswd.youpin.shiro.jedis.RedisManager;
import com.lswd.youpin.utils.DataSourceConst;
import com.lswd.youpin.utils.DataSourceHandle;
import com.lswd.youpin.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liuhao on 2017/6/10.
 */
@Component
public class AssociatorThinImpl implements AssociatorThin {
    private final Logger log = LoggerFactory.getLogger(AssociatorThinImpl.class);
    @Autowired
    private AssociatorService associatorService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private CanteenService canteenService;

    @Autowired
    private MembersService membersService;

    @Override
    public LsResponse lock(Integer id, Boolean lock) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.lock(id, lock);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteAssociator(Integer id) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.deleteAssociator(id);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateAssociator(Associator associator) {

        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.updateAssociator(associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAssociatorList(String keyword, Integer pageNum, Integer pageSize, User user, String canteenId) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.getAssociatorList(keyword, pageNum, pageSize, user, canteenId);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse canteenList(String associatorId, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.canteenList(associatorId, user);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse lookAssociator(Integer id) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.lookAssociator(id);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse bindCanteen(String data, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (data != null && !"".equals(data)) {
                JSONObject jsonObject = JSONObject.parseObject(data);
                String code = jsonObject.get("code").toString();
                String tel = jsonObject.get("tel").toString();
                String username = jsonObject.get("username").toString();
                String cardNumber = jsonObject.get("cardNumber").toString();
                String canteenId = associator.getCanteenId();
                String smsCode = "";
                if (redisManager.get(tel.getBytes()) != null) {
                    smsCode = new String(redisManager.get(tel.getBytes())).split(",")[0];
                }
                if (code.equals(smsCode)) {//判断短信验证码是否正确
                    String associatorId = associator.getAssociatorId();
                    Employee employee = employeeService.getEmployeeByCardId(cardNumber, username);
                    if (employee != null) {
//                        if (tel.equals(employee.getTelephone())) {
                        if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                        }
                        lsResponse = associatorService.bindCanteen(associatorId, canteenId);
//                        } else {
//                            lsResponse.checkSuccess(false,CodeMessage.ASSOCIATOR_BIND_PHONE.name());
//                        }
                    } else {
                        lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_CARD.name());
                    }
                } else {
                    lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_SMSCODE.name());
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse pay(String associatorId, String keyword, Integer pageSize, Integer pageNum) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.getPay(associatorId, keyword, pageSize, pageNum);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse seeAssociator(Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.getAssociatorById(associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }

        return lsResponse;
    }

    @Override
    public LsResponse updateLoginPwd(String data, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.updateLoginPwd(data, associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updatePayPwd(String data, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.updatePayPwd(data, associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updatePhone(String data, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            String dataSource = DataSourceHandle.getDataSourceType();
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.updatePhone(data, associator);
            if (lsResponse.getSuccess()) {
                DataSourceHandle.setDataSourceType(dataSource);
                employeeService.updateTelphone(data, associator);
            }

        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMoney(Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.getMoney(associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse removeCanteen(String canteenId, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            if (!DataSourceConst.LSWD.equals(DataSourceHandle.getDataSourceType())) {
                DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            }
            lsResponse = associatorService.deleteCanteen(canteenId, associator);
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse isBindCanteen(String canteenId, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            Canteen canteen = (Canteen) canteenService.getCanteenByCanteenId(canteenId).getData();
            if (canteen != null) {
                lsResponse = canteenService.getAssociatorCanteenList(associator.getAssociatorId());
                boolean flag = false;
                if (lsResponse.getSuccess()) {
                    if (lsResponse.getData() != null) {
                        flag = isBind(canteen, (List<Canteen>) lsResponse.getData());
                    }
                }
                if (!flag) {
                    if (canteen.getCanteenType() == 1) {
                        lsResponse = associatorService.bindCanteen(associator.getAssociatorId(), canteenId);
                    } else {
                        lsResponse.setData(1);
                    }
                } else {
                    AssociatorCanteen associatorCanteen = new AssociatorCanteen();
                    associatorCanteen.setBindTime(Dates.now());
                    associatorCanteen.setCanteenId(canteenId);
                    associatorCanteen.setAssociatorId(associator.getAssociatorId());
                    associatorService.updateBindCanteen(associatorCanteen);
                    lsResponse.setData("");
                }
            } else {
                lsResponse.checkSuccess(false, CodeMessage.CANTEEN_NO_ERR.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    private boolean isBind(Canteen canteen, List<Canteen> canteenList) {
        boolean flag = false;
        for (Canteen can : canteenList) {
            if (can.getCanteenId().equals(canteen.getCanteenId())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public LsResponse addPhone(String tel, Associator associator) {
        LsResponse lsResponse = new LsResponse();
        try {
            DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
            lsResponse = associatorService.addPhone(tel, associator);
        } catch (Exception e) {
            log.error(e.toString());
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse bindCard(String data, HttpServletRequest request) {
        LsResponse lsResponse = new LsResponse();
        Associator associator = (Associator) request.getAttribute("associator");
        JSONObject jsonObject = JSONObject.parseObject(data);
        String token = request.getHeader("token");
        String telephone = String.valueOf(jsonObject.get("telephone"));
        String phoneCode = String.valueOf(jsonObject.get("code"));
        String username= String.valueOf(jsonObject.get("username"));
        String message = "";
        boolean flag = true;
        try {
            //判断手机验证码是否正确
            String codes = new String(redisManager.get(telephone.getBytes()));
            if (codes != null) {
                String code = codes.split(",")[0];
                //判断手机号验证码正确
                if (phoneCode.equals(code)) {
                    //根据手机号码检索开卡人信息
                    Members member = membersService.getMemberByPhone(telephone,username);
                    if (member != null) {
                        associator.setMemberId(member.getMemberId());
                        DataSourceHandle.setDataSourceType(DataSourceConst.LSWD);
                        associatorService.updateById(associator.getId(), member.getMemberId());
                        redisManager.set(token.getBytes(), SerializeUtils.serialize(associator));
                    } else {
                        message = "该会员不存在，请到服务台办卡";
                        flag = false;
                    }
                } else {
                    message = "手机验证码错误，请重新发送";
                    flag = false;
                }
            } else {
                message = "手机验证码不能为空";
                flag = false;
            }
            lsResponse.setMessage(message);
            lsResponse.setSuccess(flag);
        } catch (Exception e) {
            lsResponse.setMessage("绑卡失败");
            lsResponse.setAsFailure();
        }
        return lsResponse;
    }

}
