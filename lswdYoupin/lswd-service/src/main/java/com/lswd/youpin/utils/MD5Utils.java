package com.lswd.youpin.utils;

import com.alibaba.fastjson.JSONObject;
import com.lswd.youpin.common.date.Dates;
import com.lswd.youpin.common.security.MD5;
import com.lswd.youpin.common.util.PropertiesUtils;
import com.lswd.youpin.minghua.Declare;
import com.lswd.youpin.minghua.MWUtils;
import com.lswd.youpin.model.User;
import com.lswd.youpin.response.LsResponse;
import com.lswd.youpin.shiro.kit.ShiroKit;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;

import java.awt.geom.FlatteningPathIterator;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liruilong on 16/9/18.
 */
public class MD5Utils {

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};


    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }


    public static String getMD5ForRandomUUID() {
        return MD5Encode(UUID.randomUUID().toString(), null);
    }


    private static int i = 0;

    public static int Fribonacci(int n) {
        i++;
        if (n <= 2) {
            return 1;
        } else {
            return Fribonacci(n - 1) + Fribonacci(n - 2);
        }
    }


//
//    }


    static void swaper(Integer a, Integer b) {
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            int tmp = a.intValue();
            field.setInt(a, b);
            field.setInt(b, new Integer(tmp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void test(StringBuffer sb1, StringBuffer sb2) {
        sb1.append(sb2);
        sb2 = sb1;
//        return "ceshiwancheng ";
    }


    public static String getRegularNameStr(Object obj, Integer size) {
        StringBuffer sb = new StringBuffer();
        String str = obj.toString();
        int len = str.getBytes().length;
        sb.append("  " + str);
        if (len < size) {
            for (int i = 0; i < size - len; i += 2) {
                sb.append("  ");
            }
        }
        return sb.toString();
    }

    public static String getRegularMoneyStr(Object obj, Integer size) {
        StringBuffer sb = new StringBuffer();
        String str = obj.toString();
        int len = str.getBytes().length;
        if (len < size) {
            for (int i = 0; i < size - len; i += 2) {
                sb.append("  ");
            }
        }
        sb.append(str);
        return sb.toString();
    }

    public static String formatStr(Object obj, int length) {
        String str = obj.toString();
        if (str == null) {
            str = "";
        }
        int strLen = str.getBytes().length;
        if (strLen == length) {
            return str;
        } else if (strLen < length) {
            int temp = length - strLen;
            String tem = "";
            for (int i = 0; i < temp; i++) {
                tem = tem + " ";
            }
            return str + tem;
        } else {
            return str.substring(0, length);
        }

    }


    public static void main(String[] args) throws Exception {
        String str = "370281200207073522";
        String s = str.substring(6,10);
        System.out.println(s);
//        Integer ai = 1;
//        Integer bi = new Integer(1);
//        System.out.println(ai.equals(bi));
//        System.out.println(ai==bi);



//        Calendar calendar = Calendar.getInstance();
//        //设置时间成本周第一天(周日)
//        calendar.set(Calendar.DAY_OF_WEEK, 1);
//        //上周日时间
////        calendar.add(Calendar.DATE, 0);
//        //上周一时间
//        calendar.add(Calendar.DATE, -6);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //转化为日期
//        Date preWeekSunday = calendar.getTime();
//
//        String s = sdf.format(preWeekSunday);
//        System.out.println(Dates.getLastMonthFirstDay());
//        System.out.println(Dates.getLastMonthEndDay());
//        System.out.println(Dates.getLastWeekMonday());
//        System.out.println(Dates.getLastWeekSunday());

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.add(Calendar.MONTH, -1);
//        calendar1.set(Calendar.DAY_OF_MONTH,1);
//        String firstDay = sdf.format(calendar1.getTime());
//        //获取前一个月最后一天
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.set(Calendar.DAY_OF_MONTH, 0);
//        String lastDay = sdf.format(calendar2.getTime());
//
//        System.out.println(firstDay);
//        System.out.println(lastDay);
//
//        System.out.println(Dates.getCurrentMonthFirstDay());
//        System.out.println(Dates.getCurrentWeekMonDay());


//        System.out.println("编码长度汉字");
//        System.out.println("          ");
//
//
//        String a = " ";
//        System.out.println("UTF-8编码长度:" + a.getBytes("UTF-8").length);
//        System.out.println("GBK编码长度:" + a.getBytes("GBK").length);
//        System.out.println("GB2312编码长度:" + a.getBytes("GB2312").length);
//        System.out.println("==========================================");

//        String name1 = "西红柿土豆底单";
//        Integer num1 = 1;
//        Float money1 = 10F;
//
//        String name2 = "黄瓜";
//        Integer num2 = 123;
//        Float money2 = 10F;

//        System.out.println(String.format("%1$-11s","菜品名称") + String.format("%1$-4s","数量") + String.format("%1$-4s","金额"));
//        System.out.println(String.format("%1$-11s",name1) + String.format("%1$-4s",num1) + String.format("%1$-4s",money1));
//        System.out.println(String.format("%1$-11s",name2) + String.format("%1$-4s",num2) + String.format("%1$-4s",money2));

//
//        System.out.println("----------------------------------------------------------------------------------");
////        System.out.println(formatStr("菜品名称",9) + formatStr("数量",4) + formatStr("金额",4));
////        System.out.println(formatStr(name1,9) + formatStr(num1,4) + formatStr(money1,4));
////        System.out.println(formatStr(name1,9) + formatStr(num1,4) + formatStr(money1,4));
////
//
//
//        System.out.println("----------------------------------------------------------------------------------");
//
//        System.out.println(getRegularNameStr("菜品名称", 18) + getRegularMoneyStr("数量", 4) + getRegularMoneyStr("金额", 4));
//        System.out.println(getRegularNameStr(name1, 18) + getRegularMoneyStr(num1, 4) + getRegularMoneyStr(money1, 4));
//        System.out.println(getRegularNameStr(name2, 18) + getRegularMoneyStr(num2, 4) + getRegularMoneyStr(money2, 4));
//
//
//        System.out.println("----------------------------------------------------------------------------------");
//        System.out.println("java补空格:" + String.format("%1$-9s", name1));


//        Lock lock = new ReentrantLock();
//
//        List<String> list = new ArrayList<>();
//        System.out.println(list.size());


//        Integer i = 123;
//
//        boolean b = i instanceof Integer;
//
//        int a = 123;

//        boolean  boo = a instanceof Integer;

//        Map<String,Object> map = new HashMap<>();
//        map.put("1",1);
//        map.put("2",2);
//        JSONObject json = JSONObject.


//        User user = new User();
//        User user1 = new User();
//        System.out.println(user.equals(user1));
//        Map<String,List<String>> map = new HashMap<>();
//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("1");
//        list.add("1");
//        list.add("1");
//        map.put("1",list);
//        System.out.println(list);
//        System.out.println(map);

//        String pass = MD5.generate("123456", true);
//        System.out.println(pass);

//        StringBuffer a = new StringBuffer("M");
//        StringBuffer b = new StringBuffer("N");
//        a.append(b);
//        b =a;
//        System.out.println(a+"  --------------- "+b);


//        String pass = ShiroKit.md5("123", "BT");
//        System.out.println(pass);
//        double sum = 0;
//        int i;
//        boolean flag = true;
//        for (i = 201; i < 300; i++) {
//            for (int n = 2; n < i; n++) {
//                if (i % n == 0 && i != n) {
//                    flag = false;
//                    break;
//                }
//            }
//            if (flag) {
//                sum = sum + i;
//            }
//        }
//        System.out.println(sum);
//        if ((Integer)1 instanceof Object){
//            System.out.println(true);
//        }else {
//            System.out.println(false);
//        }

//        Declare.mwrf epen = MWUtils.getDLLConn();
//        MWUtils.DevConnect(epen);
//        String cardUid =  MWUtils.getUid(epen);;
//        StringBuilder stringBuilder = new StringBuilder();
//        if (cardUid != null && !"".equals(cardUid)) {
//            for (int i = 0; i < cardUid.length(); i++ ) {
//                String sb = cardUid.substring(cardUid.length() - i - 1,cardUid.length() - i);
//                stringBuilder.append(sb);
//            }
//        }
//        if (cardUid != null && !"".equals(cardUid)) {
//            for (int i = 0; i < cardUid.length(); i+=2) {
//                stringBuilder.append(cardUid.substring(i,2));
//            }
//        }
//        System.out.println(stringBuilder);
//        MWUtils.disconnectDev(epen);

//        try {
//            long start = System.currentTimeMillis();
//            System.out.println(PropertiesUtils.getMacAddress("114.215.108.23"));
//            long end = System.currentTimeMillis();
//            System.out.println(end -start );
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        try {
//            Map<String,String> obj = System.getenv();
//
//
//            String sIP = PropertiesUtils.getLocalHostIP();
//            String sMAC = PropertiesUtils.getLocalHostMAC();
//            System.out.println("IP：" + sIP);
//            System.out.println("MAC：" + sMAC);
//        }catch (Exception e){
//            e.printStackTrace();
//        }


//        System.out.println(System.currentTimeMillis());
//        Properties properties =  System.getProperties();
//        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
//            System.out.println(entry.getKey()+"---->"+entry.getValue());
//        }
//        System.out.println(properties.getProperty("os.name"));
//        System.out.println(properties.entrySet());
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String out = sdf.format(new Date());
//        System.out.println(out+"lala");


//        String password = ShiroKit.md5("123456","LSCTadmin");
//        System.out.println(password);

//        System.out.println("UUID: "+MD5Encode(UUID.randomUUID().toString(),null));
//        System.out.println(getMessageDigest("11111111".getBytes()));
//        System.out.println(MD5Encode("11111111", null));
    }
}
