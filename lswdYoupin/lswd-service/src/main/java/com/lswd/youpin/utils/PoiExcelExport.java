package com.lswd.youpin.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * 功能: [POI实现把Excel数据导出功能]
 * 作者: zhenguanqi
 * 版本: 1.0
 */
public class PoiExcelExport {
    HttpServletResponse response;

    private String fileName ;// 文件名
    private String fileDir;//文件保存路径
    private String sheetName; //sheet名
    private String titleFontType = "Arial Unicode MS";//表头字体
    private String titleBackColor = "C1FBEE"; //表头背景色
    private short titleFontSize = 12; //表头字号
    private String address = ""; //添加自动筛选的列 如 A:M
    private String contentFontType = "Arial Unicode MS"; //正文字体
    private short contentFontSize = 12;  //正文字号
    private String floatDecimal = ".00";//Float类型数据小数位
    private String doubleDecimal = ".00"; //Double类型数据小数位
    private String colFormula[] = null; //设置列的公式

    DecimalFormat floatDecimalFormat=new DecimalFormat(floatDecimal);
    DecimalFormat doubleDecimalFormat=new DecimalFormat(doubleDecimal);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private HSSFWorkbook workbook = null;


    /**
     * 三个构造方法，一个无参构造方法
     */

    public PoiExcelExport() {
    }
    public PoiExcelExport(String fileDir,String sheetName){
        this.fileDir = fileDir;
        this.sheetName = sheetName;
        workbook = new HSSFWorkbook();
    }

    public PoiExcelExport(HttpServletResponse response,String fileName,String sheetName){
        this.response = response;
        this.fileName = fileName;
        this.sheetName = sheetName;
        workbook = new HSSFWorkbook();
    }
    /**
     * 设置表头字体.
     * @param titleFontType
     */
    public void setTitleFontType(String titleFontType) {this.titleFontType = titleFontType;}
    /**
     * 设置表头背景色.
     * @param titleBackColor 十六进制
     */
    public void setTitleBackColor(String titleBackColor) {this.titleBackColor = titleBackColor;}
    /**
     * 设置表头字体大小.
     * @param titleFontSize
     */
    public void setTitleFontSize(short titleFontSize) {this.titleFontSize = titleFontSize;}
    /**
     * 设置表头自动筛选栏位,如A:AC.
     * @param address
     */
    public void setAddress(String address) {this.address = address;}
    /**
     * 设置正文字体.
     * @param contentFontType
     */
    public void setContentFontType(String contentFontType) {this.contentFontType = contentFontType;
    }
    /**
     * 设置正文字号.
     * @param contentFontSize
     */
    public void setContentFontSize(short contentFontSize) {this.contentFontSize = contentFontSize;}
    /**
     * 设置float类型数据小数位 默认.00
     * @param doubleDecimal 如 ".00"
     */
    public void setDoubleDecimal(String doubleDecimal) {this.doubleDecimal = doubleDecimal;}
    /**
     * 设置doubel类型数据小数位 默认.00
     * @param floatDecimalFormat 如 ".00
     */
    public void setFloatDecimalFormat(DecimalFormat floatDecimalFormat) {this.floatDecimalFormat = floatDecimalFormat;}
    /**
     * 设置列的公式
     * @param colFormula  存储i-1列的公式 涉及到的行号使用@替换 如A@+B@
     */
    public void setColFormula(String[] colFormula) {this.colFormula = colFormula;}

