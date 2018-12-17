package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.commons.CodeMessage;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.Associator;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.RecipeService;
import com.lswd.youpin.utils.StringsUtil;
import com.lswd.youpin.utils.Values;
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
 * Created by zhenguanqi on 2017/6/19.
 */
@Service
public class RecipeServiceImpl implements RecipeService {
    private final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private RecipeDetailsImgMapper recipeDetailsImgMapper;
    @Autowired
    private RecipeMaterialMapper recipeMaterialMapper;
    @Autowired
    private RecipeCategoryMapper recipeCategoryMapper;
    @Autowired
    private RecipePlanItemsMapper recipePlanItemsMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private RecipeOrderMapper recipeOrderMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private RecipeAttributeMapper recipeAttributeMapper;
    @Autowired
    private IngredientMapper ingredientMapper;
    @Autowired
    private NutritionMapper nutritionMapper;
    @Autowired
    private DiskTypeMapper diskTypeMapper;

    //根据名称来获取字符串数组的索引
    private int getIndex(String[] strings, String name) {
        int i = 0;
        for (; i < strings.length; i++) {
            if (strings[i].equals(name)) break;
        }
        return i + 1;
    }

    @Override
    public LsResponse insertRecipe(User user, Recipe recipe) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (recipe != null) {
                log.info("菜谱插入的方法正在执行，是由" + user.getUsername() + "进行的");
                if (recipe.getGuidePrice() > (short) 1000) {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("指导价格太高,新增失败");
                    return lsResponse;
                }
                Integer max = recipeMapper.getRecipeMaxID();
                if (max == null) {
                    max = 0;
                }
                int number = 1000000 + max;
                recipe.setRecipeId("r" + String.valueOf(number));
                recipe.setStatus((short) 0);
                recipe.setCookType((short) getIndex(Values.cookTypeName, recipe.getCookTypeName()));
                recipe.setSurplus(99);
                recipe.setCreateTime(Dates.now());
                recipe.setUpdateTime(Dates.now());
                recipe.setCreateUser(user.getUsername());
                recipe.setUpdateUser(user.getUsername());
                if (recipe.getOldSmallPic() != null && recipe.getOldSmallPic().size() > 0) {
                    log.info(user.getUsername() + "新增菜品时，上传的小图片共" + recipe.getOldSmallPic().size());
                    for (int i = 0; i < recipe.getOldSmallPic().size() - 1; i++) {//图片删除
                        String path = "../webapps/images";
                        int indexDot = recipe.getOldSmallPic().get(i).lastIndexOf("/");
                        File oldFile = new File(path, recipe.getOldSmallPic().get(i).substring(indexDot));
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }
                    recipe.setImageurl(recipe.getOldSmallPic().get(recipe.getOldSmallPic().size() - 1));
                }
                if (recipe.getOldBigPic() != null && recipe.getOldBigPic().size() > 0) {
                    log.info(user.getUsername() + "新增菜品时，上传的大图片共" + recipe.getOldBigPic().size());
                    for (int i = 0; i < recipe.getOldBigPic().size() - 1; i++) {//图片删除
                        String path = "../webapps/images/";
                        int indexDot = recipe.getOldBigPic().get(i).lastIndexOf("/");
                        File oldFile = new File(path, recipe.getOldBigPic().get(i).substring(indexDot));
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }
                    recipe.setBigimageurl(recipe.getOldBigPic().get(recipe.getOldBigPic().size() - 1));
                }
            }
            int recipeflag = recipeMapper.insertRecipe(recipe);
            for (RecipeMaterial recipeMaterial : recipe.getMaterials()) {
                if (recipeMaterial == null) {
                    continue;
                }
                recipeMaterial.setId(null);
                recipeMaterial.setCreateUser(user.getUsername());
                recipeMaterial.setCreateTime(Dates.now());
                recipeMaterial.setUpdateUser(user.getUsername());
                recipeMaterial.setUpdateTime(Dates.now());
                recipeMaterial.setRecipeId(recipe.getRecipeId());
                //recipeMaterial.setMaterialType((short)(recipeMaterial.getMaterialTypeName() == "主料"?0:1));//修改主料、辅料
                if (recipeMaterial.getMaterial().getMaterialId() != null) {
                    recipeMaterial.setMaterialId(recipeMaterial.getMaterial().getMaterialId());
                    recipeMaterialMapper.insert(recipeMaterial);
                }
            }
            if (recipeflag > 0) {
                lsResponse.setMessage(CodeMessage.RECIPE_INSERTRECIPE_SUCCESS.getMsg());//返回后台成功的信息
                log.info("菜谱插入的方法正在执行，是由" + user.getUsername() + "进行的，成功插入数据");
            } else {//表示数据插入失败
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.RECIPE_INSERTRECIPE_ERR.getMsg());//返回后台成功的信息
                log.info("菜谱插入的方法正在执行，是由" + user.getUsername() + "进行的，数据插入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("新建菜品发生异常，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeDetails(String recipeId, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (recipeId != null && !"".equals(recipeId)) {
                recipeId = new String(recipeId.getBytes("iso8859-1"), "utf-8");
                log.info("正在查看菜品详情，传入的菜品编号为:" + recipeId);
                Recipe recipe = recipeMapper.getRecipeByRecipeId(recipeId);
                if (recipe != null) {
                    RecipeAttribute recipeAttribute = recipeAttributeMapper.getAttributeByRecipeId(recipeId);
                    if (recipeAttribute != null) {
                        recipe.setRecipeAttribute(recipeAttribute);
                    } else {
                        recipe.setRecipeAttribute(new RecipeAttribute());
                    }
                    List<RecipeDetailsImg> recipeDetailsImgs = recipeDetailsImgMapper.getRecipeDetailsImgs(recipeId);
                    if (recipeDetailsImgs != null && recipeDetailsImgs.size() > 0) {
                        recipe.setRecipeDetailsImgs(recipeDetailsImgs);
                    } else {
                        recipe.setRecipeDetailsImgs(new ArrayList<RecipeDetailsImg>());
                    }
                    lsResponse.setData(recipe);
                    lsResponse.setMessage("菜品详情查看成功");
                    log.info("菜品详情查看成功,菜品编号为：recipeId=" + recipeId);
                } else {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("菜品详情查看失败");
                    log.info("菜品详情查看失败,菜品编号为：recipeId=" + recipeId);
                }
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("输入的菜品编号有误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("查看菜品详情出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse addRecipeDetails(Recipe recipe, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        int flag = 0;
        try {
            if (recipe != null) {
                if (recipe.getRecipeAttribute() != null) {
                    if (recipe.getRecipeAttribute().getId() != null) {
                        flag = recipeAttributeMapper.updateByPrimaryKeySelective(recipe.getRecipeAttribute());
                        log.info(user.getUsername() + "正在修改菜品的attribute，修改是否成功？请看flag，flag=" + flag);
                    } else {
                        recipe.getRecipeAttribute().setRecipeId(recipe.getRecipeId());
                        flag = recipeAttributeMapper.insertSelective(recipe.getRecipeAttribute());
                        log.info(user.getUsername() + "正在插入菜品的attribute，插入是否成功？请看flag，flag=" + flag);
                    }
                }
                if (recipe.getRecipeDetailsImgs() != null && recipe.getRecipeDetailsImgs().size() > 0) {
                    flag = recipeDetailsImgMapper.deleteImgByRecipeId(recipe.getRecipeId());
                    log.info(user.getUsername() + "正在删除recipeDetailsImg，删除是否成功？请看flag，flag=" + flag);
                    for (RecipeDetailsImg img : recipe.getRecipeDetailsImgs()) {
                        recipeDetailsImgMapper.insertRecipeDetailsImg(img);
                    }
                }
                if (recipe.getDelImgs().size() > 0 && recipe.getDelImgs() != null) {//服务器上删除菜品的图片
                    log.info(user.getUsername() + "添加菜品详情时，删除的图片共" + recipe.getDelImgs().size());
                    for (int i = 0; i < recipe.getDelImgs().size(); i++) {//图片删除
                        log.info("第" + i + "个图片的url路径为" + recipe.getDelImgs().get(i));
                        String path = "../webapps/images";
                        int indexDot = recipe.getDelImgs().get(i).lastIndexOf("/");
                        File oldFile = new File(path, recipe.getDelImgs().get(i).substring(indexDot));
                        if (oldFile.exists()) {
                            oldFile.delete();
                            log.info("删除成功-------------------------");
                        }
                        log.info("文件不存在，继续循环");
                    }
                }
                lsResponse.setMessage("菜品详情添加或修改成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("菜品详情添加或修改失败");
                log.info("新增或修改菜品详情出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("新增或修改菜品详情出错", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse delRecipeDetailsImg(String recipeId, String imageurl, User user) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (imageurl != null && !"".equals(imageurl)) {
                imageurl = new String(imageurl.getBytes("iso8859-1"), "utf-8");
                String path = "../webapps/images";
                int indexDot = imageurl.lastIndexOf("/");
                File oldFile = new File(path, imageurl.substring(indexDot));
                if (oldFile.exists()) {
                    log.info("文件存在，我想要删除菜品详情中的图片信息");
                    oldFile.delete();
                    log.info("删除成功-------------------------");
                }
                if (recipeId != null && !"".equals(recipeId)) {
                    recipeId = new String(recipeId.getBytes("iso8859-1"), "utf-8");
                    int flag = recipeDetailsImgMapper.deleteImg(recipeId, imageurl);
                    if (flag > 0) {
                        lsResponse.setMessage("删除成功");
                    } else {
                        lsResponse.setAsFailure();
                        lsResponse.setMessage("删除菜品详情中的图片出错");
                        log.info("删除菜品详情中的图片出错");
                    }
                }
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("图片地址不正确");
                log.info("图片地址不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("删除菜品图片出错，错误信息为：", e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse deleteRecipe(User user, Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id != null) {
                log.info("菜谱删除的方法正在执行，是由" + user.getUsername() + "进行的");
            }
            //int flag = recipeMapper.deleteRecipe(id);
            int flag = recipeMapper.updateRecipeIsDelete(id);//对数据不进行删除，只是修改某个字段，is_delete = 1 和 status = 1
            if (flag > 0) {//表示数据删除成功
                log.info("菜谱删除的方法正在执行，是由" + user.getUsername() + "进行的，成功删除数据");
                lsResponse.setMessage(CodeMessage.RECIPE_DELETERECIPE_SUCCESS.getMsg());//返回后台成功的信息
            } else {//表示数据删除失败
                log.info("餐厅菜谱删除的方法正在执行，是由" + user.getUsername() + "进行的，数据删除失败");
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.RECIPE_DELETERECIPE_ERR.getMsg());//返回后台成功的信息
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setSuccess(false);
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("删除菜品时发生异常，异常信息为" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse updateRecipe(User user, Recipe recipe) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (recipe != null) {
                log.info("菜谱修改的方法正在执行，是由" + user.getUsername() + "进行的");
                if (recipe.getGuidePrice() > (short) 1000) {
                    lsResponse.setAsFailure();
                    lsResponse.setMessage("指导价格太高,修改失败");
                    return lsResponse;
                }
                recipe.setCookType((short) getIndex(Values.cookTypeName, recipe.getCookTypeName()));
                recipe.setStatus((short) 0);
                recipe.setDelete(false);
                recipe.setUpdateTime(Dates.now());
                recipe.setUpdateUser(user.getUsername());
            }
            int recipeflag = recipeMapper.updateRecipe(recipe);
            recipeMaterialMapper.deleteByRecipeId(recipe.getRecipeId());//首先将现在的数据全部删除，然后再进行添加
            if (recipe.getMaterials() != null && recipe.getMaterials().size() > 0) {
                for (RecipeMaterial recipeMaterial : recipe.getMaterials()) {
                    if (recipeMaterial == null) {//如果为null，退出循环
                        continue;
                    }
                    recipeMaterial.setId(null);
                    recipe.setCreateTime(Dates.now());
                    recipe.setUpdateTime(Dates.now());
                    recipe.setCreateUser(user.getUsername());
                    recipe.setUpdateUser(user.getUsername());
                    recipeMaterial.setRecipeId(recipe.getRecipeId());
                    if (recipeMaterial.getMaterial().getMaterialId() != null) {
                        recipeMaterial.setMaterialId(recipeMaterial.getMaterial().getMaterialId());
                        recipeMaterialMapper.insert(recipeMaterial);
                    }
                    //recipeMaterial.setMaterialId(recipeMaterial.getMaterial().getMaterialId());
                    //recipeMaterial.setMaterialType((short)(recipeMaterial.getMaterialTypeName() == "主料"?0:1));//修改主料、辅料
                    //recipeMaterialMapper.insert(recipeMaterial);
                }
            }
            if (recipeflag > 0) {
                log.info("菜谱修改的方法正在执行，是由" + user.getUsername() + "进行的，成功修改数据");
                lsResponse.setMessage(CodeMessage.RECIPE_UPDATERECIPE_SUCCESS.getMsg());//返回后台成功的信息
            } else {
                log.info("菜谱修改的方法正在执行，是由" + user.getUsername() + "进行的，数据修改失败");
                lsResponse.setSuccess(false);
                lsResponse.setMessage(CodeMessage.RECIPE_UPDATERECIPE_ERR.getMsg());//返回后台成功的信息
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setSuccess(false);
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("菜谱修改的方法执行时发生异常信息，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeList(User user, String keyword, String canteenId, Integer categoryId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        StringBuilder sb = new StringBuilder();
        try {
            if (keyword != null && !"".equals(keyword)) {
                keyword = sb.append("%").append(new String(keyword.getBytes("iso8859-1"), "utf-8")).append("%").toString();
            } else {
                keyword = "";
            }
            String[] canteenIds = user.getCanteenIds().split(",");
            if (canteenId != null && !"".equals(canteenId)) {
                canteenId = new String(canteenId.getBytes("iso8859-1"), "utf-8");
            } else {
                canteenId = "";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = recipeMapper.getRecipeCount(keyword, canteenId, categoryId, canteenIds);
            List<Recipe> rlist = recipeMapper.getRecipeList(keyword, canteenId, categoryId, canteenIds, offSet, pageSize);
            for (Recipe recipe : rlist) {
                recipe.setCookTypeName(Values.cookTypeName[recipe.getCookType() - 1]);
                recipe.setStatusName(recipe.getStatus() == 0 ? "在售" : "已下架");
                List<RecipeMaterial> materials = recipeMaterialMapper.getRecipeDetails(recipe.getRecipeId());
                if (materials != null && materials.size() > 0) {
                    recipe.setMaterials(materials);
                }
            }
            if (rlist != null && rlist.size() > 0) {//表示数据查询成功
                lsResponse.setData(rlist);
                lsResponse.setTotalCount(total);
            } else {//表示数据删除失败
                if (keyword != null && !"".equals(keyword)) {
                    lsResponse.setMessage("该菜品不存在");
                } else if (categoryId != null) {
                    lsResponse.setMessage("该分类中没有菜品信息");
                } else {
                    lsResponse.setMessage(CodeMessage.RECIPE_GETRECIPELIST_SUCCESS.getMsg());
                }
                lsResponse.setSuccess(false);
                lsResponse.setData(new ArrayList<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("WEB端，菜品列表查询失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeListAll(String canteenId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (canteenId != null && !"".equals(canteenId)) {
                canteenId = new String(canteenId.getBytes("iso8859-1"), "utf-8");
            }
            List<Recipe> rlist = recipeMapper.getRecipeListAllAll(canteenId);
            if (rlist != null && rlist.size() > 0) {//表示数据查询成功
                lsResponse.setData(rlist);
            } else {//表示数据删除失败
                lsResponse.checkSuccess(false, "该餐厅暂时没有菜品信息");
            }
        } catch (Exception e) {
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("根据餐厅查询所有的菜品失败" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeListAllPage(String keyword, String canteenId, Integer categoryId, String recipeId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            keyword=StringsUtil.encodingChange(keyword);
            canteenId=StringsUtil.encodingChange(canteenId);
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = recipeMapper.getRecipeListAllPageCount(keyword, canteenId, categoryId);
            List<Recipe> rlist = recipeMapper.getRecipeListAllPage(keyword, canteenId, categoryId, offSet, pageSize);
            if (rlist != null && rlist.size() > 0) {//表示数据查询成功
                for (Recipe recipe : rlist) {
                    if (recipe.getRecipeId().equals(recipeId)) {
                        recipe.setChecked(true);
                    }
                }
                lsResponse.setData(rlist);
                lsResponse.setTotalCount(total);
            } else {//表示数据删除失败
                lsResponse.setSuccess(false);
                if (keyword != null && !"".equals(keyword)) {
                    lsResponse.setMessage("该菜品不存在");
                } else if (categoryId != null) {
                    lsResponse.setMessage("该分类中没有菜品信息");
                } else {
                    lsResponse.setMessage(CodeMessage.RECIPE_GETRECIPELIST_ERR.getMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("WEB端，菜品列表查询失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    /**
     * 查看餐厅菜谱详情(暂时未用到)
     */
    @Override
    public LsResponse getRecipeInfo(User user, Integer id) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (id != null) {
                log.info(user.getUsername() + "正在查看餐厅菜谱的详情");
            }
            Recipe recipe = recipeMapper.selectByPrimaryKey(id);
            List<RecipeMaterial> recipeDetails = recipeMaterialMapper.getRecipeDetails(recipe.getRecipeId());
            if (recipeDetails != null) {
                recipe.setMaterials(recipeDetails);
            }
            if (recipe != null) {
                lsResponse.setMessage(CodeMessage.RECIPE_GETRECIPEDETAILS_SUCCESS.getMsg());//返回后台成功的信息
                lsResponse.setData(recipe);
                log.info(user.getUsername() + "正在查看餐厅菜谱的详情，查看成功");
            } else {
                lsResponse.setMessage(CodeMessage.RECIPE_GETRECIPEDETAILS_SUCCESS.getMsg());//返回后台成功的信息
                lsResponse.setSuccess(false);
                log.info(user.getUsername() + "正在查看餐厅菜谱的详情，查看失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.info(user.getUsername() + "正在查看餐厅菜谱的详情，发生异常" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getAccessNumber() {
        LsResponse lsResponse = LsResponse.newInstance();
        int number = 10000000 + recipeMapper.getRecipeMaxID();
        lsResponse.setData(number + "");
        return lsResponse;
    }

    /**
     * 获取烹饪类型和单位信息
     *
     * @return
     */
    @Override
    public LsResponse getUnitAndCookType() {
        LsResponse lsResponse = LsResponse.newInstance();
        Map<String, String[]> map = new HashMap<>();
        map.put("1", Values.cookTypeName);
        List<Unit> units = materialMapper.getUnits();
        String[] unitName = new String[units.size()];
        for (int i = 0; i < units.size(); i++) {
            unitName[i] = units.get(i).getUnit();
        }
        map.put("2", unitName);
        lsResponse.setData(map);
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeMINI(String keyword, String recipePlanId, Integer recipeType, Integer categoryId, String canteenId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        StringBuilder sb = new StringBuilder();
        try {
            log.info("添加菜品计划时，获取菜品信息，餐厅编号为：" + canteenId + "");
            if (keyword != null && !"".equals(keyword)) {
                keyword = sb.append("%").append(new String(keyword.getBytes("iso8859-1"), "utf-8")).append("%").toString();
            } else {
                keyword = "";
            }
            if (canteenId != null && !"".equals(canteenId)) {
                canteenId = new String(canteenId.getBytes("iso8859-1"), "utf-8");
            } else {
                canteenId = "";
            }
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = recipeMapper.getRecipeMINICount(keyword, categoryId, canteenId);
            List<Recipe> recipes = recipeMapper.getRecipeMINI(keyword, categoryId, canteenId, offSet, pageSize);
            if (recipePlanId != null && !("").equals(recipePlanId)) {
                List<RecipePlanItems> items = recipePlanItemsMapper.getRecipeByRecipePlanIdAndRecipeType(recipePlanId, recipeType);
                for (Recipe recipe : recipes) {
                    if (items != null && items.size() > 0) {
                        for (RecipePlanItems it : items) {
                            if (recipe.getRecipeId().equals(it.getRecipeId())) {
//                                log.info("MINI方法中的recipeId和计划详情中的recipeId相同", JSON.toJSON(it));
                                log.info("MINI方法中的recipeId和计划详情中的recipeId相同" + it.isHot());
                                recipe.setSurplus(it.getSurplus());
                                recipe.setGuidePrice(it.getPriceDay());
                                recipe.setMarketPrice(it.getMarketPriceDay());
                                log.info("菜品编号为：" + it.getRecipeId() + "是否是热销：" + it.isHot());
                                recipe.setHot(it.isHot());
//                                if (it.isHot()){
//                                    log.info("菜品编号为："+it.getRecipeId()+"是热销，计划单号为"+it.getRecipePlanId());
//                                    recipe.setHot(true);
//                                }else {
//                                    log.info("菜品编号为："+it.getRecipeId()+"不是热销菜品，计划单号为"+it.getRecipePlanId());
//                                    recipe.setHot(it.isHot());
//                                }
                                recipe.setChecked(true);
                                break;
                            }
                        }
                    }
                }
            }
            if (recipes != null && recipes.size() > 0) {
                lsResponse.setData(recipes);
                lsResponse.setTotalCount(total);
                lsResponse.setMessage("查询成功");
            } else {
                lsResponse.setAsFailure();
                lsResponse.setMessage("数据查询失败");
                log.error("数据查询失败,总条数为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("WEb端，添加菜品计划时，查询菜品列表失败，异常信息为" + e.toString());
        }
        return lsResponse;
    }

    /*------------------------------------------------------------------H5需要用到接口-----------------------------------------------------------------------------------*/
    @Override
    public LsResponse getRecipeInfoByRecipeIdH5(Associator associator, String recipeId, String canteenId, String dinnerTime, Integer eatType, String recipePlanId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (dinnerTime == null || dinnerTime.equals("")) {
                dinnerTime = Dates.now("yyyy-MM-dd");
            }
            String now = Dates.now("yyyyMMdd");
            Recipe recipe = recipeMapper.getRecipeByRecipeId(recipeId);
            if (recipe != null) {
                List<RecipePlanItems> items = recipePlanItemsMapper.getRecipePlanItemsList(recipePlanId);//根据菜谱计划单号查询所有的计划详情
                if (items != null && items.size() > 0) {
                    for (RecipePlanItems planItems : items) {
                        if (planItems.getRecipe().equals(recipeId) && planItems.getRecipeType().equals(eatType)) {
                            log.info("计划中的菜品编号和就餐时间相同，进入赋值过程，前台传过来的eatType为:" + eatType);
                            recipe.setGuidePrice(planItems.getPriceDay());
                            recipe.setMarketPrice(planItems.getMarketPriceDay());
                            if (Long.valueOf(dinnerTime.replace("-", "")) < Long.valueOf(now)) {
                                recipe.setSurplus(0);
                                recipe.setNum(0);
                            } else {
                                recipe.setSurplus(planItems.getSurplus());
                            }
                            recipe.setSurplus(planItems.getSurplus());
                            break;
                        }
                    }
                }
                Integer count = recipeOrderMapper.getRecipeMonthSales(canteenId, recipe.getRecipeId());
                Integer type = eatType + 1;
//                Cart cart = cartMapper.getCartByCanAndGoodIdAndAsso(canteenId,recipe.getRecipeId(),associator.getAssociatorId(),dinnerTime,eatType+1);
                log.info("type的值为=============================================" + type);
                Cart cart = cartMapper.getCartByCanAndGoodIdAndAsso(canteenId, recipe.getRecipeId(), associator.getAssociatorId(), dinnerTime, type);//根据餐厅编号、菜品编号、会员编号和就餐时间,餐次查询购物车
                if (count != null) {
                    recipe.setMonthSale(count);
                }
                if (cart != null) {
                    log.info("会员编号为：" + associator.getAssociatorId() + "的购物车不为空，菜品编号为" + recipe.getRecipeId() + "的" + eatType + "的数量为" + cart.getQuantity());
                    recipe.setNum(cart.getQuantity());
                    if (recipe.getNum() > 0) {
                        recipe.setChecked(true);
                    }
                }
               /* List<RecipeMaterial> recipeDetails = recipeMaterialMapper.getRecipeDetails(recipeId);//查看菜品所需要的材料信息
                if (recipeDetails != null){
                    recipe.setMaterials(recipeDetails);
                }*/
                RecipeAttribute recipeAttribute = recipeAttributeMapper.getAttributeByRecipeId(recipeId);
                if (recipeAttribute != null) {
                    recipe.setRecipeAttribute(recipeAttribute);
                }
                List<RecipeDetailsImg> recipeDetailsImgs = recipeDetailsImgMapper.getRecipeDetailsImgs(recipeId);
                if (recipeDetailsImgs != null && recipeDetailsImgs.size() > 0) {
                    recipe.setRecipeDetailsImgs(recipeDetailsImgs);
                }
                lsResponse.setData(recipe);
                lsResponse.setMessage(CodeMessage.RECIPE_GETRECIPEDETAILS_SUCCESS.getMsg());
                log.info("查看餐厅菜谱的详情成功");
            } else {
                lsResponse.setMessage(CodeMessage.RECIPE_GETRECIPEDETAILS_SUCCESS.getMsg());
                lsResponse.setSuccess(false);
                log.info("查看餐厅菜谱的详情失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setSuccess(false);
            lsResponse.setErrorCode("500");
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.info("H5端，正在查看餐厅菜谱的列表，发生异常，异常信息为：" + e.toString());
        }
        return lsResponse;
    }

    /*------------------------------------------------------------------微信小程序需要用到接口---------------------------------------------------------------------------*/
    @Override
    public LsResponse getRecipeInfoByRecipeIdWx(String recipeId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            recipeId = new String(recipeId.getBytes("iso8859-1"), "utf-8");//设置编码
            Recipe recipe = recipeMapper.getRecipeByRecipeId(recipeId);
            if (recipe != null) {
                lsResponse.setData(recipe);//返回数据
                log.info("微信小程序，正在查看菜品详情，成功");
                lsResponse.setMessage(CodeMessage.RECIPE_GETRECIPEDETAILS_SUCCESS.getMsg());//返回后台成功的信息
            } else {
                lsResponse.setSuccess(false);
                log.info("微信小程序，正在查看菜品详情，失败");
                lsResponse.setMessage(CodeMessage.RECIPE_GETRECIPEDETAILS_SUCCESS.getMsg());//返回后台错误的信息
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setErrorCode("500");
            lsResponse.setAsFailure();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("微信小程序，正在查看菜品详情，失败,原因为：", e.toString());
        }
        return lsResponse;
    }

    /*------------------------------------------------------------------菜品营养成分管理部分----------------------------------------------------------------------------*/

    @Override
    public LsResponse getNutritionByRecipeId(String recipeId) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            if (recipeId != null && !"".equals(recipeId)) {
                recipeId = new String(recipeId.getBytes("iso8859-1"), "utf-8");
            } else {
                return lsResponse.checkSuccess(false,CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
            }
            Recipe recipe = recipeMapper.getRecipeByRecipeId(recipeId);
            if (recipe != null){
                List<Nutrition> nutritionList = nutritionMapper.getNutritionListByRecipeId(recipeId);
                if (nutritionList != null && nutritionList.size() > 0) {
                    recipe.setNutritions(nutritionList);
                }
                recipe.setNutritions(nutritionList);
                lsResponse.setData(recipe);
            }else {
                lsResponse.checkSuccess(false,"改菜品不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("WEB端，根据菜品编号，查询菜品的营养成分失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse recipeAddNutrition(User user, Ingredient ingredient) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (ingredient == null) {
            return lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        try {
            Integer insertFlag = ingredientMapper.insertSelective(ingredient);
            if (insertFlag > 0) {
                lsResponse.setMessage("添加成功");
                List<Nutrition> nutritions = nutritionMapper.getNutritionListByRecipeId(ingredient.getMenuid());
                if (nutritions != null && nutritions.size() > 0){
                    lsResponse.setData(nutritions);
                }
            } else {
                lsResponse.checkSuccess(false, "添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("菜品添加营养成分失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse recipeDeleteNutrition(User user,String ingredientId) {
        LsResponse lsResponse = LsResponse.newInstance();
        if (ingredientId == null) {
            return lsResponse.checkSuccess(false, CodeMessage.ASSOCIATOR_NO_MESSAGE.name());
        }
        try {
            Integer nid = Integer.valueOf(ingredientId);
            Integer insertFlag = ingredientMapper.deleteByPrimaryKey(nid);
            if (insertFlag > 0) {
                lsResponse.setMessage("删除成功");
            } else {
                lsResponse.checkSuccess(false, "删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("菜品删除营养成分失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse getRecipeNutritionList(User user, String keyword, String canteenId, Integer categoryId, Integer pageNum, Integer pageSize) {
        LsResponse lsResponse = LsResponse.newInstance();
        try {
            keyword= StringsUtil.encodingChange(keyword);
            String[] canteenIds = user.getCanteenIds().split(",");
            canteenId=StringsUtil.encodingChange(canteenId);
            int offSet = 0;
            if (pageSize != null && pageNum != null) {
                offSet = (pageNum - 1) * pageSize;
            }
            int total = recipeMapper.getRecipeCount(keyword, canteenId, categoryId, canteenIds);
            List<Recipe> rlist = recipeMapper.getRecipeList(keyword, canteenId, categoryId, canteenIds, offSet, pageSize);
            if (rlist != null && rlist.size() > 0) {
                for (Recipe recipe : rlist) {
                    Float calorieAll = 0f;//卡路里
                    Float proteinAll = 0f;//蛋白质
                    Float fatAll = 0f;//脂肪
                    Float carbonhydrateAll = 0f;//碳水化合物
                    Float vcAll = 0f;//维生素C
                    List<Nutrition> nutritions = nutritionMapper.getNutritionListByRecipeId(recipe.getRecipeId());
                    if (nutritions != null && nutritions.size() > 0) {
                        for (Nutrition nutrition : nutritions) {
                            calorieAll += nutrition.getCalorie() * nutrition.getAmount();
                            proteinAll += nutrition.getProtein() * nutrition.getAmount();
                            fatAll += nutrition.getFat() * nutrition.getAmount();
                            carbonhydrateAll += nutrition.getCarbonhydrate() * nutrition.getAmount();
                            vcAll += nutrition.getVc() * nutrition.getAmount();
                        }
                    }
                    recipe.setCalorieAll(calorieAll * 0.01f);
                    recipe.setProteinAll(proteinAll * 0.01f);
                    recipe.setFatAll(fatAll * 0.01f);
                    recipe.setCarbonhydrateAll(carbonhydrateAll * 0.01f);
                    recipe.setVcAll(vcAll * 0.01f);
                }
                lsResponse.setTotalCount(total);
                lsResponse.setData(rlist);
            } else {
                lsResponse.setAsFailure();
                if (keyword != null && !"".equals(keyword)) {
                    lsResponse.setMessage("该菜品不存在");
                } else if (categoryId != null) {
                    lsResponse.setMessage("该分类中没有菜品信息");
                } else {
                    lsResponse.setMessage("该菜品列表暂无数据");
                }
//                lsResponse.checkSuccess(false,CodeMessage.EMPTY_DATA.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
            log.error("WEB端，查询菜品的营养成分失败，失败原因为：" + e.toString());
        }
        return lsResponse;
    }
}
