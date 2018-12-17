package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.CounterUserMapper;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.model.vo.TotalCountMoney;
import com.lswd.youpin.poiutils.ExportUtil;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhenguanqi on 2017/11/15.
 */
@Service
public class CounterServiceImpl implements CounterService {
    private final Logger log = LoggerFactory.getLogger(CounterServiceImpl.class);
    @Autowired
    private CounterMapper counterMapper;
    @Autowired
    protected CounterUserMapper counterUserMapper;
    @Autowired
    private CounterMenuMapper counterMenuMapper;
    @Autowired
    private CounterMenuLinkedMapper counterMenuLinkedMapper;
    @Autowired
    private CounterUserLinkedMapper counterUserLinkedMapper;
    @Autowired
    private MenuTypeMapper menuTypeMapper;
    @Autowired
    private CounterOrderMapper counterOrderMapper;

    @Override
    public LsResponse getCounterList(User user,String keyword,String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] canteenIds = user.getCanteenIds().split(",");
        int total = 0;
        try {
            if (!("").equals(keyword) && keyword != null) {
//                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = new String(keyword.getBytes("utf-8"), "utf-8");
            } else {
                keyword = "";
            }
            if (!("").equals(canteenId) && canteenId != null){
//                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                canteenId = new String(canteenId.getBytes("utf-8"), "utf-8");
            }else {
                canteenId = "";
            }
        } catch (UnsupportedEncodingException e) {
            log.error("查看吧台列表失败，失败原因为：" + e.toString());
            lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        int offSet = 0;
        if (pageSize != null && pageNum != null) {
            offSet = (pageNum - 1) * pageSize;
        }
        try {
            total = counterMapper.getCounterListCount(keyword,canteenId,canteenIds);
            List<Counter> counters = counterMapper.getCounterList(keyword, canteenId,canteenIds,offSet, pageSize);
            if (counters.size() > 0 && counters != null) {
                lsResponse.setTotalCount(total);
                lsResponse.setData(counters);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("查看吧台列表失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterUserLinkedList(String counterId) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<CounterUserLinked> linkeds = counterUserLinkedMapper.isBingCounterByCounterId(counterId);
        if (linkeds != null && linkeds.size() > 0) {
            lsResponse.setData(linkeds);
        }else {
            lsResponse.setData(null);
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterListAll() {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
//            String[] canteenIds = user.getCanteenIds().split(",");
            List<Counter> counters = counterMapper.getCounterListAll();
            if (counters.size() > 0 && counters != null) {
                lsResponse.setData(counters);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("查看吧台所有列表失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCounter(Counter counter, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        counter.setCreateUser(user.getUsername());
        counter.setCreateTime(Dates.now());
        counter.setUpdateUser(user.getUsername());
        counter.setUpdateTime(Dates.now());
        counter.setIsDelete((byte) 0);
        try {
            Integer maxId = counterMapper.getMaxId();
            if (maxId == null) maxId = 0;
            int counterId = maxId + 1001;
            counter.setCounterId("BT" + counterId);
            int insertFlag = counterMapper.insertSelective(counter);
            if (insertFlag > 0) {
                lsResponse.setMessage("吧台新增成功");
            } else {
                lsResponse.checkSuccess(false, "吧台新增失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("吧台新增失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounter(Counter counter, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        counter.setUpdateTime(Dates.now());
        counter.setUpdateUser(user.getUsername());
        try {
            int updateFlag = counterMapper.updateByPrimaryKeySelective(counter);
            if (updateFlag > 0) {
                lsResponse.setMessage("吧台修改成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台修改失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("吧台修改失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounter(Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id == null) {
                return lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            int deleteFlag = counterMapper.deleteByPrimaryKey(id);
            if (deleteFlag > 0) {
                lsResponse.setMessage("吧台删除成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台删除失败");
            }
        } catch (Exception e) {
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.name());
            log.error("吧台删除失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterByCanteenIds(User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] canteenIds = user.getCanteenIds().split(",");
        List<Counter> counters = counterMapper.getCounterByCanteenIds(canteenIds);
        if (counters != null && counters.size() > 0){
            lsResponse.setData(counters);
        }else {
            lsResponse.checkSuccess(false,"该餐厅没有吧台");
        }
        return lsResponse;
    }

    @Override
    public LsResponse getBTOrderListWeb(User user, String counterId, String date, Integer payType, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        String[] canteenIds = user.getCanteenIds().split(",");
        String[] dateStr = new String[2];
        Integer offSet = 0;
        try {
            if (counterId != null && !counterId.equals("")) {
                counterId = new String(counterId.getBytes("utf-8"), "utf-8");
            } else {
                counterId = "";
            }
            if (date != null && !date.equals("")) {
                dateStr = date.split(" - ");
                dateStr[0] = dateStr[0].replace("/", "-");
                dateStr[1] = dateStr[1].replace("/", "-");
            } else {
                dateStr[0] = Dates.format(Dates.getBeforeDate(new Date(), 30), "yyyy-MM-dd");
                dateStr[1] = Dates.format(new Date(), "yyyy-MM-dd");
            }
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
        }
        try {
            TotalCountMoney total = counterMapper.getBTOrderListWebCount(counterId, dateStr[0], dateStr[1], payType);
            List<CounterOrder> counterOrders = counterMapper.getBTOrderListWeb(counterId, dateStr[0], dateStr[1], payType, offSet, pageSize);
            if (counterOrders != null && counterOrders.size() > 0) {
                lsResponse.setData(counterOrders);
                lsResponse.setMessage(total.getTotalMoney().toString());
                lsResponse.setTotalCount(total.getTotalCount());
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse exportBTOrderListWeb(User user, String counterId, String date, Integer payType, HttpServletResponse response) {
        LsResponse lsResponse = LsResponse.newInstance();
        ExportUtil pee = new ExportUtil(response,counterId + "的吧台流水","sheet1");
        String[] dateStr = new String[2];
        try {
            if (counterId != null && !counterId.equals("")) {
                counterId = new String(counterId.getBytes("utf-8"), "utf-8");
            } else {
                counterId = "";
            }
            if (date != null && !date.equals("")) {
                dateStr = date.split(" - ");
                dateStr[0] = dateStr[0].replace("/", "-");
                dateStr[1] = dateStr[1].replace("/", "-");
            } else {
                dateStr[0] = Dates.format(Dates.getBeforeDate(new Date(), 30), "yyyy-MM-dd");
                dateStr[1] = Dates.format(new Date(), "yyyy-MM-dd");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
        }
        try {
            List<CounterOrder> counterOrders = counterMapper.exportBTOrderListWeb(counterId, dateStr[0], dateStr[1], payType);
            if (counterOrders != null && counterOrders.size() > 0) {
                String titleName[] = {"订单号","订单名称","会员编号","会员名称","会员卡号","实收金额","余额","付款方式","付款时间"};
                String titleColumn[] = {"orderId","orderName","memberId","memberName","memberCarduid","receivedAmount","balance","payTypeName","payTimeStr"};
                int titleSize[] = {20,13,16,16,16,10,10,14,22};
                pee.wirteExcel(titleColumn, titleName, titleSize, counterOrders);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMemberBTOrderListWEB(User user, String counterId, String memberName, String memberCardUid, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer offSet = 0;
        try {
            if (counterId != null && !counterId.equals("")) {
                counterId = new String(counterId.getBytes("utf-8"), "utf-8");
            }
            if (memberName != null && !memberName.equals("")) {
                memberName = new String(memberName.getBytes("utf-8"), "utf-8");
            } else {
                memberName = "";
            }
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        try {
            TotalCountMoney total = counterOrderMapper.getMemberOrderListCount(counterId, memberName, memberCardUid);
            List<CounterOrder> counterOrders = counterOrderMapper.getMemberOrderList(counterId, memberName, memberCardUid, offSet, pageSize);
            if (counterOrders != null && counterOrders.size() > 0) {
                lsResponse.setErrorCode(total.getTotalReceivableMoney().toString());//设置 应收金额总额
                lsResponse.setMessage(total.getTotalMoney().toString());//设置 实收金额总额
                lsResponse.setTotalCount(total.getTotalCount());//设置 总的订单个数
                lsResponse.setData(counterOrders);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getMemberBTOrderItemsWEB(User user, String orderId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (orderId != null && !orderId.equals("")) {
                orderId = new String(orderId.getBytes("utf-8"), "utf-8");
            } else {
                return lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        try {
            List<CounterOrderItems> counterOrderItems = counterOrderMapper.getMemberOrderItems(orderId);
            if (counterOrderItems != null && counterOrderItems.size() > 0) {
                lsResponse.setData(counterOrderItems);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getBingCounterUserList(String keyword, String counterId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (!("").equals(keyword) && keyword != null) {
//                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = new String(keyword.getBytes("utf-8"), "utf-8");
            } else {
                keyword = "";
            }
        } catch (UnsupportedEncodingException e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("查看吧台用户列表失败，失败原因为：" + e.toString());
        }
        try {
            List<CounterUser> counterUsers = counterUserMapper.getBingCounterUserList(keyword);
            if (counterUsers != null && counterUsers.size() > 0) {
                lsResponse.setData(counterUsers);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("吧台用户列表程序失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCountBingUser(CounterUserLinked counterUserLinked, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (counterUserLinked.getChecked()) {
                List<CounterUserLinked> linkeds1 = counterUserLinkedMapper.isBingCounterByUserId(counterUserLinked.getUserId());
                if (linkeds1 != null && linkeds1.size() > 0) {
                    return lsResponse.checkSuccess(false, "该用户已经绑定过吧台，不能重复绑定");
                }
                List<CounterUserLinked> linkeds2 = counterUserLinkedMapper.isBingCounterByCounterId(counterUserLinked.getCounterId());
                if (linkeds2 != null && linkeds2.size() > 0) {
                    return lsResponse.checkSuccess(false, "该吧台已经绑定过用户，不能重复绑定");
                }
                counterUserLinked.setCreateTime(Dates.now());
                counterUserLinked.setCreateUser(user.getUsername());
                int insertFlag = counterUserLinkedMapper.insertSelective(counterUserLinked);
                if (insertFlag > 0) {
                    lsResponse.setMessage("绑定成功");
                } else {
                    lsResponse.checkSuccess(false, "绑定失败");
                }
            } else {
                int deleteFlag = counterUserLinkedMapper.deleteByTwoForigenKey(counterUserLinked.getCounterId(), counterUserLinked.getUserId());
                if (deleteFlag > 0) {
                    lsResponse.setMessage("解绑成功");
                } else {
                    lsResponse.checkSuccess(false, "解绑失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("吧台绑定菜品存失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounterUserLinked(String counterId, String userId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (counterId == null && userId == null) {
                return lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            int deleteFlag = counterUserLinkedMapper.deleteByTwoForigenKey(counterId, userId);
            if (deleteFlag > 0) {
                lsResponse.setMessage("吧台用户删除成功");
            } else {
                lsResponse.checkSuccess(false, "吧台用户删除失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("吧台菜品删除失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterUserLinkedList(String counterId,String keyword) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (!("").equals(counterId) && counterId != null) {
                counterId = new String(counterId.getBytes("iso8859-1"), "utf-8");
            } else {
                counterId = "";
            }
            if (!("").equals(keyword) && keyword != null) {
                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
            } else {
                keyword = "";
            }
        } catch (UnsupportedEncodingException e) {
            lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.getMsg());
            log.error("查看吧台用户列表失败，失败原因为：" + e.toString());
        }
        try {
            CounterUserLinked user = counterUserLinkedMapper.getCounterUserLinkedList(counterId,keyword);
            if (user != null){
                lsResponse.setData(user);
            }else {
                lsResponse.checkSuccess(false,"该吧台暂未绑定用户");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("吧台用户列表程序失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getBingMenuList(String keyword, Integer menutypeId, String counterId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        Integer offSet = 0;
        try {
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (!("").equals(keyword) && keyword != null) {
//                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = new String(keyword.getBytes("utf-8"), "utf-8");
            } else {
                keyword = "";
            }
            if (!("").equals(counterId) && counterId != null) {
//                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                counterId = new String(counterId.getBytes("utf-8"), "utf-8");
            } else {
                counterId = "";
            }
        } catch (UnsupportedEncodingException e) {
            log.error("吧台绑定菜品，右侧弹出菜品列表，无分页失败，失败原因为：" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        try {
            total = counterMenuMapper.getBingMenuListCount(keyword, menutypeId);
            List<CounterMenu> menus = counterMenuMapper.getBingMenuList(keyword, menutypeId, offSet, pageSize);
            List<CounterMenuLinked> counterMenuLinkeds = counterMenuLinkedMapper.getMenuListByCounterId(counterId);
            if (menus != null && menus.size() > 0) {
                for (CounterMenu menu : menus) {
                    if (counterMenuLinkeds != null && counterMenuLinkeds.size() > 0) {
                        for (CounterMenuLinked linked : counterMenuLinkeds) {
                            if (menu.getMenuId().equals(linked.getMenuId())) {
                                menu.setChecked(true);
                            }
                        }
                    }
                }
                lsResponse.setTotalCount(total);
                lsResponse.setData(menus);
            } else {
                lsResponse.checkSuccess(false, CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(CodeMessage.SYSTEM_BUSY + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCountBingMenu(CounterMenuLinked counterMenuLinked, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (counterMenuLinked.isChecked()) {
                counterMenuLinked.setCreateUser(user.getUsername());
                counterMenuLinked.setCreateTime(Dates.now());
                counterMenuLinked.setUpdateUser(user.getUsername());
                counterMenuLinked.setUpdateTime(Dates.now());
                int insertFlag = counterMenuLinkedMapper.insertSelective(counterMenuLinked);
                if (insertFlag > 0) {
                    lsResponse.setMessage("吧台绑定菜品，保存成功");
                } else {
                    lsResponse.checkSuccess(false, "吧台绑定菜品，保存失败");
                }
            } else {
                int deleteFlag = counterMenuLinkedMapper.deleteByTwoForeignKey(counterMenuLinked.getCounterId(), counterMenuLinked.getMenuId());
                if (deleteFlag > 0) {
                    lsResponse.setMessage("吧台绑定菜品,取消成功");
                } else {
                    lsResponse.checkSuccess(false, "吧台绑定菜品,取消失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("吧台绑定菜品存失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounterMenuLinked(String counterId, String menuId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (counterId == null && menuId == null) {
                return lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            int deleteFlag = counterMenuLinkedMapper.deleteByTwoForeignKey(counterId, menuId);
            if (deleteFlag > 0) {
                lsResponse.setMessage("吧台菜品删除成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台菜品删除失败");
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("吧台菜品删除失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterMenuLinkedList(String counterId, String keyword, Integer menutypeId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        Integer total = 0;
        Integer offSet = 0;
        try {
            if (pageNum != null && pageSize != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (!("").equals(counterId) && counterId != null) {
//                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                counterId = new String(counterId.getBytes("utf-8"), "utf-8");
            } else {
                counterId = "";
            }
            if (!("").equals(keyword) && keyword != null) {
//                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
                keyword = new String(keyword.getBytes("utf-8"), "utf-8");
            } else {
                keyword = "";
            }
        } catch (UnsupportedEncodingException e) {
            log.error("获取吧台绑定菜品列表，失败原因为：" + e.toString());
            lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        try {
            total = counterMenuMapper.getCounterMenuLinkedListCount(counterId, keyword, menutypeId);
            List<CounterMenu> menus = counterMenuMapper.getCounterMenuLinkedList(counterId, keyword, menutypeId, offSet, pageSize);
            if (menus != null && menus.size() > 0) {
                lsResponse.setData(menus);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.setAsFailure();
                if (!("").equals(keyword) && keyword != null) {
                    lsResponse.setMessage("该菜品不存在");
                } else if (menutypeId != null) {
                    lsResponse.setMessage("该类型菜品不存在");
                } else {
                    lsResponse.setMessage("该吧台暂未绑定菜品，请先去绑定菜品");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error(CodeMessage.SYSTEM_BUSY + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterMenuType(String counterId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (!("").equals(counterId) && counterId != null) {
                counterId = new String(counterId.getBytes("iso8859-1"), "utf-8");
            } else {
                return lsResponse.checkSuccess(false,CodeMessage.PARAMS_ERR.name());
            }
        } catch (UnsupportedEncodingException e) {
            lsResponse.setMessage(CodeMessage.ASSOCIATOR_NO_MESSAGE.getMsg());
            log.error("根据吧台绑定的菜品得到菜品分类失败，失败原因为：" + e.toString());
        }
        try {
            List<MenuType> menuTypes = menuTypeMapper.getCounterMenuType(counterId);
            if (menuTypes != null && menuTypes.size() > 0){
                lsResponse.setData(menuTypes);
            }else {
                lsResponse.checkSuccess(false,CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("根据吧台绑定的菜品得到菜品分类失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterMenuByTypeId(Integer typeId,String counterId) {
        LsResponse lsResponse = LsResponse.newInstance();
        List<CounterMenu> counterMenus = new ArrayList<>();
        if (typeId == null){
            lsResponse.checkSuccess(false,CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        try {
            counterMenus = counterMenuMapper.getCounterMenuByTypeId(typeId,counterId);
            if (counterMenus != null && counterMenus.size() > 0){
                lsResponse.setData(counterMenus);
            }else {
                lsResponse.checkSuccess(false,CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            log.error("根据吧台绑定的菜品得到菜品分类失败，异常信息为：" + e.toString());
        }
        return lsResponse;
    }
}
