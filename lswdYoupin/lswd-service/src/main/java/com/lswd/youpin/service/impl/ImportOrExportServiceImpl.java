package com.lswd.youpin.service.impl;

import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.dao.PersonInfoMapper;
import com.lswd.youpin.dao.RegionMapper;
import com.lswd.youpin.dao.lsyp.*;
import com.lswd.youpin.model.Canteen;
import com.lswd.youpin.model.PersonInfo;
import com.lswd.youpin.model.Region;
import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsyp.*;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.service.ImportOrExportService;
import com.lswd.youpin.service.PersonInfoService;
import com.lswd.youpin.utils.DateValidity;
import com.lswd.youpin.utils.PoiExcelExport;
import com.lswd.youpin.utils.Values;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.lswd.youpin.utils.Values.cookType;

/**
 * Created by zhenguanqi on 2017/6/30.
 */
@Service
public class ImportOrExportServiceImpl implements ImportOrExportService {
    private final Logger log = LoggerFactory.getLogger(ImportOrExportServiceImpl.class);

    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private RecipeCategoryMapper recipeCategoryMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private DiskLabelMapper diskLabelMapper;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    /**
     * 获取菜谱分类的名称
     * @return
     */
    public String[] getRecipeCategoryName(String canteenId){
        List<RecipeCategory> recipeCategories = recipeCategoryMapper.getRecipeCategoryAll(canteenId);
        String[] s = new String[recipeCategories.size()];
        for (int i=0;i<recipeCategories.size();i++){
           s[i] = recipeCategories.get(i).getName();
        }
        return s;
    }

    /**
     * 获取部门名称
     * @return
     */
    public String[] getDepartmentName(){
        List<Department> departments = departmentMapper.getDepartmentAll();
        String[] s = new String[departments.size()];
        for (int i=0;i<departments.size();i++){
            s[i] = departments.get(i).getDepartmentName();
        }
        return s;
    }

    /**
     * 获取材料分类名称
     * @return
     */
    public String[] getMeterialCategoryName(){
//        List<MaterialCategory> materialCategories = materialMapper.selectCategoryList();
//        String[] s = new String[materialCategories.size()];
//        for (int i=0;i<materialCategories.size();i++){
//            s[i] = materialCategories.get(i).getName();
//        }
        return null;
    }

    /**
     * 获取园区名称
     * @return
     */
    public String[] getRegionName(){
        List<Region> regions = regionMapper.selectRegions();
        String[] s = new String[regions.size()];
        for (int i=0;i<regions.size();i++){
            s[i] = regions.get(i).getRegionName();
        }
        return s;
    }


    /**
     * 获取单位名称
     * @return
     */
    public String[] getUnitsName(){
        List<Unit> units = materialMapper.getUnits();
        String[] s = new String[units.size()];
        for (int i=0;i<units.size();i++){
            s[i] = units.get(i).getUnit();
        }
        return s;
    }

    /**
     * 导出模板(菜品分类没做好)
     * @param flag
     * @param response
     * @return
     */
    @Override
    public LsResponse exportExeclModel(Integer flag, HttpServletResponse response) {
        LsResponse lsResponse = LsResponse.newInstance();
        String fileName = "" ;
        try {
            switch (flag){
                case 0 : fileName = "菜品导入模版";break;
                case 1 : fileName = "员工导入模版";break;
                case 2 : fileName = "食材导入模版";break;
                default : break;
            }
            PoiExcelExport pee = new PoiExcelExport(response,fileName,"sheet1");
            if (flag == 0){
                String titleColumn[] = {"recipeName","cookType","guidePrice","marketPrice","categoryName","materialType","cookDetail"};
                String titleName[] = {"菜品名称*","烹饪方式","指导价格*","市场价格*","菜品分类名称*","制作步骤"};
                int titleSize[] = {20,16,16,16,20,75};
                pee.wirteExcelModel(flag,"菜品资料详情导入模版（勿按要求）",titleColumn, titleName, titleSize,null,getRecipeCategoryName(""),null);
            }else if (flag == 1){
                String titleColumn[] = {"employeeName","telephone","regionName","cardNumber","sex","birthday","status","duty","job","packStartTime"};
                String titleName[] = {"员工姓名*","手机号*","所属园区*","员工卡号*","性别","生日","就职状态","职责","岗位","入职时间"};
                int titleSize[] = {14,16,14,16,12,18,15,20,20,18};
                pee.wirteExcelModel(flag,"员工资料详情导入模版（勿按要求）",titleColumn, titleName, titleSize, null,null,getRegionName());
            }else if(flag == 2){
                String titleColumn[] = {"materialName","categoryName","price","stock","units","level","seasonName","region","specification"};
                String titleName[] = {"材料名称*","材料分类名称*","单价*","库存","单位","材料等级","材料季节","材料产地","材料描述信息"};
                int titleSize[] = {20,20,14,14,14,14,18,30,38};
                pee.wirteExcelModel(flag,"材料资料详情导入模版（勿按要求）",titleColumn, titleName, titleSize, null,getMeterialCategoryName(),getUnitsName());
            }
        } catch (Exception e) {
            lsResponse.setAsFailure();
            lsResponse.setErrorCode("500");
            lsResponse.setMessage("导出失败，失败原因为："+e.toString());
            e.printStackTrace();
        }
        return lsResponse;
    }

