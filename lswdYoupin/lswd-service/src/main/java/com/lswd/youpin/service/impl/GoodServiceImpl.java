package com.lswd.youpin.service.impl;


import com.alibaba.fastjson.JSON;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.common.xml.XmlWriters;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.GoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhenguanqi on 2017/6/14.
 */
@Service
public class GoodServiceImpl implements GoodService{
    private final Logger log = LoggerFactory.getLogger(GoodServiceImpl.class);
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private GoodAttributeMapper goodAttributeMapper;
    @Autowired
    private GoodAdditiveattributeMapper goodAdditiveattributeMapper;
    @Autowired
    private GoodDetailsImgMapper goodDetailsImgMapper;
    @Autowired
    private GoodCategoryMapper goodCategoryMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private GoodPlanItemsMapper goodPlanItemsMapper;
    @Autowired
    private GoodOrderMapper goodOrderMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private RecipeMapper recipeMapper;

    /**
     * 添加商品
     */
    @Override
    public LsResponse addGood(Good good, User user) {
        LsResponse lsResponse = new LsResponse();
        try{
            if (good != null){
                log.info("{} is being executed. User = {}", "添加商品", JSON.toJSON(user.getUsername() + "准备追加新的商品" + good.getGoodName()));
                if (good.getPrice() > (short)1000){
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("商品价格太高,新增失败");
                    return lsResponse;
                }
                Integer max = goodMapper.getMaxId();
                if (max == null){
                    max = 0;
                }
                int number = max + 1000000;//获取商品最大id
                good.setGoodId("g" + String.valueOf(number));//设置商品的编号
                good.setStatus((short)0);
                /*if (good.getUnit() != null){
                    //int num = (int)(Math.random()*3+1);
                    String name = materialMapper.getUnitById(good.getUnit()).getUnit();
                    String standard = String.valueOf(1) + name + "/份";
                    good.setStandard(standard);
                }else {
                    int num = (int)(Math.random()*3+1);
                    good.setStandard(String.valueOf(num) + "斤/份");
                }*/
                good.setSurplus(99);//设置库存
                good.setCreateUser(user.getUsername());//设置基本的用户信息
                good.setCreateTime(Dates.now());
                good.setUpdateUser(user.getUsername());
                good.setUpdateTime(Dates.now());
                String[] startEndModel = good.getStartEndModel().split(" - ");//前台传的日期格式  起始日期/结束日期
                good.setStartTime(Dates.toDate(startEndModel[0].replaceAll("/","-"),"yyyy-MM-dd"));
                good.setEndTime(Dates.toDate(startEndModel[1].replaceAll("/","-"),"yyyy-MM-dd"));
                if (good.getOldSmallPic() != null && good.getOldSmallPic().size() > 0){
                    log.info(user.getUsername()+"新增商品时，上传的小图片共"+good.getOldSmallPic().size());
                    for (int i = 0; i < good.getOldSmallPic().size() - 1; i++){//图片删除
                        log.info("第"+i+"次删除，小图片");
                        log.info("第"+i+"个小图片的url路径为"+good.getOldSmallPic().get(i));
                        String path = "../webapps/images";
                        int indexDot = good.getOldSmallPic().get(i).lastIndexOf("/");
                        File oldFile = new File(path,good.getOldSmallPic().get(i).substring(indexDot));
                        if (oldFile.exists()){
                            log.info("文件存在，我想要删除");
                            oldFile.delete();
                            log.info("删除成功-------------------------");
                        }
                        log.info("文件不存在，继续循环");
                    }
                    good.setImageurl(good.getOldSmallPic().get(good.getOldSmallPic().size() - 1));
                }
                if (good.getOldBigPic() != null && good.getOldBigPic().size() > 0){
                    log.info(user.getUsername()+"新增商品时，上传的大图片共"+good.getOldBigPic().size());
                    for (int i = 0; i < good.getOldBigPic().size() - 1; i++){//图片删除
                        log.info("第"+i+"次删除，大图片");
                        log.info("第"+i+"个大图片的url路径为"+good.getOldBigPic().get(i));
                        String path = "../webapps/images/";
                        int indexDot = good.getOldBigPic().get(i).lastIndexOf("/");
                        File oldFile = new File(path,good.getOldBigPic().get(i).substring(indexDot));
                        if (oldFile.exists()){
                            log.info("文件存在，我想要删除");
                            oldFile.delete();
                            log.info("删除成功-------------------------");
                        }
                        log.info("文件不存在，继续循环");
                    }
                    good.setBigimageurl(good.getOldBigPic().get(good.getOldBigPic().size() - 1));
                }
            }
            int goodflag = goodMapper.insertGood(good);
            if (good.getGoodAdditiveattribute() != null){
                good.getGoodAdditiveattribute().setGoodId(good.getGoodId());
                goodAdditiveattributeMapper.insertGoodAdditiveattribute(good.getGoodAdditiveattribute());
            }
            if (goodflag == 1){//表示数据插入成功
                lsResponse.setMessage(CodeMessage.GOOD_ADD_SUCCESS.getMsg());
            }else {//表示数据插入失败
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.GOOD_ADD_ERR.getMsg());
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("新增商品出错",e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getGoodDetails(String goodId, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try{
            if (goodId != null && !goodId.equals("")){
                goodId = new String(goodId.getBytes("iso8859-1"),"utf-8");
                log.info("正在查看商品详情，传入的商品编号为:"+goodId);
                Good good = goodMapper.getGoodByGoodId(goodId);//根据商品编号查询商品
                if (good != null){
                    List<GoodDetailsImg> goodDetailsImgs = goodDetailsImgMapper.getGoodDetailsImgs(goodId);//查看商品的详情图片
                    GoodAdditiveattribute goodAdditiveattribute = goodAdditiveattributeMapper.getAdditiveattributeByGoodId(goodId);//查看商品的附加属性，比如品牌、产地、季节、适宜人群等
                    GoodAttribute goodAttribute = goodAttributeMapper.getAttributeByGoodId(goodId);//查看商品自身的特性，比如热量、蛋白质、碳水化合物等
                    if (goodDetailsImgs != null && goodDetailsImgs.size() > 0){
                        good.setGoodDetailsImgs(goodDetailsImgs);
                    }else {
                        good.setGoodDetailsImgs(new ArrayList<GoodDetailsImg>());
                    }
                    if (goodAdditiveattribute != null) {
                        good.setGoodAdditiveattribute(goodAdditiveattribute);
                    }else {
                        good.setGoodAdditiveattribute(new GoodAdditiveattribute());
                    }
                    if (goodAttribute != null) {
                        good.setGoodAttribute(goodAttribute);
                    }else {
                        good.setGoodAttribute(new GoodAttribute());
                    }
                    lsResponse.setData(good);//返回数据
                    lsResponse.setMessage(CodeMessage.GOOD_GETGOODADDITIVEATTRIBUTE_SUCCESS.getMsg());//查看商品详情成功
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage(CodeMessage.GOOD_GETGOODADDITIVEATTRIBUTE_ERR.getMsg());//查看商品详情失败
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("查看商品详情出错",e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addGoodDetails(Good good, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        int flag = 0;
        try{
            if (good != null){
                log.info("正在添加商品详情",JSON.toJSON(good));
                if (good.getGoodAttribute() != null){
                    if (good.getGoodAttribute().getId() != null){
                        flag = goodAttributeMapper.updateByGoodId(good.getGoodAttribute());
                    }else {
                        good.getGoodAttribute().setGoodId(good.getGoodId());
                        flag = goodAttributeMapper.insertSelective(good.getGoodAttribute());
                    }
                    log.info(user.getUsername()+"正在添加或修改goodAttribute，修改是否成功呢？请看flag，flag="+flag);
                }
                if (good.getGoodAdditiveattribute() != null){
                    if (good.getGoodAdditiveattribute().getId() != null){
                        flag = goodAdditiveattributeMapper.updateByGoodId(good.getGoodAdditiveattribute());
                    }else {
                        good.getGoodAdditiveattribute().setGoodId(good.getGoodId());
                        flag = goodAdditiveattributeMapper.insertSelective(good.getGoodAdditiveattribute());
                    }
                    log.info(user.getUsername()+"正在添加或修改goodAdditiveattribute，修改是否成功呢？请看flag，flag="+flag);
                }
                if (good.getGoodDetailsImgs().size() > 0 && good.getGoodDetailsImgs() != null){
                    goodDetailsImgMapper.deleteImgByGoodId(good.getGoodId());
                    for (GoodDetailsImg img : good.getGoodDetailsImgs()){
                        img.setGoodId(good.getGoodId());
                        flag = goodDetailsImgMapper.insertGoodDetailsImg(img);
                        log.info(user.getUsername()+"正在添加或修改goodDetailsImg，修改是否成功呢？请看flag，flag="+flag+",上传的图片的个数为：" + good.getGoodDetailsImgs().size());
                    }
                }
                if (good.getDelImgs().size() > 0 && good.getDelImgs() != null){//服务器上删除商品的图片
                    log.info(user.getUsername()+"添加商品详情时，删除的图片共"+good.getDelImgs().size());
                    for (int i = 0; i < good.getDelImgs().size(); i++){//图片删除
                        log.info("第"+i+"个图片的url路径为"+good.getDelImgs().get(i));
                        String path = "../webapps/images";
                        int indexDot = good.getDelImgs().get(i).lastIndexOf("/");
                        File oldFile = new File(path,good.getDelImgs().get(i).substring(indexDot));
                        if (oldFile.exists()){
                            log.info("文件存在，我想要删除");
                            oldFile.delete();
                            log.info("删除成功-------------------------");
                        }
                        log.info("文件不存在，继续循环");
                    }
                }
                if (flag > 0){
                    lsResponse.setMessage("商品详情添加或修改成功");
                }else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("商品详情添加或修改失败");
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("商品good是空的");
                log.info("商品good是空的");
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            //lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("新建或者修改商品详情出错",e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse delGoodDetailsImg(String goodId, String imageurl, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try{
            if (imageurl != null && !"".equals(imageurl)) {
                imageurl = new String(imageurl.getBytes("iso8859-1"), "utf-8");
                String path = "../webapps/images";
                int indexDot = imageurl.lastIndexOf("/");
                File oldFile = new File(path, imageurl.substring(indexDot));
                if (oldFile.exists()) {
                    log.info("文件存在，我想要删除商品详情中的图片信息");
                    oldFile.delete();
                    log.info("删除成功-------------------------");
                }
                if (goodId != null && !"".equals(goodId)) {
                    goodId = new String(goodId.getBytes("iso8859-1"), "utf-8");
                    int flag = goodDetailsImgMapper.deleteImg(goodId, imageurl);
                    if (flag > 0) {
                        lsResponse.setMessage("删除成功");
                    } else {
                        lsResponse.setAsFailure();
                        lsResponse.setMessage("删除商品详情中的图片出错");
                        log.info("删除商品详情中的图片出错");
                    }
                }
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("图片地址不正确");
                log.info("图片地址不正确");
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("删除商品图片出错，错误信息为：",e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse delCancelImg(List<String> imageurls, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (imageurls != null && imageurls.size() >0){//服务器上删除商品的图片
                log.info(user.getUsername()+"添加或修改商品时，用户上传图片之后未保存的图片共" + imageurls.size() + "张");
                for (int i= 0; i < imageurls.size() ; i++){
                    log.info("第"+i+"个图片的url路径为"+imageurls.get(i));
                    String path = "../webapps/images";
                    int indexDot = imageurls.get(i).lastIndexOf("/");
                    File oldFile = new File(path,imageurls.get(i).substring(indexDot));
                    if (oldFile.exists()){
                        oldFile.delete();
                        log.info("文件存在，删除成功");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            lsResponse.setAsFailure();
            lsResponse.setErrorCode("500");
            log.error("添加或修改商品时，用户上传图片之后未保存的图片---删除失败，失败原因：",e.toString());
        }
        return lsResponse;
    }

    /**
     * 商品下架
     */
    @Override
    public LsResponse deleteGood(Integer id, User user) {
        LsResponse lsResponse = new LsResponse();
        try {
            int goodflag = goodMapper.deleteGoodCancel(id);
            if (goodflag > 0) {
                log.info("{} is being executed. User = {}", "商品下架", JSON.toJSON(user.getUsername() + "成功下架商品主键id为" + id + "的商品"));
                lsResponse.setMessage(CodeMessage.GOOD_DELETE_SUCCESS.getMsg());
            } else {
                log.info("{} is being executed. User = {}", "商品下架", JSON.toJSON(user.getUsername() + "下架商品主键id为" + id  + "的商品失败"));
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.GOOD_DELETE_ERR.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("商品下架出现异常，快来这里看看是哪里的毛病，异常信息为：",e.toString());
        }
        return lsResponse;
    }

    /**
     * 修改商品
     */
    @Override
    public LsResponse updateGood(Good good, User user) {
        LsResponse lsResponse = new LsResponse();
        log.info("{} is being executed. User = {}", "修改商品", JSON.toJSON(user.getUsername() + "准备修改商品的编号为" + good.getId() + "的商品"));
        try {
            if (good != null){
                if (good.getPrice() > (short)1000){
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("商品价格太高,修改失败");
                    return lsResponse;
                }
                good.setStatus((short)0);
                good.setUpdateUser(user.getUsername());
                good.setUpdateTime(Dates.now());
                String[] startEndModel = good.getStartEndModel().split(" - ");
                good.setStartTime(Dates.toDate(startEndModel[0].replaceAll("/","-"),"yyyy-MM-dd"));
                good.setEndTime(Dates.toDate(startEndModel[1].replaceAll("/","-"),"yyyy-MM-dd"));
                if (good.getOldSmallPic() != null && good.getOldSmallPic().size() > 0){
                    log.info(user.getUsername()+"修改商品时，上传的小图片共"+good.getOldSmallPic().size());
                    for (int i = 0; i < good.getOldSmallPic().size() - 1; i++){//图片删除
                        log.info("修改商品时，第"+i+"次删除，小图片");
                        log.info("修改商品时，第"+i+"个小图片的url路径为"+good.getOldSmallPic().get(i));
                        String path = "../webapps/images";
                        int indexDot = good.getOldSmallPic().get(i).lastIndexOf("/");
                        File oldFile = new File(path,good.getOldSmallPic().get(i).substring(indexDot));
                        if (oldFile.exists()){
                            log.info("文件存在，我想要删除");
                            oldFile.delete();
                            log.info("删除成功-------------------------");
                        }
                        log.info("文件不存在，继续循环");
                    }
                    good.setImageurl(good.getOldSmallPic().get(good.getOldSmallPic().size() - 1));
                }
                if (good.getOldBigPic() != null && good.getOldBigPic().size() > 0){
                    log.info(user.getUsername()+"修改商品时，上传的大图片共"+good.getOldBigPic().size());
                    for (int i = 0; i < good.getOldBigPic().size() - 1; i++){//图片删除
                        log.info("修改商品时，第"+i+"次删除，大图片");
                        log.info("修改商品时，第"+i+"个大图片的url路径为"+good.getOldBigPic().get(i));
                        String path = "../webapps/images/";
                        int indexDot = good.getOldBigPic().get(i).lastIndexOf("/");
                        File oldFile = new File(path,good.getOldBigPic().get(i).substring(indexDot));
                        if (oldFile.exists()){
                            log.info("文件存在，我想要删除");
                            oldFile.delete();
                            log.info("删除成功-------------------------");
                        }
                        log.info("文件不存在，继续循环");
                    }
                    good.setBigimageurl(good.getOldBigPic().get(good.getOldBigPic().size() - 1));
                }
            }
            int goodflag = goodMapper.updateGood(good);
            if (good.getGoodAdditiveattribute() != null){
                int attrflag = goodAdditiveattributeMapper.updateByGoodId(good.getGoodAdditiveattribute());
            }
            if (goodflag > 0) {
                log.info("{} is being executed. User = {}", "修改商品", JSON.toJSON(user.getUsername() + "成功修改商品编号为" +  good.getGoodId()  + "的商品"));
                lsResponse.setMessage(CodeMessage.GOOD_UPDATE_SUCCESS.getMsg());
            } else {
                log.info("{} is being executed. User = {}", "修改商品", JSON.toJSON(user.getUsername() + "修改商品编号为" +  good.getGoodId()  + "的商品，已失败告终"));
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.GOOD_UPDATE_ERR.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("修改商品出错",e.toString());
        }
        return lsResponse;
    }

    /**
     * 根据条件进行查询
     * @param keyword 关键字
     * @param supplierId 供应商编号
     * @param categoryId 分类编号
     * @param canteenId 餐厅编号
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    @Override
    public LsResponse getGoodList(User user,String keyword, String supplierId,Integer categoryId,String canteenId,Integer pageNum,Integer pageSize) {
        LsResponse lsResponse=new LsResponse();
        StringBuilder sb  = new StringBuilder();
        try {
            String[] canteenIds = user.getCanteenIds().split(",");
            if(keyword!=null&&!"".equals(keyword)){
                keyword = sb.append("%").append(new String(keyword.getBytes("iso8859-1"),"utf-8")).append("%").toString();
            }else{
                keyword = "";
            }
            if(supplierId!=null && !"".equals(supplierId)){
                supplierId = new String(supplierId.getBytes("iso8859-1"),"utf-8");
            }else{
                supplierId = "";
            }
            if(canteenId!=null && !"".equals(canteenId)){
                canteenId = new String(canteenId.getBytes("iso8859-1"),"utf-8");
            }else{
                /*String[] ids = user.getCanteenIds().split(",");
                List<Canteen> canteen = canteenMapper.getUserCanteenList(ids);
                for (Canteen c :canteen){
                }*/
                canteenId = "";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = goodMapper.getGoodCount(keyword,supplierId,categoryId,canteenId,canteenIds);
            List<Good> goodslist = goodMapper.getGoodList(keyword,supplierId,categoryId,canteenId,canteenIds,offSet,pageSize);
            if (goodslist != null && goodslist.size() > 0){
                for (Good good : goodslist){//这个分类名称和供应商名称可以完善
                    GoodCategory goodCategory = goodCategoryMapper.selectByPrimaryKey(good.getCategoryId());
                    if (goodCategory != null){
                        good.setCategoryName(goodCategory.getName());
                    }
                    Supplier supp = supplierMapper.selectBySupplierId(good.getSupplierId());
                    if (supp != null){
                        good.setSupplierName(supp.getName());
                    }
                    String startEndModel = Dates.format(good.getStartTime(),"yyyy/MM/dd") + " - " + Dates.format(good.getEndTime(),"yyyy/MM/dd") ;
                    good.setStartEndModel(startEndModel);
                }
                lsResponse.setTotalCount(total);
                lsResponse.setData(goodslist);
                lsResponse.setMessage(CodeMessage.GOOD_SELECT_SUCCESS.getMsg());
            }else{
                lsResponse.setSuccess(false);
                lsResponse.setData(new ArrayList<>());
                lsResponse.setMessage(CodeMessage.GOOD_SELECT_ERR.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("WEB端，获取商品列表出错",e.toString());
        }
        return lsResponse;
    }

    /**
     * 查看商品附加详情（该方法暂时未用到）
     * @param goodId
     * @return
     */
    @Override
    public LsResponse getGoodInfoByGoodId(String goodId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            goodId = new String(goodId.getBytes("iso8859-1"), "utf-8");  //设置编码
            Good good = goodMapper.getGoodByGoodId(goodId);
            GoodAdditiveattribute goodAdditiveattribute = goodAdditiveattributeMapper.getAdditiveattributeByGoodId(goodId);//查看商品的附加属性，比如品牌、产地、季节、适宜人群等
            GoodAttribute goodAttribute = goodAttributeMapper.getAttributeByGoodId(goodId);//查看商品自身的特性，比如热量、蛋白质、碳水化合物等
            List<GoodDetailsImg> goodDetailsImgs = goodDetailsImgMapper.getGoodDetailsImgs(goodId);//查看商品的详情图片
            if (goodAdditiveattribute != null) {
                good.setGoodAdditiveattribute(goodAdditiveattribute);
            }
            if (goodAttribute != null) {
                good.setGoodAttribute(goodAttribute);
            }
            if (goodDetailsImgs != null && goodDetailsImgs.size() > 0){
                good.setGoodDetailsImgs(goodDetailsImgs);
            }
            if (good != null){
                lsResponse.setData(good);//返回数据
                lsResponse.setMessage(CodeMessage.GOOD_GETGOODADDITIVEATTRIBUTE_SUCCESS.getCode());//返回后台成功的信息
            } else {
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.GOOD_GETGOODADDITIVEATTRIBUTE_ERR.getCode());//返回后台错误的信息
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.setMessage(e.toString());
            log.error("查看商品附加详情失败", e.toString());
        }
        return lsResponse;
    }


    /**
     * 获取商品的附加信息以及自身特性
     * @return
     */
    public void getGoodDetailsInfo(List<Good> goods){
        for (Good good : goods){
            GoodAdditiveattribute goodAdditiveattribute = goodAdditiveattributeMapper.getAdditiveattributeByGoodId(good.getGoodId());//查看商品的附加属性，比如品牌、产地、季节、适宜人群等
            GoodAttribute goodAttribute = goodAttributeMapper.getAttributeByGoodId(good.getGoodId());//查看商品自身的特性，比如热量、蛋白质、碳水化合物等
            List<GoodDetailsImg> goodDetailsImgs = goodDetailsImgMapper.getGoodDetailsImgs(good.getGoodId());//查看商品的详情图片
            if (goodAdditiveattribute != null) {
                good.setGoodAdditiveattribute(goodAdditiveattribute);
            }
            if (goodAttribute != null) {
                good.setGoodAttribute(goodAttribute);
            }
            if (goodDetailsImgs != null && goodDetailsImgs.size() > 0){
                good.setGoodDetailsImgs(goodDetailsImgs);
            }
        }
    }


    /*商品计划新增时需要用到的方法*/
    @Override
    public LsResponse getGoodMINI(String goodPlanId,String keyword, String supplierId, Integer categoryId, String pickingTime, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        StringBuilder sb  = new StringBuilder();
        try {
            if (keyword != null && !"".equals(keyword)){
                keyword = sb.append("%").append(new String(keyword.getBytes("iso8859-1"),"utf-8")).append("%").toString();
            }else{
                keyword = "";
            }
            if(supplierId!=null && !"".equals(supplierId)){
                supplierId = new String(supplierId.getBytes("iso8859-1"),"utf-8");
            }else{
                supplierId = "";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = goodMapper.getGoodMINICount(keyword,supplierId,categoryId,canteenId,pickingTime);
            //带有pickingTime的原因是因为：查询商品的最晚供货时间>取货时间
            List<Good> goods = goodMapper.getGoodMINIList(keyword,supplierId,categoryId,canteenId,pickingTime,offSet,pageSize);
            if (goodPlanId != null && !("").equals(goodPlanId)){
                List<GoodPlanItems> items = goodPlanItemsMapper.getGoodPlanDetailsListByGoodPlanId(goodPlanId);
                for (Good good : goods){
                    if (items != null && items.size() >0){
                        for (GoodPlanItems it :items){
                            if (good.getGoodId().equals(it.getGoodId())){
                                good.setSurplus(it.getSurplus());
                                good.setPrice(it.getPriceDay());
                                good.setMarketPrice(it.getMarketPriceDay());
                                log.info("商品编号为："+it.getGoodId()+"是否是热销："+it.isHot());
                                good.setHot(it.isHot());
//                                if (it.isHot()){
//                                    log.info("商品编号为："+it.getGoodId()+"是热销，计划单号为"+it.getGoodPlanId());
//                                    good.setHot(true);
//                                }else {
//                                    log.info("商品编号为："+it.getGoodId()+"不是热销商品，计划单号为"+it.getGoodPlanId());
//                                    good.setHot(it.isHot());
//                                }
                                good.setChecked(true);
                                break;
                            }
                        }
                    }
                }
            }
            if (goods != null && goods.size() > 0){
                lsResponse.setTotalCount(total);
                lsResponse.setData(goods);
                lsResponse.setMessage("查询成功");
            }else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("数据查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("查询商品计划近5天的计划时，失败，异常信息为"+e.toString());
        }
        return lsResponse;
    }

/*------------------------------------ H5 用到的方法----------------------------------------------------------------*/
    @Override
    public LsResponse getGoodInfoByGoodIdH5(Associator associator, String goodId, String canteenId, String pickingTime,String goodPlanId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (pickingTime == "" || pickingTime == null){
                pickingTime = Dates.now("yyyy-MM-dd");
            }
            goodId = new String(goodId.getBytes("iso8859-1"), "utf-8");  //设置编码
            Good good = goodMapper.getGoodByGoodId(goodId);
            List<GoodPlanItems> items = goodPlanItemsMapper.getGoodPlanDetailsListByGoodPlanId(goodPlanId);
            String now = Dates.now("yyyyMMdd");
            if (items != null && items.size() > 0){
                for (GoodPlanItems planItems : items){
                    if (planItems.getGoodId().equals(goodId)){
                        good.setPrice(planItems.getPriceDay());
                        good.setMarketPrice(planItems.getMarketPriceDay());
                        if (Long.valueOf(pickingTime.replace("-","")) <  Long.valueOf(now)){
                            good.setSurplus(0);
                            good.setNum(0);
                        }else {
                            good.setSurplus(planItems.getSurplus());
                        }
                    }
                }
            }
            Integer count = goodOrderMapper.getGoodMonthSales(canteenId,good.getGoodId());
            Cart cart = cartMapper.getCartByCanAndGoodIdAndAsso(canteenId,good.getGoodId(),associator.getAssociatorId(),pickingTime,0);
            if (count != null){
                good.setMonthSale(count);
            }
            if (cart != null) {
                good.setNum(cart.getQuantity());
                if (good.getNum() >0){
                    good.setChecked(true);
                }
            }
            GoodAdditiveattribute goodAdditiveattribute = goodAdditiveattributeMapper.getAdditiveattributeByGoodId(goodId);//查看商品的附加属性，比如品牌、产地、季节、适宜人群等
            GoodAttribute goodAttribute = goodAttributeMapper.getAttributeByGoodId(goodId);//查看商品自身的特性，比如热量、蛋白质、碳水化合物等
            List<GoodDetailsImg> goodDetailsImgs = goodDetailsImgMapper.getGoodDetailsImgs(goodId);//查看商品的详情图片
            if (goodDetailsImgs != null && goodDetailsImgs.size() > 0){
                good.setGoodDetailsImgs(goodDetailsImgs);
            }
            if (goodAdditiveattribute != null) {
                good.setGoodAdditiveattribute(goodAdditiveattribute);
            }
            if (goodAttribute != null) {
                good.setGoodAttribute(goodAttribute);
            }
            if (good != null){
                lsResponse.setData(good);//返回数据
                lsResponse.setMessage(CodeMessage.GOOD_GETGOODADDITIVEATTRIBUTE_SUCCESS.getMsg());//返回后台成功的信息
            } else {
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.GOOD_GETGOODADDITIVEATTRIBUTE_ERR.getMsg());//返回后台错误的信息
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("H5端，查看商品列表时，失败，失败原因为：", e.toString());
        }
        return lsResponse;
    }

/*-------------------------------------微信小程序需要用到接口------------------------------------------------------*/

    @Override
    public LsResponse getGoodOrRecipeByNoWx(String goodOrRecipeId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            goodOrRecipeId = new String(goodOrRecipeId.getBytes("iso8859-1"), "utf-8");//设置编码
            String flag = goodOrRecipeId.substring(0,1);
            Map<String,Object> map = new HashMap<>();
            if (flag.equals("g")){
                Good good = goodMapper.getGoodByGoodId(goodOrRecipeId);
                if (good != null){
                    map.put("name",good.getGoodName());
                    map.put("bigimageurl",good.getBigimageurl());
                    map.put("price",good.getPrice());
                }
            }else if (flag.equals("r")){
                Recipe recipe = recipeMapper.getRecipeByRecipeId(goodOrRecipeId);
                if (recipe != null) {
                    map.put("name", recipe.getRecipeName());
                    map.put("bigimageurl", recipe.getBigimageurl());
                    map.put("price", recipe.getGuidePrice());
                }
            }
            if (map != null && map.size() >0){
                lsResponse.setData(map);
                lsResponse.setMessage("查看详情成功");
                log.info("查看详情成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setSuccess(false);
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("微信小程序，查看商品or菜品详情失败", e.toString());
        }
        return lsResponse;
    }
}