    /**
     * 导出execl模板
     * @param flag 0：代表菜品，1：代表员工，2：代表食材
     * @param bigTitle 表示标题
     * @param titleColumn  对应bean的属性名
     * @param titleName   excel要导出的表名
     * @param titleSize   列宽
     * @param dataList  数据
     * @param array1 表示数据有效性，比如数据库中分类的name，部门的name
     * @param array2 表示数据有效性，比如数据库中分类的name，部门的name
     */
    public void wirteExcelModel(Integer flag,String bigTitle,String titleColumn[],String titleName[],int titleSize[],List<?> dataList,String[] array1,String[] array2){
        Sheet sheet = workbook.createSheet(this.sheetName);//添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        OutputStream out = null; //新建文件
        try {
            if(fileDir!=null){//有文件路径
                out = new FileOutputStream(fileDir);
            }else{//否则，直接写到输出流中
                fileName = fileName+".xls";
                out = response.getOutputStream();
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            }
            HSSFRow bigtitleRow = workbook.getSheet(sheetName).createRow(0); //写入大标题
            Cell titlecell = bigtitleRow.createCell(0);
            HSSFCellStyle bigtitleStyle = workbook.createCellStyle(); //设置样式
            bigtitleStyle = (HSSFCellStyle) setFontAndBorder(bigtitleStyle, titleFontType, (short) 20);
            bigtitleStyle = (HSSFCellStyle) setColor(bigtitleStyle, "AFEEEE", (short)32);
            bigtitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置文字居中
            titlecell.setCellStyle(bigtitleStyle);//设置单元格内容
            titlecell.setCellValue(bigTitle); //设置单元格内容

            HSSFRow titleNameRow = null;
            if (flag == 0){//表示菜谱
                CellRangeAddress address = new CellRangeAddress(0, 0, 0, 5);
                sheet.addMergedRegion(address);//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, address, sheet,workbook);//给合并后的单元格设置边框

                sheet.addMergedRegion(new CellRangeAddress(1,1,0,5));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                HSSFRow ruleRow = workbook.getSheet(sheetName).createRow(1); //写入规则
                titleNameRow = workbook.getSheet(sheetName).createRow(2); //写入excel的表头
                ruleRow.setHeight((short) (130 * 16));
                Cell rulecell = ruleRow.createCell(0);
                HSSFCellStyle ruleStyle = workbook.createCellStyle(); //设置样式
                ruleStyle = (HSSFCellStyle) setFontAndBorder(ruleStyle, titleFontType, (short) 12);
                ruleStyle = (HSSFCellStyle) setColor(ruleStyle, "AFEEEE", (short)20);
                ruleStyle.setWrapText(true);//设置自动换行
                rulecell.setCellStyle(ruleStyle);
                rulecell.setCellValue("填写注意事项：（未按照如下要求填写，会导致数据不能正常导入，本模板一次性导入上限为500条。）\n" +
                        "1、请不要修改此表格格式，包括插入删除行和列、合并拆分单元格等。请逐行录入数据。\n" +
                        "2、需要填写的单元格有字段规则校验，请按照提示输入；请不要随意复制单元格，这样会破坏字段规则校验。\n" +
                        "3、带*的为必填项，必填项为：菜品名称、指导价格、市场价格、菜品分类名称\n" +
                        "4、相关录入规格说明：\n" +
                        "   a.烹饪方式、菜品分类名称在录入，请根据下拉选择框进行选择"); //设置单元格内容
            }else if(flag == 1){//表示员工
                CellRangeAddress address =new CellRangeAddress(0,0,0,9);
                sheet.addMergedRegion(address);//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, address, sheet,workbook);//给合并后的单元格设置下边框

                sheet.addMergedRegion(new CellRangeAddress(1,1,0,9));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                HSSFRow ruleRow = workbook.getSheet(sheetName).createRow(1); //写入规则
                ruleRow.setHeight((short) (150 * 16));//设置高度

                titleNameRow = workbook.getSheet(sheetName).createRow(2); //写入excel的表头
                Cell rulecell = ruleRow.createCell(0);
                HSSFCellStyle ruleStyle = workbook.createCellStyle(); //设置样式
                ruleStyle = (HSSFCellStyle) setFontAndBorder(ruleStyle, titleFontType, (short) 12);
                ruleStyle = (HSSFCellStyle) setColor(ruleStyle, "AFEEEE", (short)20);
                ruleStyle.setWrapText(true);//设置自动换行
                rulecell.setCellStyle(ruleStyle);
                rulecell.setCellValue("填写注意事项：（未按照如下要求填写，会导致数据不能正常导入，本模板一次性导入上限为500条。）\n" +
                        "1、请不要修改此表格格式，包括插入删除行和列、合并拆分单元格等。请逐行录入数据。\n" +
                        "2、需要填写的单元格有字段规则校验，请按照提示输入；请不要随意复制单元格，这样会破坏字段规则校验。\n" +
                        "3、带*的为必填项，必填项为：员工姓名、员工手机号、所属园区、员工卡号 \n" +
                        "4、相关录入规格说明：\n" +
                        "   a.录入时间的标准格式为：yyyy/MM/dd \n" +
                        "   b.录入所属园区、性别、就职状态时，请根据下拉选择框进行选择"); //设置单元格内容
            }else if(flag == 2){//表示材料
                CellRangeAddress address =new CellRangeAddress(0,0,0,8);
                sheet.addMergedRegion(address);//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列,截至列
                RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, address, sheet,workbook);//给合并后的单元格设置下边框

                sheet.addMergedRegion(new CellRangeAddress(1,1,0,8));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                HSSFRow ruleRow = workbook.getSheet(sheetName).createRow(1); //写入规则
                ruleRow.setHeight((short) (150 * 16));

                Cell rulecell = ruleRow.createCell(0);
                titleNameRow = workbook.getSheet(sheetName).createRow(2); //写入excel的表头
                HSSFCellStyle ruleStyle = workbook.createCellStyle(); //设置样式
                ruleStyle = (HSSFCellStyle) setFontAndBorder(ruleStyle, titleFontType, (short) 12);
                ruleStyle = (HSSFCellStyle) setColor(ruleStyle, "AFEEEE", (short)20);
                ruleStyle.setWrapText(true);//设置自动换行
                rulecell.setCellStyle(ruleStyle);
                rulecell.setCellValue("填写注意事项：（未按照如下要求填写，会导致数据不能正常导入，本模板一次性导入上限为500条。）\n" +
                        "1、请不要修改此表格格式，包括插入删除行和列、合并拆分单元格等。请逐行录入数据，若连续10行为空，录入数据不再被识别。\n" +
                        "2、需要填写的单元格有字段规则校验，请按照提示输入；请不要随意复制单元格，这样会破坏字段规则校验。\n" +
                        "3、带*的为必填项，必填项为：材料名称，材料分类名称，单价 \n" +
                        "4、相关录入规格说明：\n" +
                        "   a.录入材料等级时，请输入：1，2，3，4...，数字越大，表示等级越高\n" +
                        "   b.录入材料季节、材料分类名称以及单位时，请根据下拉选择框进行选择"); //设置单元格内容
            }
            HSSFCellStyle titleStyle = workbook.createCellStyle(); //设置样式
            titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, titleFontType, (short) titleFontSize);
            titleStyle = (HSSFCellStyle) setColor(titleStyle, titleBackColor, (short)10);

