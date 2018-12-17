package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.CounterMenuLinkedMapper;
import com.lswd.youpin.dao.lsyp.CounterMenuMapper;
import com.lswd.youpin.dao.lsyp.CounterUserLinkedMapper;
import com.lswd.youpin.dao.lsyp.MenuTypeMapper;
import com.lswd.youpin.model.CounterUser;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.CounterMenu;
import com.lswd.youpin.model.lsyp.CounterMenuLinked;
import com.lswd.youpin.model.lsyp.MenuType;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.CounterMenuService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;


/**
 * Created by zhenguanqi on 2017/11/24.
 */
@Service
public class CounterMenuServiceImpl implements CounterMenuService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(CounterMenuServiceImpl.class);

    @Autowired
    private CounterMenuMapper counterMenuMapper;
    @Autowired
    private MenuTypeMapper typeMapper;
    @Autowired
    private CounterMenuLinkedMapper counterMenuLinkedMapper;
    @Autowired
    private CounterUserLinkedMapper counterUserLinkedMapper;

    @Override
    public LsResponse getcounterMenuTypeListAll() {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            List<MenuType> menuTypes = typeMapper.getcounterMenuTypeListAll();
            if (menuTypes != null && menuTypes.size() > 0) {
                lsResponse.setData(menuTypes);
            } else {
                lsResponse.setSuccess(false);
                lsResponse.setMessage("查看所有的吧台菜品类型失败");
            }
        } catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getCounterMenuList(String keyword, String counterId, Integer menutypeId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        try {
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (keyword != null && !"".equals(keyword)) {
                keyword = new String(keyword.getBytes("utf-8"), "utf-8");
            } else {
                keyword = "";
            }
            if (counterId != null && !"".equals(counterId)) {
                counterId = new String(counterId.getBytes("iso8859-1"), "utf-8");
            } else {
                counterId = "";
            }
            int total = counterMenuMapper.getCounterMenuListCount(keyword, counterId, menutypeId);
            List<CounterMenu> counterMenus = counterMenuMapper.getCounterMenuList(keyword, counterId, menutypeId, offSet, pageSize);
            if (counterMenus != null && counterMenus.size() > 0) {
                lsResponse.setData(counterMenus);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.setSuccess(false);
                if (keyword != null && !"".equals(keyword)) {
                    lsResponse.setMessage("该吧台菜品不存在");
                } else if (menutypeId != null){
                    lsResponse.setMessage("该分类暂无菜品");
                } else {
                    lsResponse.setMessage(CodeMessage.EMPTY_DATA.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCounterMenu(CounterMenu counterMenu, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (counterMenu.getVipPrice() > 9999){
            return lsResponse.checkSuccess(false,"菜品会员价格过高，新增失败");
        }
        if (counterMenu.getMarketPrice() > 9999){
            return lsResponse.checkSuccess(false,"菜品市场价格过高，新增失败");
        }
        counterMenu.setIsDelete(false);
        counterMenu.setSurplus(99);//做库存用，暂时未用到
        counterMenu.setCreateUser(user.getUsername());
        counterMenu.setCreateTime(Dates.now());
        counterMenu.setUpdateUser(user.getUsername());
        counterMenu.setUpdateTime(Dates.now());
        try {
            Integer maxId = counterMenuMapper.getMaxId();
            if (maxId == null) maxId = 0;
            int menuId = maxId + 100001;
            counterMenu.setMenuId("BTM" + menuId);
            int insertFlag = counterMenuMapper.insertSelective(counterMenu);
            if (insertFlag > 0){
                lsResponse.setMessage("吧台菜品信息新增成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台菜品信息新增失败");
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台菜品信息新增失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounterMenu(CounterMenu counterMenu, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (counterMenu.getVipPrice() > 9999){
            return lsResponse.checkSuccess(false,"菜品会员价格过高，新增失败");
        }
        if (counterMenu.getMarketPrice() > 9999){
            return lsResponse.checkSuccess(false,"菜品市场价格过高，新增失败");
        }
        counterMenu.setUpdateUser(user.getUsername());
        counterMenu.setUpdateTime(Dates.now());
        try {
            int updateFlag = counterMenuMapper.updateByPrimaryKeySelective(counterMenu);
            if (updateFlag > 0){
                counterMenuLinkedMapper.updateByMenuId(counterMenu.getMenuId(),counterMenu.getMenutypeId());
                lsResponse.setMessage("吧台菜品信息修改成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台菜品信息修改失败");
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//如果发生异常，事务回滚
            lsResponse.setAsFailure();
            lsResponse.setMessage(CodeMessage.SYSTEM_BUSY.getMsg());
            logger.error("吧台菜品信息修改失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounterMenu(Integer id, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id == null){
                return lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
            }
            CounterMenu counterMenu = counterMenuMapper.selectByPrimaryKey(id);
            List<CounterMenuLinked> linkeds = counterMenuLinkedMapper.getMenuListByMenuId(counterMenu.getMenuId());
            if (linkeds != null && linkeds.size() > 0){
                return lsResponse.checkSuccess(false,"仍有吧台绑定该菜品，请先解除绑定，再来删除");
            }
            int deleteFlag = counterMenuMapper.deleteCounterMenuUpdate(id);
            if (deleteFlag > 0){
                lsResponse.setMessage("吧台菜品信息删除成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台菜品信息删除失败");
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台菜品信息删除失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    /* -------------------------------------------------吧台收银员 菜品信息 增删改查-----------------------------------------------------------*/

    @Override
    public LsResponse getCounterMenuListBTSY(String keyword, String counterId, Integer menutypeId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = new LsResponse();
        try {
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            if (keyword != null && !"".equals(keyword)) {
                keyword = new String(keyword.getBytes("utf-8"), "utf-8");
//                keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
            } else {
                keyword = "";
            }
            if (counterId != null && !"".equals(counterId)) {
                counterId = new String(counterId.getBytes("iso8859-1"), "utf-8");
            } else {
                counterId = "";
            }
            int total = counterMenuMapper.getCounterMenuListBTSYCount(keyword, counterId, menutypeId);
            List<CounterMenu> counterMenus = counterMenuMapper.getCounterMenuListBTSY(keyword, counterId, menutypeId, offSet, pageSize);
            if (counterMenus != null && counterMenus.size() > 0) {
                lsResponse.setData(counterMenus);
                lsResponse.setTotalCount(total);
            } else {
                lsResponse.setSuccess(false);
                if (keyword != null && !"".equals(keyword)) {
                    lsResponse.setMessage("该吧台菜品不存在");
                } else if (menutypeId != null){
                    lsResponse.setMessage("该分类暂无菜品");
                } else {
                    lsResponse.setMessage(CodeMessage.EMPTY_DATA.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            logger.error(e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addCounterMenuBTSY(CounterMenu counterMenu, CounterUser counterUser) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (counterMenu.getVipPrice() > 9999){
            return lsResponse.checkSuccess(false,"菜品VIP价格过高，新增失败");
        }
        if (counterMenu.getMarketPrice() > 9999){
            return lsResponse.checkSuccess(false,"菜品市场价格过高，新增失败");
        }
        counterMenu.setIsDelete(false);
        counterMenu.setSurplus(99);//做库存用，暂时未用到
        counterMenu.setCreateUser(counterUser.getUsername());
        counterMenu.setCreateTime(Dates.now());
        counterMenu.setUpdateUser(counterUser.getUsername());
        counterMenu.setUpdateTime(Dates.now());
        try {
            Integer maxId = counterMenuMapper.getMaxId();
            if (maxId == null) maxId = 0;
            int menuId = maxId + 100001;
            counterMenu.setMenuId("BTM" + menuId);
            int insertFlag = counterMenuMapper.insertSelective(counterMenu);
            if (insertFlag > 0){
                String counterId = counterUserLinkedMapper.getCounterIdByUserId(counterUser.getUserId());
                CounterMenuLinked linked = new CounterMenuLinked();
                linked.setCounterId(counterId);
                linked.setMenutypeId(counterMenu.getMenutypeId());
                linked.setMenuId("BTM" + menuId);
                linked.setUpdateTime(Dates.now());
                linked.setUpdateUser(counterUser.getUsername());
                linked.setCreateTime(Dates.now());
                linked.setCreateUser(counterUser.getUsername());
                counterMenuLinkedMapper.insertSelective(linked);
                lsResponse.setMessage("吧台菜品信息新增成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台菜品信息新增失败");
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//如果出错，设置事务回滚
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台菜品信息新增失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateCounterMenuBTSY(CounterMenu counterMenu, CounterUser counterUser) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (counterMenu.getVipPrice() > 9999){
            return lsResponse.checkSuccess(false,"菜品VIP价格过高，新增失败");
        }
        if (counterMenu.getMarketPrice() > 9999){
            return lsResponse.checkSuccess(false,"菜品市场价格过高，新增失败");
        }
        counterMenu.setUpdateUser(counterUser.getUsername());
        counterMenu.setUpdateTime(Dates.now());
        try {
            int updateFlag = counterMenuMapper.updateByPrimaryKeySelective(counterMenu);
            if (updateFlag > 0){
                counterMenuLinkedMapper.updateByMenuId(counterMenu.getMenuId(),counterMenu.getMenutypeId());
                lsResponse.setMessage("菜品信息修改成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("菜品信息修改失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//发生异常，手动回滚
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台菜品信息修改失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteCounterMenuBTSY(String menuId, CounterUser counterUser) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (menuId == null || menuId.equals("")){
                return lsResponse.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
            }
            String counterId = counterUserLinkedMapper.getCounterIdByUserId(counterUser.getUserId());
            Integer deleteFlag = counterMenuLinkedMapper.deleteByTwoForeignKey(counterId,menuId);
            if (deleteFlag > 0){
                lsResponse.setMessage("吧台菜品信息删除成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("吧台菜品信息删除失败");
            }
        }catch (Exception e){
            lsResponse.checkSuccess(false,CodeMessage.SYSTEM_BUSY.name());
            logger.error("吧台菜品信息删除失败，异常信息为："+e.toString());
        }
        return lsResponse;
    }
}
