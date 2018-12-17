package com.lswd.youpin.web.utils;

import com.lswd.youpin.common.util.Strings;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by liruilong on 17/4/13.
 */
public class XmlUtils {
    private static final Logger log = LoggerFactory.getLogger(XmlUtils.class);

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return Map<String, String>
     * @throws Exception
     */
   public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList)
            map.put(e.getName(), e.getText());
        inputStream.close();
        inputStream = null;
        return map;
    }

    /**
     * 将map转成xml格式
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        if (data == null || data.isEmpty()) {
            log.error("转XML的数据为空");
            return null;
        }
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
        Map.Entry<String, String> item = null;
        sb.append("<xml>");
        while (it.hasNext()) {
            item = it.next();
            if (Strings.isNullOrEmpty(item.getKey()) || Strings.isNullOrEmpty(item.getValue())) {
                log.error("Data中存在null或空串");
                sb = new StringBuffer();//置空
                break;
            }
            sb.append("<").append(item.getKey()).append(">");
            sb.append("<![CDATA[").append(item.getValue()).append("]]>");
            sb.append("</").append(item.getKey()).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 扩展xstream使其支持CDATA
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}