    /**
     * 导出数据库的内容
     * @param flag
     * @param response
     * @return
     */
    @Override
    public LsResponse exportExeclList(String[] canteenIds, List<Canteen> canteens, Integer flag, HttpServletResponse response) {
        LsResponse lsResponse = LsResponse.newInstance();
        String fileName = "" ;
        try {
            switch (flag){
                case 0 : fileName = "菜品导出详情预览";break;
                case 1 : fileName = "员工导出详情预览";break;
                case 2 : fileName = "食材导出详情预览";break;
                default : break;
            }
            PoiExcelExport pee = new PoiExcelExport(response,fileName,"sheet1");
            String[] regions = new String[canteens.size()];
            for (int i = 0; i<canteens.size(); i++){
                regions[i] = canteens.get(i).getRegionId();
            }
            if (flag == 0){//菜品
                List<Recipe> recipes = recipeMapper.getRecipeListAll(canteenIds);
                for (Recipe recipe : recipes){
                    recipe.setCategoryName(recipeCategoryMapper.selectByPrimaryKey(recipe.getCategoryId()).getName());
                    recipe.setCookTypeName(Values.cookTypeName[recipe.getCookType()]);
                    recipe.setCreateTimeString(Dates.format(recipe.getCreateTime(),"yyyy-MM-dd"));
                    recipe.setUpdateTimeString(Dates.format(recipe.getUpdateTime(),"yyyy-MM-dd"));
                }
                String titleColumn[] = {"recipeId","recipeName","guidePrice","marketPrice","cookTypeName","categoryName","cookDetail","createUser","updateUser","createTimeString","updateTimeString"};
                String titleName[] = {"菜品编号","菜品名称","指导价格","市场价格","烹饪类型","菜品分类名称","制作步骤","创建人","更新人","创建时间","更新时间"};
                int titleSize[] = {13,18,10,10,12,16,40,15,15,18,18};
                pee.wirteExcelList(flag,"菜谱资料详情导出预览表",titleColumn, titleName, titleSize, recipes);
            }else if (flag == 1){//员工
                List<Employee> employees = employeeMapper.getEmployessAll(regions);
                for (Employee employee : employees){
                    employee.setRegionName(regionMapper.getRegionByRegionId(employee.getRegionId()).getRegionName());
                    employee.setSexName(employee.getSex()?"男":"女");
                    employee.setStatusName(Values.statusEmployeeName[employee.getStatus()]);
                    employee.setEducationName(Values.educationName[employee.getEducation()]);
                    employee.setBirthdayString(Dates.format(employee.getBirthday(),"yyyy-MM-dd"));
                    employee.setPackStartTimeString(Dates.format(employee.getPackStartTime(),"yyyy-MM-dd"));
                    employee.setCreateTimeString(Dates.format(employee.getCreateTime(),"yyyy-MM-dd"));
                    employee.setUpdateTimeString(Dates.format(employee.getUpdateTime(),"yyyy-MM-dd"));
                }
                String titleColumn[] = {"employeeId","employeeName","cardNumber","regionName","sexName","birthdayString","statusName","duty","telephone","job","packStartTimeString","political","major","weixin","email","qq","nation","school","educationName","updateUser","createTimeString","updateTimeString"};
                String titleName[] = {"员工编号","员工姓名","员工卡号","所属园区","性别","生日","就职状态","职责","手机号","岗位","入职时间","政治面貌","专业","微信","邮箱","QQ","民族","学校","教育经历","更新人","创建时间","更新时间"};
                int titleSize[] = {11,12,12,13,10,16,14,15,14,16,12,12,14,16,16,16,10,15,12,13,14,14};
                pee.wirteExcelList(flag,"员工资料详情导出预览表",titleColumn, titleName, titleSize, employees);
            }else if(flag == 2){//食材
//                List<Material> materials = materialMapper.getMaterialsAll(canteenIds);
//                for (Material material : materials){
//                    material.setCategoryName(materialMapper.getCategoryByCategoryId(material.getCategoryId()).getName());
//                    material.setUnitName(materialMapper.getUnitById(material.getUnit()).getUnit());
//                    material.setSeasonName(Values.seasonName[material.getSeason()]);
//                    material.setCreateTimeString(Dates.format(material.getCreateTime(),"yyyy-MM-dd"));
//                    material.setUpdateTimeString(Dates.format(material.getUpdateTime(),"yyyy-MM-dd"));
//                }
//                String titleColumn[] = {"materialId","materialName","categoryName","level","stock","unitName","price","region","seasonName","specification","updateUser","createTimeString","updateTimeString"};
//                String titleName[] = {"食材编号","食材名称","分类名称","食材等级","库存","单位","价格","食材产地","食材季节","材料描述信息","更新人","创建时间","更新时间"};
//                int titleSize[] = {11,12,12,11,10,10,10,20,10,30,20,14,14};
//                pee.wirteExcelList(flag,"食材资料详情导出预览表",titleColumn, titleName, titleSize, materials);
                }
        } catch (Exception e) {
            lsResponse.setAsFailure();
            lsResponse.setErrorCode("500");
            lsResponse.setMessage("导出失败，失败原因为："+e.toString());
            log.error("导出数据库资料失败，失败原因为："+e.toString());
            e.printStackTrace();
        }
        return lsResponse;
    }


