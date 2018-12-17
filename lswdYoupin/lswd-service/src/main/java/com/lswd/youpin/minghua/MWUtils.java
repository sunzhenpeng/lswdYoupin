package com.lswd.youpin.minghua;

import com.lswd.youpin.utils.StringsUtil;
import com.sun.jna.Native;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigInteger;

public class MWUtils {
    public static short st = 1;
    public static int icdev;
    public static byte Snr[] = new byte[5];

//    @Value("${minghua.Driven}")
//    private String drivenPath;

    private static String path = "C:\\mwrf32.dll";

    /**
     * 加载本地驱动，bin目录底下的mwrf32.dll文件
     */
    public static Declare.mwrf getDLLConn() {
        Declare.mwrf epen = (Declare.mwrf) Native.loadLibrary(path, Declare.mwrf.class);
        if (epen != null) {
            System.out.println("DLL加载成功！");
            return epen;
        } else {
            System.out.println("DLL加载失败！");
            return null;
        }
    }

    /**
     * 创建连接，设备初始化
     *
     * @param epen
     */
    public static void DevConnect(Declare.mwrf epen) {
        byte[] ver = new byte[20];
        icdev = epen.rf_init((short) 0, 115200);
        st = epen.rf_get_status(icdev, ver);
        if (st == 0) {
            String str = new String(ver, 0, 18);
            System.out.println("设备初始化成功！" + str);//返回的是设备的版本号
        } else {
            System.out.println("设备连接失败!+++++++DevConnect()方法");
        }
        epen.rf_beep(icdev, (short) 30);//beef表示蜂鸣响，第二个参数表示响持续的时间
    }

    /**
     * 创建连接，设备初始化
     *
     * @param epen
     */
    public static short DevConnectShort(Declare.mwrf epen) {
        byte[] ver = new byte[20];
        short s = 1;
        icdev = epen.rf_init((short) 0, 115200);
        s = epen.rf_get_status(icdev, ver);
        epen.rf_beep(icdev, (short) 30);//beef表示蜂鸣响，第二个参数表示响持续的时间
        return s;
    }

    /**
     * 断开设备
     *
     * @param epen
     */
    public static void disconnectDev(Declare.mwrf epen) {
        epen.rf_exit(icdev);
        System.out.println("断开设备");
    }


    /**
     * 获取卡Uid 16进制，八位数，格式如：24BE84A4
     *
     * @param epen
     * @return
     */
    public static String getUid(Declare.mwrf epen) {
        String str = "";
        byte[] resetData = new byte[50];
        st = epen.rf_card(icdev, (short) 0, Snr);
        if (st == 0) {
            byte[] Snrhex = new byte[9];
            epen.hex_a(Snr, Snrhex, (short) 4);
            str = new String(Snrhex, 0, 8);
//            System.out.println(str);
        } else {
            System.out.println("寻卡失败！");
        }
        String opptStr = StringsUtil.getOppositeTwo(str);//位置对换
        String s = getTenSize(opptStr);//将16进制转成10进制
        return s;
    }

    public static String getTenSize(String str){
        StringBuilder s = new StringBuilder();
        Long x = Long.parseLong(str,16);
        if (x.toString().length() < 10){
            for (int i = 10; i > x.toString().length(); i--){
                s.append("0");
            }
            s.append(x.toString());
        }else {
            s.append(x.toString());
        }
        return s.toString();
    }




    public static void main(String[] args){
        Declare.mwrf epen = MWUtils.getDLLConn();
        MWUtils.DevConnect(epen);
        String cardUid =  MWUtils.getUid(epen);
//        String cardUid =  getTenSize("A484BE24");
//        Long cardUid =  Long.parseLong("A484BE24",16);

        System.out.println(cardUid);

        disconnectDev(epen);
    }

}
