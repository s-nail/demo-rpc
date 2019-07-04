package com.hundsun.jrescloud.demo.rpc.server.common.util;

import cn.hutool.core.util.StrUtil;
import com.thoughtworks.xstream.XStream;

/**
 * @author jiayq24996
 */
public class XStreamUtil {
    /**
     * java 转换成xml
     *
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String beanToXml(Object obj) {
        XStream xstream = new XStream();
        xstream.processAnnotations(obj.getClass());
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + (StrUtil.isBlank(xstream.toXML(obj)) ? StrUtil.EMPTY : xstream.toXML(obj).replaceAll("__", "_"));
    }

    /**
     * 将传入xml文本转换成Java对象
     * s
     *
     * @param xmlStr
     * @param types  xml对应的class类
     * @return T   xml对应的class类的实例对象
     */
    public static <T> T xmlToBean(String xmlStr, Class[] types) {
        XStream xstream = new XStream();
        xstream.processAnnotations(types);
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(types);
        T obj = (T) xstream.fromXML(xmlStr);
        return obj;
    }
}