    //根据名称来获取字符串数组的索引
    private int getIndex(String[] strings,String name){
        int i = 0;
        for (;i<strings.length;i++){
            if (strings[i].equals(name)) break;
        }
        return i;
    }

    private Date createTime = Dates.now();//表格中的字段，创建时间
    private Date updateTime = Dates.now();//表格中的字段，更新时间
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    private Map<String,String> errorInfo = new HashMap<>();//用来存储错误信息
    private int nullrownum = 0;//空行的记录数
    private int successCount = 0;//用来记录成功的次数
    private int failCount = 0;//用来记录失败的次数

    /**
     * 判断是否是空行
     * @param arrys
     * @return
     */
    private boolean isNullRow(int[] arrys){
        int count = 0;
        for (int i = 0; i< arrys.length ; i++){
            if (arrys[i] == i){
                count ++;
            }
        }
        if (count == arrys.length){
            return true;
        }
        return false;
    }

    /**
     * 数据恢复 function：全局变量都恢复默认值！！！
     */
    private void dataRecovery(){
        nullrownum = 0;
        successCount = 0;
        failCount = 0;
        errorInfo =  new HashMap<>();
    }

    /**
     * 读取Recipe，菜品信息
     * @param user
     * @return
     */
    private LsResponse readRecipe(User user,String canteenId,MultipartFile file){
        LsResponse lsResponse =  LsResponse.newInstance();
        HSSFWorkbook workbook = null;
        HSSFSheet sheet;
        try {
            workbook = new HSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        List<Recipe> recipes = new ArrayList<>();
        for (Row row : sheet){
            int rowNum = row.getRowNum();
            if(rowNum == 0 || rowNum == 1 || rowNum == 2) continue;//前三行是标题以及各种规范，不需要循环判断
            int[] cellnotnull = new int[sheet.getRow(2).getLastCellNum()];//获取第三行的单元格数，最后一个，recipe目前共6列，cellnotnull的length为6
            for (int i = 0; i< cellnotnull.length ; i++){//设置cellnotnull的所有value都 == 7，为判断单元格是否为空，做准备
                cellnotnull[i] = cellnotnull.length + 1;
            }
            for (int j = 0; j < sheet.getRow(2).getLastCellNum(); j++) { //首先判断该单元格是否有值，如果有值，设置cellnotnull数组的值=该单元格的列号
                String value = getCellValue(row.getCell(j));
                if (value.equals("")) {
                    cellnotnull[j] = j;
                }
            }
            //如果全部是空行，空行次数加1，然后退出循环
            if(isNullRow(cellnotnull)){
                nullrownum++;
                continue;
            }

            String recipeName = "";//菜品名称
            if (cellnotnull[0] != 0){
                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING){//判断输入的菜谱类型是否是String类型，如果不是，退出循环，次数加1
                    recipeName = row.getCell(0).getStringCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:菜谱名称不能只为数字，必须带有汉字，故您本条数据添加失败");
                    failCount++;
                    continue;
                }
            }

            String cookTypeName = "";//烹饪类型
            if (cellnotnull[1] != 1){
                cookTypeName = row.getCell(1).getStringCellValue();
            }

            double guidePrice = 0;//指导价格
            if (cellnotnull[2] != 2){
                if (row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC){
                    guidePrice = row.getCell(2).getNumericCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:指导价格必须只能是数字，您输入了非法字符，故本条数据添加失败");
                    failCount++;
                    continue;
                }
            }

            double marketPrice = 0;//市场价格
            if (cellnotnull[3] != 3){
                if (row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC){
                    marketPrice = row.getCell(3).getNumericCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:市场价格必须只能是数字，您输入了非法字符，故本条数据添加失败");
                    failCount++;
                    continue;
                }
            }

            String categoryName = "";//分类名称
            if (cellnotnull[4] != 4){
                categoryName = row.getCell(4).getStringCellValue();
            }

            String cookDetail = "";//制作步骤
            if (cellnotnull[5] != 5){
                if (row.getCell(5).getCellType() == Cell.CELL_TYPE_STRING){//判断输入的菜谱类型是否是String类型，如果不是，退出循环，次数加1
                    cookDetail = row.getCell(5).getStringCellValue();
                }else{
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:制作步骤可以只为文字，也可以是文字和数字，但不能仅仅是数字，故您本条数据添加失败");
                    failCount++;
                    continue;
                }
            }

            int cookType = 0;
            if (cookTypeName != ""){
//                cookType = getIndex(cookType,cookTypeName);
            }

            //根据菜品分类名称得到菜品分类信息
            RecipeCategory recipeCategory = new RecipeCategory();
            if (categoryName != ""){
                recipeCategory = recipeCategoryMapper.getRecipeCategoryByName(categoryName,canteenId);
            }

            if (recipeName != "" && canteenId != null){
                Recipe recipe = new Recipe((float)marketPrice,recipeName,(short)cookType,"","",(short)0,
                        (float)guidePrice,recipeCategory.getId(),canteenId,cookDetail,false, user.getUsername(), user.getUsername(),Dates.now(),Dates.now());
                recipes.add(recipe);
            }
        }
        if (recipes != null && recipes.size() > 0){
            for (Recipe recipe : recipes){
                int number = 1000000 + recipeMapper.getRecipeMaxID();
                recipe.setRecipeId("r"+number);
                int insertflag = recipeMapper.insertRecipe(recipe);
                if (insertflag > 0){//表示插入成功
                    successCount++;
                }
            }
            lsResponse.setMessage("添加成功，总共添加"+successCount+"条记录");
            lsResponse.setTotalCount(successCount);
        }else {//如果只有3行表头，就会走这个地方~
            lsResponse.setAsFailure();
            lsResponse.setMessage("没有获取到数据，请根据提示进行添加，必填项务必填正确~");
            dataRecovery();
        }
        if (errorInfo != null && errorInfo.size() > 0){
            lsResponse.setMessage("不好意思，你上传的文件没有按照规定要求进行上传，原因可能为：很多空单元格或数据非法，请按照提示进行更改，谢谢");
            lsResponse.setAsFailure();
            lsResponse.setData(errorInfo);
            lsResponse.setTotalCount(errorInfo.size());
            dataRecovery();
        }
        return  lsResponse;
    }