            for(int i = 0;i < titleName.length;i++){
                sheet.setColumnWidth(i, titleSize[i]*256);//设置宽度
                Cell cell = titleNameRow.createCell(i);//创建单元格
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titleName[i].toString());
            }

            //为表头添加自动筛选
            if(!"".equals(address)){
                CellRangeAddress c = (CellRangeAddress) CellRangeAddress.valueOf(address);
                sheet.setAutoFilter(c);
            }

            //通过反射获取数据并写入到excel中
            if(flag == 0){//菜谱
                for (int i = 3;i<500;i++){
                    HSSFDataValidation data_validation_list1 = DateValidity.setDataValidationList(Values.cookTypeName,(short)i,(short)i,(short)1,(short)1); //得到验证对象,烹饪方式
                    sheet.addValidationData(data_validation_list1);

                    HSSFDataValidation data_validation_list2 = DateValidity.setDataValidationList(array1,(short)i,(short)i,(short)4,(short)4); //得到验证对象，菜品分类名称
                    sheet.addValidationData(data_validation_list2);
                }
            }
            if(flag ==1){//员工
                for (int i = 3;i<500;i++){
                    HSSFDataValidation data_validation_list6 = DateValidity.setDataValidationList(array2,(short)i,(short)i,(short)2,(short)2); //所属园区下拉选择框
                    sheet.addValidationData(data_validation_list6);

                    //HSSFDataValidation data_validation_list1 = DateValidity.setDataValidationList(array1,(short)i,(short)i,(short)4,(short)4); //部门下拉选择框
                    //sheet.addValidationData(data_validation_list1);

                    HSSFDataValidation data_validation_list2 = DateValidity.setBoxs(new String[]{"男","女"},"请选择男or女",(short)i,(short)i,(short)5,(short)5); //男女
                    sheet.addValidationData(data_validation_list2);

                    HSSFDataValidation data_validation_list3 = DateValidity.setDataValidationList(new String[]{"在职","离职","休假"},(short)i,(short)i,(short)7,(short)7); //在职、离职、休假
                    sheet.addValidationData(data_validation_list3);

                    HSSFDataValidation data_validation_list4 = DateValidity.setDate((short)i,(short)i,(short)6,(short)6); //生日校验
                    sheet.addValidationData(data_validation_list4);

                    HSSFDataValidation data_validation_list5 = DateValidity.setDate((short)i,(short)i,(short)10,(short)10); //入职日期校验
                    sheet.addValidationData(data_validation_list5);
                }
            }
            if(flag ==2){//材料
                for (int i = 3;i<500;i++){
                    HSSFDataValidation data_validation_list1 = DateValidity.setDataValidationList(array1,(short)i,(short)i,(short)1,(short)1); //得到验证对象,材料分类名称
                    sheet.addValidationData(data_validation_list1);

                    HSSFDataValidation data_validation_list2 = DateValidity.setDataValidationList(array2,(short)i,(short)i,(short)4,(short)4); //得到验证对象，单位
                    sheet.addValidationData(data_validation_list2);

                    HSSFDataValidation data_validation_list3 = DateValidity.setDataValidationList(new String[]{"春季","夏季","秋季","冬季 "},(short)i,(short)i,(short)6,(short)6); //得到验证对象.材料季节，春夏秋冬
                    sheet.addValidationData(data_validation_list3);
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出execl，导出数据库的内容
     * @param flag 0：代表菜品，1：代表员工，2：代表食材
     * @param bigTitle 表示标题
     * @param titleColumn  对应bean的属性名
     * @param titleName   excel要导出的表名
     * @param titleSize   列宽
     * @param dataList  数据
     */
    public void wirteExcelList(Integer flag,String bigTitle,String titleColumn[],String titleName[],int titleSize[],List<?> dataList){
        Sheet sheet = workbook.createSheet(this.sheetName);//添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        OutputStream out = null; //新建文件
        try {
            if(fileDir!=null){//有文件路径
                out = new FileOutputStream(fileDir);
            }else{//否则，直接写到输出流中
                out = response.getOutputStream();
                fileName = fileName+".xls";
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            }

            HSSFRow bigtitleRow = workbook.getSheet(sheetName).createRow(0); //写入大标题
            Cell titlecell = bigtitleRow.createCell(0);
            HSSFCellStyle bigtitleStyle = workbook.createCellStyle(); //设置样式
            bigtitleStyle = (HSSFCellStyle) setFontAndBorder(bigtitleStyle, titleFontType, (short) 20);
            bigtitleStyle = (HSSFCellStyle) setColor(bigtitleStyle, "AFEEEE", (short)32);
            bigtitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置文字居中
            titlecell.setCellStyle(bigtitleStyle);//设置单元格内容
            titlecell.setCellValue(bigTitle); //设置单元格内容
            HSSFRow titleNameRow = null;

            if (flag == 0){//表示菜谱
                sheet.addMergedRegion(new CellRangeAddress(0,0,0,10));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                titleNameRow = workbook.getSheet(sheetName).createRow(1); //写入excel的表头
            }else if(flag == 1){//表示员工
                sheet.addMergedRegion(new CellRangeAddress(0,0,0,21));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                titleNameRow = workbook.getSheet(sheetName).createRow(1); //写入excel的表头
            }else if(flag == 2){//表示材料
                sheet.addMergedRegion(new CellRangeAddress(0,0,0,12));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                titleNameRow = workbook.getSheet(sheetName).createRow(1); //写入excel的表头
            }

            HSSFCellStyle titleStyle = workbook.createCellStyle(); //设置样式
            titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, titleFontType, (short) titleFontSize);
            titleStyle = (HSSFCellStyle) setColor(titleStyle, titleBackColor, (short)10);

            for(int i = 0;i < titleName.length;i++){
                sheet.setColumnWidth(i, titleSize[i]*256);//设置宽度
                Cell cell = titleNameRow.createCell(i);//创建单元格
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titleName[i].toString());
            }

            //通过反射获取数据并写入到excel中
            if(dataList!=null&&dataList.size()>0){
                HSSFCellStyle dataStyle = workbook.createCellStyle();//设置样式
                titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, contentFontType, (short) contentFontSize);
                if(titleColumn.length>0){
                    for(int rowIndex = 1;rowIndex <= dataList.size();rowIndex++){
                        Object obj = dataList.get(rowIndex - 1); //获得该对象
                        Class clsss = obj.getClass();//获得该对对象的class实例
                        Row dataRow = null;
                        dataRow = workbook.getSheet(sheetName).createRow(rowIndex + 1);//创建单元格
                        for(int columnIndex = 0;columnIndex<titleColumn.length;columnIndex++){//根据字段的个数进行for循环
                            String title = titleColumn[columnIndex].toString().trim();
                            if(!"".equals(title)){  //字段不为空
                                String UTitle = Character.toUpperCase(title.charAt(0))+ title.substring(1, title.length()); // 使其首字母大写;
                                String methodName  = "get"+UTitle;//通过字符串拼接生成getXxx方法
                                Method method = clsss.getDeclaredMethod(methodName);// 设置要执行的方法
                                String returnType = method.getReturnType().getName(); //获取返回类型，返回的是java.lang.String的格式
                                String data = (method.invoke(obj) == null) ? "" : method.invoke(obj).toString();//获取数据
                                Cell cell = dataRow.createCell(columnIndex);
                                if(data != null && !"".equals(data)){
                                    if("java.lang.Integer".equals(returnType) || "int".equals(returnType)){
                                        cell.setCellValue(Integer.parseInt(data));
                                    }else if ("java.lang.Short".equals(returnType) || "short".equals(returnType)){
                                        cell.setCellValue(Short.parseShort(data));
                                    }else if("java.lang.Long".equals(returnType) || "long".equals(returnType)){
                                        cell.setCellValue(Long.parseLong(data));
                                    }else if("java.lang.Float".equals(returnType) || "float".equals(returnType)){
                                        cell.setCellValue(floatDecimalFormat.format(Float.parseFloat(data)));
                                    }else if("java.lang.Double".equals(returnType) || "double".equals(returnType)){
                                        cell.setCellValue(doubleDecimalFormat.format(Double.parseDouble(data)));
                                    }else{
                                        cell.setCellValue(data);
                                    }
                                }
                            }else{//字段为空 检查该列是否是公式，一般没有这种情况，除了算年薪，总价等。。。
                                if(colFormula!=null){
                                    String sixBuf = colFormula[columnIndex].replace("@", (rowIndex+1)+"");
                                    Cell cell = dataRow.createCell(columnIndex);
                                    cell.setCellFormula(sixBuf.toString());
                                }
                            }
                        }
                    }
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 导出execl，导出数据库的内容
     * @param flag 0：代表菜品，1：代表员工，2：代表食材
     * @param isModel true：表示是模板
     * @param bigTitle 表示标题
     * @param titleColumn  对应bean的属性名
     * @param titleName   excel要导出的表名
     * @param titleSize   列宽
     * @param dataList  数据
     * @param s 表示数据有效性，比如数据库中分类的name，部门的name
     */
    public void wirteExcel(Integer flag,Boolean isModel,String bigTitle,String titleColumn[],String titleName[],int titleSize[],List<?> dataList,String[] s){
        Sheet sheet = workbook.createSheet(this.sheetName);//添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        OutputStream out = null; //新建文件
        try {
            if(fileDir!=null){//有文件路径
                out = new FileOutputStream(fileDir);
            }else{//否则，直接写到输出流中
                out = response.getOutputStream();
                fileName = fileName+".xls";
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            }
            HSSFRow bigtitleRow = workbook.getSheet(sheetName).createRow(0); //写入大标题
            Cell titlecell = bigtitleRow.createCell(0);
            HSSFCellStyle bigtitleStyle = workbook.createCellStyle(); //设置样式
            bigtitleStyle = (HSSFCellStyle) setFontAndBorder(bigtitleStyle, titleFontType, (short) 20);
            bigtitleStyle = (HSSFCellStyle) setColor(bigtitleStyle, "AFEEEE", (short)32);
            bigtitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置文字居中
            titlecell.setCellStyle(bigtitleStyle);//设置单元格内容
            titlecell.setCellValue(bigTitle); //设置单元格内容

            HSSFRow titleNameRow = null;
            if (isModel){//表明是模版
                if (flag == 0){//表示菜谱
                    CellRangeAddress address = new CellRangeAddress(0, 0, 0, 5);
                    sheet.addMergedRegion(address);//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                    RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, address, sheet,workbook);//给合并后的单元格设置边框
                    sheet.addMergedRegion(new CellRangeAddress(1,1,0,5));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                    HSSFRow ruleRow = workbook.getSheet(sheetName).createRow(1); //写入规则
                    titleNameRow = workbook.getSheet(sheetName).createRow(2); //写入excel的表头
                    ruleRow.setHeight((short) (120 * 20));
                    Cell rulecell = ruleRow.createCell(0);
                    HSSFCellStyle ruleStyle = workbook.createCellStyle(); //设置样式
                    ruleStyle = (HSSFCellStyle) setFontAndBorder(ruleStyle, titleFontType, (short) 12);
                    ruleStyle = (HSSFCellStyle) setColor(ruleStyle, "AFEEEE", (short)20);
                    ruleStyle.setWrapText(true);//设置自动换行
                    rulecell.setCellStyle(ruleStyle);
                    rulecell.setCellValue("填写注意事项：（未按照如下要求填写，会导致数据不能正常导入，本模板一次性导入上限为500条。）\n" +
                            "1、请不要修改此表格格式，包括插入删除行和列、合并拆分单元格等。请逐行录入数据。\n" +
                            "2、需要填写的单元格有字段规则校验，请按照提示输入；请不要随意复制单元格，这样会破坏字段规则校验。\n" +
                            "3、带*的为必填项！！！\n" +
                            "4、相关录入规格说明：\n" +
                            "   a.菜品类型、烹饪方式、菜品分类名称在录入，请根据下拉选择框进行选择 （****）"); //设置单元格内容
                }else if(flag == 1){//表示员工
                    CellRangeAddress address =new CellRangeAddress(0,0,0,8);
                    sheet.addMergedRegion(address);//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                    RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, address, sheet,workbook);//给合并后的单元格设置下边框

                    sheet.addMergedRegion(new CellRangeAddress(1,1,0,8));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                    HSSFRow ruleRow = workbook.getSheet(sheetName).createRow(1); //写入规则
                    ruleRow.setHeight((short) (135 * 20));//设置高度

                    titleNameRow = workbook.getSheet(sheetName).createRow(2); //写入excel的表头
                    Cell rulecell = ruleRow.createCell(0);
                    HSSFCellStyle ruleStyle = workbook.createCellStyle(); //设置样式
                    ruleStyle = (HSSFCellStyle) setFontAndBorder(ruleStyle, titleFontType, (short) 12);
                    ruleStyle = (HSSFCellStyle) setColor(ruleStyle, "AFEEEE", (short)20);
                    ruleStyle.setWrapText(true);//设置自动换行
                    rulecell.setCellStyle(ruleStyle);
                    rulecell.setCellValue("填写注意事项：（未按照如下要求填写，会导致数据不能正常导入，本模板一次性导入上限为500条。）\n" +
                            "1、请不要修改此表格格式，包括插入删除行和列、合并拆分单元格等。请逐行录入数据。\n" +
                            "2、需要填写的单元格有字段规则校验，请按照提示输入；请不要随意复制单元格，这样会破坏字段规则校验。\n" +
                            "3、带*的为必填项！！！\n" +
                            "4、相关录入规格说明：\n" +
                            "   a.录入时间的标准格式为：yyyy-MM-dd （****）\n" +
                            "   b.录入性别、就职状态、部门名称时，请根据下拉选择框进行选择 （****）"); //设置单元格内容
                }else if(flag == 2){//表示材料
                    CellRangeAddress address =new CellRangeAddress(0,0,0,4);
                    sheet.addMergedRegion(address);//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列,截至列
                    RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, address, sheet,workbook);//给合并后的单元格设置下边框

                    sheet.addMergedRegion(new CellRangeAddress(1,1,0,4));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                    HSSFRow ruleRow = workbook.getSheet(sheetName).createRow(1); //写入规则
                    ruleRow.setHeight((short) (145 * 20));

                    Cell rulecell = ruleRow.createCell(0);
                    titleNameRow = workbook.getSheet(sheetName).createRow(2); //写入excel的表头
                    HSSFCellStyle ruleStyle = workbook.createCellStyle(); //设置样式
                    ruleStyle = (HSSFCellStyle) setFontAndBorder(ruleStyle, titleFontType, (short) 12);
                    ruleStyle = (HSSFCellStyle) setColor(ruleStyle, "AFEEEE", (short)20);
                    ruleStyle.setWrapText(true);//设置自动换行
                    rulecell.setCellStyle(ruleStyle);
                    rulecell.setCellValue("填写注意事项：（未按照如下要求填写，会导致数据不能正常导入，本模板一次性导入上限为500条。）\n" +
                            "1、请不要修改此表格格式，包括插入删除行和列、合并拆分单元格等。请逐行录入数据，若连续10行为空，录入数据不再被识别。\n" +
                            "2、需要填写的单元格有字段规则校验，请按照提示输入；请不要随意复制单元格，这样会破坏字段规则校验。\n" +
                            "3、带*的为必填项！！！\n" +
                            "4、相关录入规格说明：\n" +
                            "   a.录入材料等级时，请输入：1，2，3，4...，数字越大，表示等级越高（****）\n" +
                            "   b.录入材料季节、材料分类名称时，请根据下拉选择框进行选择（****）"); //设置单元格内容
                }

            }else {//表明不是模版
                if (flag == 0){//表示菜谱
                    sheet.addMergedRegion(new CellRangeAddress(0,0,0,9));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                    titleNameRow = workbook.getSheet(sheetName).createRow(1); //写入excel的表头
                }else if(flag == 1){//表示员工
                    sheet.addMergedRegion(new CellRangeAddress(0,0,0,13));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                    titleNameRow = workbook.getSheet(sheetName).createRow(1); //写入excel的表头
                }else if(flag == 2){//表示材料
                    sheet.addMergedRegion(new CellRangeAddress(0,0,0,10));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                    titleNameRow = workbook.getSheet(sheetName).createRow(1); //写入excel的表头
                }
            }

            HSSFCellStyle titleStyle = workbook.createCellStyle(); //设置样式
            titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, titleFontType, (short) titleFontSize);
            titleStyle = (HSSFCellStyle) setColor(titleStyle, titleBackColor, (short)10);

            for(int i = 0;i < titleName.length;i++){
                sheet.setColumnWidth(i, titleSize[i]*256);//设置宽度
                Cell cell = titleNameRow.createCell(i);//创建单元格
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titleName[i].toString());
            }

            //为表头添加自动筛选
            if(!"".equals(address)){
                CellRangeAddress c = (CellRangeAddress) CellRangeAddress.valueOf(address);
                sheet.setAutoFilter(c);
            }

            //通过反射获取数据并写入到excel中
            if(dataList!=null&&dataList.size()>0){
                HSSFCellStyle dataStyle = workbook.createCellStyle();//设置样式
                titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, contentFontType, (short) contentFontSize);
                if(titleColumn.length>0){
                    for(int rowIndex = 1;rowIndex <= dataList.size();rowIndex++){
                        Object obj = dataList.get(rowIndex - 1); //获得该对象
                        Class clsss = obj.getClass();//获得该对对象的class实例
                        Row dataRow = null;
                        if (isModel){
                            dataRow = workbook.getSheet(sheetName).createRow(rowIndex + 2);//创建单元格
                        }else {
                            dataRow = workbook.getSheet(sheetName).createRow(rowIndex + 1);//创建单元格
                        }
                        for(int columnIndex = 0;columnIndex<titleColumn.length;columnIndex++){//根据字段的个数进行for循环
                            String title = titleColumn[columnIndex].toString().trim();
                            if(!"".equals(title)){  //字段不为空
                                String UTitle = Character.toUpperCase(title.charAt(0))+ title.substring(1, title.length()); // 使其首字母大写;
                                String methodName  = "get"+UTitle;//通过字符串拼接生成getXxx方法
                                Method method = clsss.getDeclaredMethod(methodName);// 设置要执行的方法
                                String returnType = method.getReturnType().getName(); //获取返回类型
                                String data = (method.invoke(obj) == null) ? "" : method.invoke(obj).toString();//获取数据
                                Cell cell = dataRow.createCell(columnIndex);
                                if(data != null && !"".equals(data)){
                                    if("int".equals(returnType)){
                                        cell.setCellValue(Integer.parseInt(data));
                                    }else if("long".equals(returnType)){
                                        cell.setCellValue(Long.parseLong(data));
                                    }else if("float".equals(returnType)){
                                        cell.setCellValue(floatDecimalFormat.format(Float.parseFloat(data)));
                                    }else if("double".equals(returnType)){
                                        cell.setCellValue(doubleDecimalFormat.format(Double.parseDouble(data)));
                                    }else{
                                        cell.setCellValue(data);
                                    }
                                }
                            }else{//字段为空 检查该列是否是公式，一般没有这种情况，除了算年薪，总价等。。。
                                if(colFormula!=null){
                                    String sixBuf = colFormula[columnIndex].replace("@", (rowIndex+1)+"");
                                    Cell cell = dataRow.createCell(columnIndex);
                                    cell.setCellFormula(sixBuf.toString());
                                }
                            }
                        }
                    }
                }
            }else{
                if (isModel){
                    if(flag == 0){//菜谱
                        String[] s1 = new String[]{"荤菜","素菜","主食","粥","凉菜","甜品","水果"};
                        String[] s2 = new String[]{"炒","爆","熘","炸","烹","烤","贴","烧","炖","熬","煮","蒸","拔丝","涮"};
                        for (int i = 3;i<500;i++){
                            HSSFDataValidation data_validation_list1 = DateValidity.setDataValidationList(s1,(short)i,(short)i,(short)1,(short)1); //得到验证对象
                            sheet.addValidationData(data_validation_list1); //工作表添加验证数据

                            HSSFDataValidation data_validation_list2 = DateValidity.setDataValidationList(s,(short)i,(short)i,(short)4,(short)4); //得到验证对象
                            sheet.addValidationData(data_validation_list2);

                            HSSFDataValidation data_validation_list3 = DateValidity.setDataValidationList(s2,(short)i,(short)i,(short)2,(short)2); //得到验证对象
                            sheet.addValidationData(data_validation_list3);
                        }

                        //String[] s3 = new String[]{"家常菜","下饭菜","创意菜","下酒菜","面食","汤","粥","自治调味料","菜式菜品"};
                        /*  String[] s4 = new String[]{"主料","佐料"};
                        HSSFDataValidation data_validation_list4 = DateValidity.setDataValidationList(s4,(short)3,(short)3,(short)7,(short)7); //得到验证对象
                        sheet.addValidationData(data_validation_list4);*/
                    }
                    if(flag ==1){//员工
                        //String[] s1 = new String[]{"采购部","技术部","销售部","统计部","人力资源部","保安大队"};
                        String[] s2 = new String[]{"男","女"};
                        String[] s3 = new String[]{"在职","离职","休假"};

                        for (int i = 3;i<500;i++){
                            HSSFDataValidation data_validation_list1 = DateValidity.setDataValidationList(s,(short)i,(short)i,(short)1,(short)1); //部门下拉选择框
                            sheet.addValidationData(data_validation_list1);

                            HSSFDataValidation data_validation_list2 = DateValidity.setBoxs(s2,"请选择男or女",(short)i,(short)i,(short)2,(short)2); //男女
                            sheet.addValidationData(data_validation_list2);

                            HSSFDataValidation data_validation_list3 = DateValidity.setDataValidationList(s3,(short)i,(short)i,(short)4,(short)4); //在职、离职、休假
                            sheet.addValidationData(data_validation_list3);

                            HSSFDataValidation data_validation_list4 = DateValidity.setDate((short)i,(short)i,(short)3,(short)3); //生日校验
                            sheet.addValidationData(data_validation_list4);

                            HSSFDataValidation data_validation_list5 = DateValidity.setDate((short)i,(short)i,(short)8,(short)8); //入职日期校验
                            sheet.addValidationData(data_validation_list5);
                        }
                    }
                    if(flag ==2){//材料
                        String[] s2 = new String[]{"春季","夏季","秋季","冬季 "};
                        for (int i = 3;i<500;i++){
                            HSSFDataValidation data_validation_list3 = DateValidity.setDataValidationList(s,(short)i,(short)i,(short)1,(short)1); //得到验证对象
                            sheet.addValidationData(data_validation_list3);

                            HSSFDataValidation data_validation_list2 = DateValidity.setDataValidationList(s2,(short)i,(short)i,(short)4,(short)4); //得到验证对象
                            sheet.addValidationData(data_validation_list2);
                        }
                    }
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将16进制的颜色代码写入样式中来设置颜色
     * @param style  保证style统一
     * @param color  颜色：66FFDD
     * @param index 索引 8-64 使用时不可重复
     * @return
     */
    public CellStyle setColor(CellStyle style,String color,short index){
        if(color!=""&&color!=null){
            //转为RGB码
            int r = Integer.parseInt((color.substring(0,2)),16);//转为16进制
            int g = Integer.parseInt((color.substring(2,4)),16);
            int b = Integer.parseInt((color.substring(4,6)),16);
            //自定义cell颜色
            HSSFPalette palette = workbook.getCustomPalette();
            palette.setColorAtIndex((short)index, (byte) r, (byte) g, (byte) b);

            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(index);
        }
        return style;
    }

    /**
     * 设置字体并加外边框
     * @param style  样式
     * @param style  字体名
     * @param style  大小
     * @return
     */
    public CellStyle setFontAndBorder(CellStyle style,String fontName,short size){
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName(fontName);
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        style.setBorderTop(CellStyle.BORDER_THIN);//上边框
        style.setBorderRight(CellStyle.BORDER_THIN);//右边框
        return style;
    }

    /**
     * 删除文件
     * @return
     */
    public boolean deleteExcel(String path){
        boolean flag = false;
        File file = new File(path);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                file.delete();
                flag = true;
            }
        }
        return flag;
    }
}