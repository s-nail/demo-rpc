package com.hundsun.jrescloud.demo.rpc.server.common.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.*;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static String getProduct() {
        Product product = new Product();
        product.setProductName("国富");
        product.setCustomerName("招商银行");
        product.setBeginDate("20190701");
        product.setExpireDate("20200901");
        product.setLicenceNo("1111222");

        Map map = new HashMap(16);
        map.put("name", "product");
        map.put("customer", "招商银行");
        String json = JSON.toJSONString(map);
        //System.out.println(json);
        product.setExtendField(json);

        PersonalizedElement personalizedElement = new PersonalizedElement();
        personalizedElement.setClassName("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest");
        personalizedElement.setFunctionName("customCheck");
        personalizedElement.setValidateField("测试自定义方法");
        personalizedElement.setRemark("test");
        List list = new ArrayList();
        list.add(personalizedElement);
        product.setPersonalizedElementSet(list);

        Module module = new Module();
        module.setGsv("group_demo-rpc-server_v");
        module.setBeginDate("20190701");
        module.setExpireDate("20200901");
        List list2 = new ArrayList();
        PersonalizedElement personalizedElement2 = new PersonalizedElement();
        BeanUtils.copyProperties(personalizedElement, personalizedElement2);
        list2.add(personalizedElement2);
        module.setPersonalizedElementSet(list2);
        List<String> machineCodeList = new ArrayList();
        machineCodeList.add("BFEBFBFF000406E3_/C239GC2/CN12963669137B/");
        MachineCodeSet machineCodeSet = new MachineCodeSet();
        machineCodeSet.setMachineCode(machineCodeList);
        module.setMachineCodeSet(machineCodeSet);

        Api api = new Api();
        api.setFunctionId("331100");
        api.setApiName("login");
        api.setBeginDate("20190701");
        api.setExpireDate("20190701");
        api.setFlowControl(15);


        Api api2 = new Api();
        api2.setFunctionId("331101");
        api2.setApiName("list");
        api2.setBeginDate("20190701");
        api2.setExpireDate("20210701");
        api2.setFlowControl(15);
        List<Api> apiList = new ArrayList();
        apiList.add(api);
        apiList.add(api2);
        module.setApiSet(apiList);

        List<Module> moduleList = new ArrayList();
        moduleList.add(module);

        product.setModules(moduleList);

        String str = XStreamUtil.beanToXml(product);
       /* System.out.println(str);

        product = XStreamUtil.xmlToBean(str, new Class[]{Product.class, Module.class, Api.class, PersonalizedElement.class, MachineCodeSet.class});*/

        return str;
    }
}