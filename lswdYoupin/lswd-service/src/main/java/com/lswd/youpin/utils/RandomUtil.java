package com.lswd.youpin.utils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liruilong on 2017/7/5.
 */
public class RandomUtil {

    /**
     * 获取一个指定位数的随机码
     *
     * @return
     */
    public static String getRandomCodeStr(Integer length) {
        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = new Date();

        String str = simpleDateFormat.format(date);
        Set<Integer> set = getRandomNumber(length);
        // 使用迭代器
        Iterator<Integer> iterator = set.iterator();
        // 临时记录数据
        String temp = "";
        while (iterator.hasNext()) {
            temp += iterator.next();
        }
        return str + temp;
    }

    /**
     * 获取随机数，并且不重复
     *
     * @return Set<Integer>
     */
    public static Set<Integer> getRandomNumber(Integer length) {
        // 使用SET以此保证写入的数据不重复
        Set<Integer> set = new HashSet<Integer>();
        // 随机数
        Random random = new Random();
        while (set.size() < length) {
            // nextInt返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）
            // 和指定值（不包括）之间均匀分布的 int 值。
            set.add(random.nextInt(10));
        }
        return set;
    }

    /**
     * 获取随机数，并且不重复
     *
     * @return String
     */
    public static String getRandom(Integer length) {
        // 使用SET以此保证写入的数据不重复
        Set<Integer> set = new HashSet<Integer>();
        // 随机数
        Random random = new Random();
        while (set.size() < length) {
            // nextInt返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）
            // 和指定值（不包括）之间均匀分布的 int 值。
            set.add(random.nextInt(10));
        }
        // 使用迭代器
        Iterator<Integer> iterator = set.iterator();
        // 临时记录数据
        String temp = "";
        while (iterator.hasNext()) {
            temp += iterator.next();
        }
        return temp;
    }



    public static void main(String args[]) {

        String r = RandomUtil.getRandom(6);
        System.out.print("-----------" + r);

    }
}
