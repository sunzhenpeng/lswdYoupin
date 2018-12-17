package com.lswd.youpin.utils;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * 生成单元格的数据有效性时使用。
 * Created by zhenguanqi on 2017/7/3.
 */
public class DateValidity {

    // 下拉框限制 1 :不带输入信息
    public static HSSFDataValidation setDataValidationList(String[] textlist,short firstRow,short firstCol,short endRow, short endCol){
        //加载下拉列表内容
        DVConstraint constraint=DVConstraint.createExplicitListConstraint(textlist);
        //设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions=new CellRangeAddressList(firstRow,firstCol,endRow,endCol);
        //数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
        return data_validation_list;
    }

    // 下拉框限制 2 :带有输入信息
    public static HSSFDataValidation setBoxs(String[] textlist,String info,short firstRow,short firstCol,short endRow, short endCol) {
        DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(textlist);
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, firstCol, endRow, endCol);
        HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
        dataValidation.setSuppressDropDownArrow(false);
        if (info != ""){
            dataValidation.createPromptBox("输入提示", info);
            dataValidation.setShowPromptBox(true);
        }
        return dataValidation;
    }

    // 日期格式限制
    public static HSSFDataValidation setDate(short firstRow,short firstCol,short endRow, short endCol) {
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, firstCol, endRow, endCol);
        DVConstraint dvConstraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1900-01-01",
                "5000-01-01", "yyyy-mm-dd");
        HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
        dataValidation.setSuppressDropDownArrow(false);
        dataValidation.createPromptBox("输入提示", "请填写日期格式");
        // 设置输入错误提示信息
        dataValidation.createErrorBox("日期格式错误提示", "你输入的日期格式不符合'yyyy-mm-dd'格式规范，请重新输入！");
        dataValidation.setShowPromptBox(true);
        return dataValidation;
    }


    public static HSSFDataValidation setDataValidationView(short firstRow,short firstCol,short endRow, short endCol){
        //构造constraint对象
        DVConstraint constraint=DVConstraint.createCustomFormulaConstraint("B1");
        //四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions=new CellRangeAddressList(firstRow,firstCol,endRow,endCol);
        //数据有效性对象
        HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
        return data_validation_view;
    }

    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        return cellphone.matches(regex);
    }


}
