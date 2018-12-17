package com.lswd.youpin.common.util;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesUtils {

    /**
     * 获取本机ip地址
     * @return
     * @throws Exception
     */
    public static String getLocalHostIP() throws Exception{
        InetAddress address = InetAddress.getLocalHost();
        String sIP = address.getHostAddress();
        return sIP;
    }

    /**
     * 获取本机计算机名称
     * @return
     * @throws Exception
     */
    public static String getLocalHostName() throws Exception{
        InetAddress address = InetAddress.getLocalHost();
        String sName = address.getHostName();
        return sName;
    }

    /**
     * 获得本机MAC地址
     * @return
     * @throws Exception
     */
    public static String getLocalHostMAC() throws Exception{
        InetAddress address = InetAddress.getLocalHost();
        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
        byte[] mac = ni.getHardwareAddress();
        String sMAC = "";
        Formatter formatter = new Formatter();
        for (int i = 0; i < mac.length; i++) {
            sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i],
                    (i < mac.length - 1) ? "-" : "").toString();
        }
        return sMAC;
    }

    /**
     * 执行单条指令
     * @param cmd 命令
     * @return 执行结果
     * @throws Exception
     */
    public static String command(String cmd) throws Exception{
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
        InputStream in = process.getInputStream();
        StringBuilder result = new StringBuilder();
        byte[] data = new byte[256];
        while(in.read(data) != -1){
            String encoding = System.getProperty("sun.jnu.encoding");
            result.append(new String(data,encoding));
        }
        return result.toString();
    }


    /**
     * 根据ip获取局域网内的mac地址
     * @param ip
     * @return
     * @throws Exception
     */
    public static String getMacAddress(String ip) throws Exception{
        String result = command("ping "+ip+" -n 2");
        if(result.contains("TTL")){
            result = command("arp -a "+ip);
        }
        String regExp = "([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(result);
        StringBuilder mac = new StringBuilder();
        while (matcher.find()) {
            String temp = matcher.group();
            mac.append(temp);
        }
        return mac.toString();
    }
}