    /**
     * 读取Employee，员工信息
     * @param user
     * @return
     */
    private LsResponse readEmployee(User user,String canteenId,MultipartFile file){
        LsResponse lsResponse = LsResponse.newInstance();
        HSSFWorkbook workbook = null;
        HSSFSheet sheet;
        try {
            workbook = new HSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        List<Employee> employees = new ArrayList<>();
        for (Row row : sheet){
            int rowNum = row.getRowNum();
            if(rowNum == 0 || rowNum == 1 || rowNum == 2) continue;//前三行是标题以及各种规范，不需要循环判断
            int[] cellnotnull = new int[sheet.getRow(2).getLastCellNum()];//获取第三行的单元格数，最后一个，employee目前共11列，cellnotnull的length为11
            for (int i = 0; i< cellnotnull.length ; i++){//设置每个cellnotnull的value值为11+1 = 12
                cellnotnull[i] = cellnotnull.length + 1;
            }
            for (int j = 0; j < sheet.getRow(2).getLastCellNum(); j++) { //首先判断该单元格是否有值，如果有值，设置int类型数组的值=该单元格的列号
                String value = getCellValue(row.getCell(j));
                if (value.equals("")) {
                    cellnotnull[j] = j;
                }
            }
            //如果全部是空行，空行次数加1，然后退出循环
            if(isNullRow(cellnotnull)){
                nullrownum++;
                continue;
            }
            String employeeName = "";//员工姓名
            if (cellnotnull[0] != 0){
                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING){
                    employeeName = row.getCell(0).getStringCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:员工姓名，必须带有汉字或者英文，故您本条数据添加失败，你家员工的名字只有数字呀，，，");
                    failCount++;
                    continue;
                }
            }

            double telephoneDouble = 0;//手机号
            if (cellnotnull[1] != 1){
                if (row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC){
                    telephoneDouble = row.getCell(1).getNumericCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:输入手机号能不能靠谱点，输入字符是什么意思？故您本条数据添加失败");
                    failCount++;
                    continue;
                }
            }

            String regionName = "";//园区名称
            if (cellnotnull[2] != 2){
                regionName = row.getCell(2).getStringCellValue();
            }
            String cardNumber = "";//员工卡号
            if (cellnotnull[3] != 3){
                if (row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING){
                    cardNumber = row.getCell(3).getStringCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:请输入正确的员工卡号");
                    failCount++;
                    continue;
                }
            }

            String sexName = "";//性别
            if (cellnotnull[4] != 4){
                sexName = row.getCell(4).getStringCellValue();
            }


            Date birthday = null;
            String birthdayString = "";//生日
            if (cellnotnull[5] != 5){
                if (row.getCell(5).getCellType() == Cell.CELL_TYPE_STRING){
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:请按照要求正确的输入日期，因您输入有误，所以您本条数据添加失败");
                    failCount++;
                    continue;
                }else if (HSSFDateUtil.isCellDateFormatted(row.getCell(6))){
                    birthdayString = getDate(row.getCell(6));
                    try {
                        birthday =  dateformat.parse(birthdayString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:请按照要求正确的输入日期，因您输入有误，所以您本条数据添加失败");
                        failCount++;
                        continue;
                    }
                }
            }

            String statusName = "";
            if (cellnotnull[6] != 6){
                statusName = row.getCell(6).getStringCellValue();
            }

            Object duty = "";
            if (cellnotnull[7] != 7){
                if (row.getCell(7).getCellType() == Cell.CELL_TYPE_STRING){
                    duty = row.getCell(7).getStringCellValue();
                }else if(row.getCell(7).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    duty = row.getCell(7).getNumericCellValue();
                }else{
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:员工职责，必须带有汉字或者英文，故您本条数据添加失败，你家员工的职责只有数字呀，，，只让人家干数字呀");
                    failCount++;
                    continue;
                }
            }

            Object job = "";
            if (cellnotnull[8] != 8){
                if (row.getCell(8).getCellType() == Cell.CELL_TYPE_STRING){
                    job= row.getCell(8).getStringCellValue();
                }else if(row.getCell(8).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    job = row.getCell(8).getNumericCellValue();
                }else{
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:员工岗位，必须带有汉字或者英文，故您本条数据添加失败！！！");
                    failCount++;
                    continue;
                }
            }

            Date packStartTime = null;
            String packStartTimeString = "";
            if (cellnotnull[9] != 9){
                if (row.getCell(9).getCellType() == Cell.CELL_TYPE_STRING ){
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:请按照要求正确的输入日期，因您输入有误，所以您本条数据添加失败");
                    failCount++;
                    continue;
                }else if (HSSFDateUtil.isCellDateFormatted(row.getCell(8))){
                    packStartTimeString = getDate(row.getCell(9));
                    try {
                        packStartTime =  dateformat.parse(packStartTimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
           /* Department department = new Department();
            if (departmentName != "" && !department.equals("")){
                department = departmentMapper.getDepartmentByDepartmentName(departmentName);
            }*/

            Region region = new Region();
            if (regionName != ""){
                region = regionMapper.getRegionByRegionName(regionName);
            }
            boolean sex = true;
            if (sexName.equals("女")) sex = false;
            int status = 0;
            if (statusName != ""){
                status = getIndex(Values.statusEmployeeName,statusName);
            }
            String telephone = "";
            if (telephoneDouble !=0){
                DecimalFormat df = new DecimalFormat("#");//用来解决short类型的手机号的问题
                telephone = df.format(telephoneDouble);
                if (!DateValidity.checkCellphone(telephone)){
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:手机号输入不合法！！！");
                    failCount++;
                    continue;
                }
            }
            if (employeeName != "" && telephone != "" && region != null && cardNumber != "" ){//数据库表中，员工姓名、手机号、所属区域、员工卡号不能为空
                Employee employee = new Employee(employeeName,region.getRegionId(),sex,birthday,(short)status,duty.toString(),telephone,
                        job.toString(),packStartTime, false,user.getUsername(),Dates.now(),Dates.now(),cardNumber);
                employees.add(employee);
            }
        }
        if (employees != null && employees.size() > 0){
            for (Employee employee : employees){
                int id=employeeMapper.selectLastId();
                employee.setEmployeeId("e"+String.valueOf(1001+id));
                int insertflag = employeeMapper.insertEmployee(employee);
                if (insertflag > 0){//表示插入成功
                    successCount++;
                }
                lsResponse.setMessage("添加成功，总共添加"+successCount+"条记录");
                lsResponse.setTotalCount(successCount);
            }
        }else {//如果只有3行表头，就会走这个地方~
            lsResponse.setAsFailure();
            lsResponse.setMessage("没有获取到数据，请根据提示进行添加，必填项务必填正确~");
            dataRecovery();
        }
        if (errorInfo != null && errorInfo.size() > 0){
            lsResponse.setMessage("不好意思，你上传的文件没有按照规定要求进行上传，原因可能为：很多空单元格或数据非法，请按照提示进行更改，谢谢");
            lsResponse.setData(errorInfo);
            lsResponse.setAsFailure();
            lsResponse.setTotalCount(errorInfo.size());
            dataRecovery();
        }
        return  lsResponse;
    }

    /**
     * 读取Material，材料信息
     * @param user
     * @return
     */
    private LsResponse readMaterial(User user,String canteenId,MultipartFile file){
        LsResponse lsResponse = LsResponse.newInstance();
        HSSFWorkbook workbook = null;
        HSSFSheet sheet;
        try {
            workbook = new HSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        List<Material> materials = new ArrayList<>();
        for (Row row : sheet){
            int rowNum = row.getRowNum();
            if(rowNum == 0 || rowNum == 1 || rowNum == 2) continue;//前三行是标题以及各种规范，不需要循环判断
            int[] cellnotnull = new int[sheet.getRow(2).getLastCellNum()];//材料模板的第三行的LastCellNum = 9，所有cellnotnull的length = 9
            for (int i = 0; i< cellnotnull.length ; i++){//设置cellnotnull的所有value都 == 10，为判断单元格是否为空，做准备
                cellnotnull[i] = cellnotnull.length + 1;
            }
            for (int j = 0; j < sheet.getRow(2).getLastCellNum(); j++) { //首先判断该单元格是否有值，如果有值，设置int类型数组的值=该单元格的列号
                String value = getCellValue(row.getCell(j));
                if (value.equals("")) {
                    cellnotnull[j] = j;
                }
            }
            //如果全部是空行，空行次数加1，然后退出循环
            if(isNullRow(cellnotnull)){
                nullrownum++;
                continue;
            }
            String materialName = "";//材料名称
            if (cellnotnull[0] != 0){
                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING){//判断输入的材料名称是否是String类型，如果不是，退出循环，次数加1
                    materialName = row.getCell(0).getStringCellValue();
                }else {
                    errorInfo.put("第"+rowNum+1+"行录入失败","原因是:材料名称不能只为数字，必须带有汉字，故您本条数据添加失败");
                    failCount++;
                    continue;
                }
            }
            String categoryName = "";//分类名称
            if (cellnotnull[1] != 1){
                categoryName = row.getCell(1).getStringCellValue();
            }
            double price = 0;//价格
            if (cellnotnull[2] != 2){
                if (row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC){
                    price = row.getCell(2).getNumericCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:材料价格必须只能是数字，您输入了非法字符，故本条数据添加失败");
                    failCount++;
                    continue;
                }
            }

            double stock = 0;//库存
            if (cellnotnull[3] != 3){
                if (row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC){
                    stock = row.getCell(3).getNumericCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:库存必须只能是数字，您输入了非法字符，故本条数据添加失败");
                    failCount++;
                    continue;
                }
            }

            String unitName = "";//单位名称
            if (cellnotnull[4] != 4){
                unitName = row.getCell(4).getStringCellValue();
            }

            double level = 0;//材料等级
            if (cellnotnull[5] != 5){
                if (row.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC){
                    level = row.getCell(5).getNumericCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:材料等级必须只能是数字，您输入了非法字符，故本条数据添加失败");
                    failCount++;
                    continue;
                }
            }

            String seasonName = "";//材料季节
            if (cellnotnull[6] != 6){
                seasonName = row.getCell(6).getStringCellValue();
            }

            String region = "";//材料产地
            if (cellnotnull[7] != 7){
                if (row.getCell(7).getCellType() == Cell.CELL_TYPE_STRING){
                    region = row.getCell(7).getStringCellValue();
                }else {
                    errorInfo.put("第"+ ++rowNum +"行录入失败","原因是:材料产地不能只为数字，必须带有汉字，故您本条数据添加失败");
                    failCount++;
                    continue;
                }
            }

            Object specification = "";//材料描述信息
            if (cellnotnull[8] != 8){
                if (row.getCell(8).getCellType() == Cell.CELL_TYPE_STRING){
                    specification = row.getCell(8).getStringCellValue();
                } else if (row.getCell(8).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    specification = row.getCell(8).getNumericCellValue();
                } else {
                    errorInfo.put("第" + ++rowNum + "行录入失败", "原因是:unknown");
                    failCount++;
                    continue;
                }
            }

            MaterialCategory materialCategory =new MaterialCategory();
            if (categoryName != ""){
                materialCategory = materialMapper.getMaterialCategoryByName(categoryName);
            }

            Unit unit = new Unit();
            if (unitName != ""){
                unit = materialMapper.getUnitByName(unitName);
            }

            int season = 0 ;
            if (seasonName != ""){
                season = getIndex(Values.seasonName,seasonName);
            }

//            if (materialName != ""){//如果材料名称不为空，可以直接添加数据库
//                Material material = new Material(materialName,(short)level,region,(short)season,materialCategory.getId(),false,
//                        Dates.now(),Dates.now(),user.getUsername(),price,(int)stock,unit.getId(),specification.toString());
//                materials.add(material);
//            }
        }
        if (materials != null && materials.size() > 0){
            for (Material material : materials){
                int id = materialMapper.getLastId();
                material.setMaterialId("M"+String.valueOf(1000001+id));
                material.setCanteenId(canteenId);
                //material.setMaterialId(i++ +""+(int)(Math.random()*100)+ i++ +"" + (int)(Math.random()*100)+i++ +""+materialMapper.getMaterialMaxID());
                int insertflag = materialMapper.insertMaterial(material);
                if (insertflag > 0){//表示插入成功
                    successCount++;
                }
            }
            log.info("添加成功，总共添加"+successCount+"条记录");
            lsResponse.setMessage("添加成功，总共添加"+successCount+"条记录");
            lsResponse.setTotalCount(successCount);
        }else {//如果只有3行表头，就会走这个地方~
            log.error("没有获取到数据，请根据提示进行添加，必填项务必填正确~");
            lsResponse.setAsFailure();
            lsResponse.setMessage("没有获取到数据，请根据提示进行添加，必填项务必填正确~");
            dataRecovery();
        }
        if (errorInfo != null && errorInfo.size() > 0){
            lsResponse.setMessage("不好意思，你上传的文件没有按照规定要求进行上传，原因可能为：很多空单元格或数据非法，请按照提示进行更改，谢谢");
            lsResponse.setAsFailure();
            lsResponse.setData(errorInfo);
            lsResponse.setTotalCount(errorInfo.size());
            log.error("不好意思，你上传的文件没有按照规定要求进行上传"+errorInfo);
            dataRecovery();
        }
        return  lsResponse;
    }


    @Override
    public LsResponse improtExecl(User user,Integer flag,String canteenId,MultipartFile file) {
        LsResponse lsResponse =  new LsResponse();
        try {
            if (user != null){
                log.info(user.getUsername()+"正在上传文件，文件名称为："+file.getName()+"，文件类型为："+file.getContentType());
            }
            switch (flag){
                case 0:
                    lsResponse = readRecipe(user,canteenId,file);break;
                case 1:
                    lsResponse = readEmployee(user,canteenId,file);break;
                case 2:
                    lsResponse = readMaterial(user,canteenId,file);break;
                default:
                    lsResponse.setMessage("读取失败");
                    lsResponse.setAsFailure();
                    log.error(user.getUsername()+"上传文件失败，文件名称为："+file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            lsResponse.setAsFailure();
            lsResponse.setErrorCode("500");
            lsResponse.setMessage("读取失败"+e.toString());
            log.error(user.getUsername()+"上传文件失败，文件名称为："+file.getName()+"失败原因为："+e.toString());
        }
        return lsResponse;
    }

    @Override
    public LsResponse importExcelYXStudent(User user) {
        LsResponse lsResponse =  LsResponse.newInstance();
        XSSFWorkbook workbook = null;
        XSSFSheet sheet;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(new File("D:/青岛艺术学校学生.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        List<PersonInfo> personInfos = new ArrayList<>();
        for (Row row : sheet){
            int rowNum = row.getRowNum();
            if(rowNum == 0 || rowNum == 1) continue;//第一、二行是标题以及各种规范，不需要循环判断

            if (rowNum > 1446) break;
            String className = row.getCell(0).getStringCellValue();//班级信息
            String studentName = row.getCell(1).getStringCellValue();//学生姓名
            String sexName = row.getCell(2).getStringCellValue();//学生性别
            String idNo = row.getCell(3).getStringCellValue();//身份证号

            PersonInfo person = new PersonInfo();
            person.setId(rowNum + 1);//设置主键，数据库中，不自增长
            person.setState(0);
            person.setCardNo("0000000000");
            person.setNumber("0000000000");
            person.setName(studentName);
            person.setLeave(1);
            person.setLeaveName("学生卡");
            person.setIdNo(idNo);
            person.setNote(className);

            personInfos.add(person);
        }
        if (personInfos != null && personInfos.size() > 0){
            for (PersonInfo personInfo : personInfos){
                personInfoMapper.insertPersonInfoStudent(personInfo);
            }
            lsResponse.setMessage("添加成功");
        }else {//如果只有3行表头，就会走这个地方~
            lsResponse.setAsFailure();
            lsResponse.setMessage("没有获取到数据，请根据提示进行添加，必填项务必填正确~");
            dataRecovery();
        }

        return  lsResponse;
    }

    @Override
    public LsResponse importExcelYXTeacher(User user) {
        LsResponse lsResponse =  LsResponse.newInstance();
        XSSFWorkbook workbook = null;
        XSSFSheet sheet;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(new File("D:/7812.xlsx")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);
        List<DiskLabel> diskLabels = new ArrayList<>();
        for (Row row : sheet){
            int rowNum = row.getRowNum();
//            if (rowNum > 162) break;
            String labeluid = row.getCell(3).getStringCellValue();//uid
            DiskLabel label = new DiskLabel();
            label.setLabeluid(labeluid);
            label.setDisktypeid(20);
            label.setCanteenid("LSCT100026");
            label.setRecipeid("recipeId");


            diskLabels.add(label);
        }
        if (diskLabels != null && diskLabels.size() > 0){
            for (DiskLabel label : diskLabels){
                diskLabelMapper.insertSelective(label);
            }
            lsResponse.setMessage("添加成功");
        }else {//如果只有3行表头，就会走这个地方~
            lsResponse.setAsFailure();
            lsResponse.setMessage("没有获取到数据，请根据提示进行添加，必填项务必填正确~");
            dataRecovery();
        }

        return  lsResponse;
    }

    /***
     * 读取单元格的值
     * @Title: getCellValue
     * @author: zgq
     * @Date : 2017-7-4 上午10:52:07
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }


    /**
     * employee员工表获取 日期类型的数据
     * @param hssfCell
     * @return
     */
    private String getDate(Cell hssfCell){
        DecimalFormat df = new DecimalFormat("#");
        if(hssfCell == null){
            return "";
        }
        switch (hssfCell.getCellType()){
            case HSSFCell.CELL_TYPE_NUMERIC:
                if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue()));
                }
                return df.format(hssfCell.getNumericCellValue());
            case HSSFCell.CELL_TYPE_STRING:
                return hssfCell.getStringCellValue();
            case HSSFCell.CELL_TYPE_FORMULA:
                return hssfCell.getCellFormula();
            case HSSFCell.CELL_TYPE_BLANK:
                return "";
        }
        return "";
    }

}
