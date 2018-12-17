package com.lswd.youpin.wxpay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayConstants;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.*;


import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import static com.github.wxpay.sdk.WXPayConstants.FAIL;
import static com.github.wxpay.sdk.WXPayUtil.HMACSHA256;
import static com.github.wxpay.sdk.WXPayUtil.MD5;

/**
 * Created by liruilong on 2017/7/3.
 */
public class CommonUtil {

    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);


    public static boolean IsNumeric(String str) {
        if (str.matches("\\d *")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean IsAllNumeric(String str) {
        if (str.matches("^[0-9]*$")) {
            return true;
        } else {
            return false;
        }
    }

    public static String ArrayToXml(Map<String, String> arr) {
        String xml = "<xml>";

        Iterator<Map.Entry<String, String>> iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue();
            if (key.equals("total_fee")) {
                xml += "<" + key + ">" + val + "</" + key + ">";

            } else
                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
        }

        xml += "</xml>";
        return xml;
    }

    public static HashMap<String, String> parseXml(String strxml) throws Exception {
        // 解析结果存储在HashMap
        HashMap<String, String> map = new HashMap<String, String>();
        InputStream inputStream = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        return map;
    }

    /**
     * @param request
     * @return
     * @throws
     * @throws IOException
     * @Title: getSendMessage
     * @Description: 接收发送进来的xml解析
     */
    public static String getSendMessage(HttpServletRequest request) {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream in = request.getInputStream(); // 支持中文
            int ch;
            while ((ch = in.read()) >= 0)
                sb.append((char) ch);
            String result = new String(sb.toString().getBytes("ISO-8859-1"), "utf-8");
            sb = null;
            return result;
        } catch (Exception e) {
            // log.error(e.getMessage(), e);
            return "";
        }
    }

    public static JSONObject httpsRequestToJsonObject(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            String result = httpsRequest(requestUrl, requestMethod, outputStr);
            jsonObject = JSON.parseObject(result);
        } catch (Exception e) {
            log.error("https请求异常：" + e.getMessage());
        }
        return jsonObject;
    }


    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return 返回微信服务器响应的信息
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return null;
    }

    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 元转换成分
     *
     * @param amount
     * @return
     */
    public static String getMoney(String amount) {
        if (amount == null) {
            return "";
        }
        // 金额转化为分为单位
        String currency = amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws JDOMException
     */
    public static Map doXMLParse(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Document doc = builder.build(in);
        org.jdom.Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            org.jdom.Element e = (org.jdom.Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                org.jdom.Element e = (org.jdom.Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    public static String getResponseStr(String url, String xmlParam) {
        CloseableHttpClient client = HttpClients.createDefault();
        //client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpost = getPostMethod(url);
        String jsonStr = FAIL;
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = client.execute(httpost);
            jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static HttpPost getPostMethod(String url) {
        HttpPost pmethod = new HttpPost(url); // 设置响应头信息
        pmethod.addHeader("Connection", "keep-alive");
        pmethod.addHeader("Accept", "*/*");
        pmethod.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        pmethod.addHeader("Host", "api.mch.weixin.qq.com");
        pmethod.addHeader("X-Requested-With", "XMLHttpRequest");
        pmethod.addHeader("Cache-Control", "max-age=0");
        pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        return pmethod;
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            System.out.println(urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            connection.setRequestMethod("GET");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
//           InputStream inputStream= connection.getInputStream();
//           System.out.println(inputStream.read());
//            in = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            System.out.println(buffer.toString());
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            connection.disconnect();
            result=buffer.toString();
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
