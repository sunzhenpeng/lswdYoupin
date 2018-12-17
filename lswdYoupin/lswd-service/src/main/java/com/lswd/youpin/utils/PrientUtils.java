package com.lswd.youpin.utils;

import com.lswd.youpin.shiro.jedis.RedisManager;
import com.mchange.v2.ser.SerializableUtils;
import redis.clients.jedis.Jedis;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;
import java.awt.*;
import java.awt.print.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrientUtils implements Printable {

    private PrientBean bean;

    public PrientBean getBean() {
        return bean;
    }

    public void setBean(PrientBean bean) {
        this.bean = bean;
    }

    public static PrintService[] isPrint() {
        HashAttributeSet hs = new HashAttributeSet();
        String printerName = "POS58";
        hs.add(new PrinterName(printerName, null));
        PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, hs);
        if (pss.length == 0) {
            System.out.println("无法找到打印机:" + printerName);
            return null;
        }
        return pss;
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) graphics;
//        Jedis jedis = RedisUtilService.getJedis();
//        byte[] bytes = jedis.get("PrientBean".getBytes());
        RedisManager redisManager = new RedisManager();
        byte[] b = redisManager.get("PrientFlag".getBytes());
        Integer flag = Integer.valueOf(new String(b));
        if (flag == 0) {
            byte[] bytes = redisManager.get("PrientBean".getBytes());
            PrientBean bean = (PrientBean) SerializeUtils.deserialize(bytes);
            g2d.setFont(new Font("Default", Font.PLAIN, 14));
            g2d.drawString("量食餐饮管理系统", 20, 10);
            g2d.drawString("", 50, 25);
            g2d.drawString("--------会员充值--------", 7, 40);
            g2d.setFont(new Font("Default", Font.PLAIN, 10));
            g2d.drawString("  会员姓名：" + bean.getMemberName(), 7, 55);
            g2d.drawString("  联系方式：" + bean.getMemberTel(), 7, 70);
            g2d.drawString("  卡原余额：" + bean.getOldCardBalance(), 7, 85);
            g2d.drawString("  充值金额：" + bean.getChargeMoney(), 7, 100);
            g2d.drawString("  卡现余额：" + bean.getNewCardBalance(), 7, 115);
            g2d.drawString("  充值时间：" + bean.getChargeTime(), 7, 130);
            g2d.drawString("-------------------------------------", 7, 145);
        } else if (flag == 1) {
            byte[] bytes = redisManager.get("CountPrientBean".getBytes());
            ArrayList<CountPrientBean> list = (ArrayList<CountPrientBean>)SerializeUtils.deserialize(bytes);
            g2d.setFont(new Font("Default", Font.PLAIN, 14));
            g2d.drawString("量食餐饮管理系统", 20, 10);
            g2d.drawString("", 50, 25);
            g2d.drawString("--------会员消费明细--------", 7, 40);
            g2d.setFont(new Font("Default", Font.PLAIN, 10));
//            g2d.drawString(getRegularNameStr("菜品名称",9) + getRegularMoneyStr("数量",4) + getRegularMoneyStr("金额",4) , 7, 55);
            g2d.drawString("  菜品名称", 7, 55);
            g2d.drawString(" 数量", 95, 55);
            g2d.drawString(" 金额", 125, 55);
            for (int i = 0; i < list.size(); i++) {
                g2d.drawString("  "+list.get(i).getRecipeName(), 7, 70 + i * 15);
                g2d.drawString("  "+list.get(i).getCount(), 95, 70 + i * 15);
                g2d.drawString("  "+list.get(i).getMoney(), 125, 70 + i * 15);
            }
//            for (int i = 0; i < list.size(); i++) {
//                g2d.drawString(getRegularNameStr(list.get(i).getRecipeName(),9) + getRegularMoneyStr(list.get(i).getCount(),4) + getRegularMoneyStr(list.get(i).getMoney(),4),7, 70 + i * 15);
//            }
            g2d.drawString("  ********总额         " +list.get(0).getTotalMoney() + "********", 7, 85 + list.size() * 15);
            g2d.drawString("-------------------------------------------", 7, 100 + list.size() * 15);
        }
        return PAGE_EXISTS;
    }

    public static PrinterJob getPrinterJob() {
        int height = 175 + 3 * 15 + 20;
        // 通俗理解就是书、文档
        Book book = new Book();
        // 打印格式
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(230, height);
        p.setImageableArea(5, -20, 230, height + 20);
        pf.setPaper(p);
        // 把 PageFormat 和 Printable 添加到书中，组成一个页面
        book.append(new PrientUtils(), pf);
        // 获取打印服务对象
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(book);
        return job;
    }

    public static String getRegularNameStr(Object obj,Integer size){
        StringBuffer sb = new StringBuffer();
        String str = obj.toString();
        sb.append("  "+str);
        int len = str.length();
        if (len < size){
            for (int i = 0 ; i < size - len ; i+=2){
                sb.append("  ");
            }
        }
        return sb.toString();
    }

    public static String getRegularMoneyStr(Object obj,Integer size){
        StringBuffer sb = new StringBuffer();
        String str = obj.toString();
        int len = str.length();
        if (len < size){
            for (int i = 0 ; i < size - len ; i+=2){
                sb.append("  ");
            }
        }
        sb.append(str);
        return sb.toString();
    }

    public static void main(String[] args) {
        PrinterJob job = PrientUtils.getPrinterJob();
        PrientBean bean = new PrientBean("李瑞龙", "13112341234", 0F, 0F, 0F, "我是充值时间");
//        Jedis jedis = RedisUtilService.getJedis();
//        jedis.set(bean, bean.toString());
//        utils.setBean();
        RedisManager redisManager = new RedisManager();
        redisManager.set("PrientFlag".getBytes(), "1".getBytes());
        redisManager.set("PrientBean".getBytes(), SerializeUtils.serialize(bean));

        List<CountPrientBean> list = new ArrayList<CountPrientBean>();
        CountPrientBean bean1 = new CountPrientBean();
        bean1.setRecipeName("woshi xiaochi ");
        bean1.setCount(2);
        bean1.setMoney(4F);
        bean1.setTotalMoney(100F);
        for (int i = 0; i < 5; i++) {
            list.add(bean1);
        }
        redisManager.set("CountPrientBean".getBytes(), SerializeUtils.serialize(list));

        try {
            job.print();
        } catch (PrinterException e) {
            e.printStackTrace();
            System.out.println("================打印出现异常");
        }
    }
}
