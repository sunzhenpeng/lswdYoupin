package com.lswd.youpin.common.util;

import java.awt.*;
import java.util.Random;

/**
 * Created by SAMA on 2017/6/1.
 */
public class Verifycode {
    /**
     *@author liuhao
     * @param fc
     * @param bc
     * @return Color
     * 功能 绘制图像干扰线
     */
    public static Color getRandColor(int fc, int bc){
        Random random = new Random();
        if(fc>255)
            fc = 255;
        if(bc>255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }

    /**
     *
     * @param num
     * @return
     * 生成随机数字
     */
    public static char[] generateRandomArray(int num) {
        String chars = "0123456789";
        char[] rands = new char[num];
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }

}
