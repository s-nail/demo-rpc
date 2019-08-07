package com.hundsun.jrescloud.demo.rpc.server.common.util;

import com.hundsun.jrescloud.common.util.StringUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Api;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.ExtendField;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Module;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiayq24996 on 2019-06-14
 */
public class CacheUtil {

    /**
     * 默认缓存
     */
    public static String DEFAULT_CACHE_NAME = "default";
    /**
     * 产品缓存
     */
    public static String PRODUCT_CACHE_NAME = "product";
    /**
     * 产品模块缓存
     */
    public static String MODULE_CACHE_NAME = "module";
    /**
     * 接口缓存
     */
    public static String API_CACHE_NAME = "api";
    /**
     * 自定义校验元素缓存
     */
    public static String CUSTOM_ELEMENT_CACHE_NAME = "custom_element";

    private Map<String, ConcurrentHashMap<String, Object>> cache = new ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>(16);

    /**
     * 延长初始化占位类模式
     */
    private static class CacheUtilHolder {
        private static CacheUtil cacheUtil = new CacheUtil();
    }

    public static CacheUtil getInstance() {
        return CacheUtilHolder.cacheUtil;
    }


    /**
     * 获取缓存数据中的某一条记录
     *
     * @param cacheName 缓存内容名称
     * @param key       缓存记录标识
     * @return
     * @since Ver 1.0
     */
    public Object getCache(String cacheName, String key) {
        ConcurrentHashMap<String, Object> camp = cache.get(cacheName);
        if (camp == null) {
            return null;
        }
        Object element = camp.get(key);
        if (element != null) {
            return element;
        }
        return null;
    }

    /**
     * 获取缓存长度
     *
     * @param cacheName 缓存内容名称
     * @return
     * @since Ver 1.0
     */
    public int getCacheSize(String cacheName) {
        ConcurrentHashMap<String, Object> camp = cache.get(cacheName);
        if (camp == null) {
            return 0;
        }
        return camp.size();
    }

    /**
     * 添加缓存数据
     *
     * @param cacheName 缓存内容名称
     * @param key       缓存记录标识
     * @param obj       缓存内容
     * @since Ver 1.0
     */
    public void addCache(String cacheName, String key, Object obj) {
        ConcurrentHashMap<String, Object> camp = cache.get(cacheName);
        if (camp == null) {
            camp = new ConcurrentHashMap<>(1000);
        }
        camp.put(key, obj);
        cache.put(cacheName, camp);
    }

    /**
     * 删除缓存数据中的某一条记录
     *
     * @param cacheName 缓存内容名称
     * @param key       缓存记录标识
     * @since Ver 1.0
     */
    public void deleteCache(String cacheName, String key) {
        ConcurrentHashMap<String, Object> camp = cache.get(cacheName);
        if (camp == null) {
            return;
        }
        camp.remove(key);
    }

    /**
     * 删除缓存中全部数据
     *
     * @param cacheName 缓存内容名称
     * @since Ver 1.0
     */
    public void deleteAll(String cacheName) {
        ConcurrentHashMap<String, Object> camp = cache.get(cacheName);
        if (camp == null) {
            return;
        }
        camp.clear();
    }

    /**
     * 判断是否存在
     *
     * @param cacheName 缓存内容名称
     * @param key       缓存记录标识
     * @return
     * @since Ver 1.0
     */
    public boolean isExist(String cacheName, String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        ConcurrentHashMap<String, Object> camp = cache.get(cacheName);
        if (camp == null) {
            return false;
        }
        Object element = camp.get(key);
        if (element != null) {
            return true;
        }
        return false;
    }

    public Map copy() {
        Map<String, ConcurrentHashMap<String, Object>> cloneMap = new ConcurrentHashMap<>(16);
        Iterator iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ConcurrentHashMap> entry = (Map.Entry<String, ConcurrentHashMap>) iterator.next();
            ConcurrentHashMap<String, Object> cloneCMap = new ConcurrentHashMap<>();
            cloneCMap.putAll(entry.getValue());
            cloneMap.put(entry.getKey(), cloneCMap);
        }
        return cloneMap;
    }

    public static void main(String[] args) {
        /*CacheUtil.getInstance().deleteAll("t");
        System.out.println(CacheUtil.getInstance().isExist("t", "t"));*/
        ExtendField field = new ExtendField();
        field.setClassName("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest");
        field.setFunctionName("customCheck");

        ExtendField field1 = new ExtendField();
        field1.setClassName("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest");
        field1.setFunctionName("test");

        List<ExtendField> list = new ArrayList<>();
        list.add(field);
        list.add(field1);
        Product product = new Product();
        product.setLicenceNo("1111");
        product.setBeginDate("20190610");
        product.setExpireDate("20210610");
        product.setProductInfo("操作员中心");
        product.setUserInfo("admin from 操作员中心");
        //product.setExtendFieldSet(list);
        CacheUtil.getInstance().addCache(CacheUtil.PRODUCT_CACHE_NAME, product.getLicenceNo(), product);
        //CacheUtil.getInstance().addCache(CacheUtil.CUSTOM_ELEMENT_CACHE_NAME, product.getLicenceNo(), product.getExtendFieldSet());

        Module module = new Module();
        module.setModuleName("demo-rpc-server");
        //module.setExpireDate("20190610");
        module.setExtendFieldSet(list);
        CacheUtil.getInstance().addCache(CacheUtil.MODULE_CACHE_NAME, module.getModuleName(), module);
        CacheUtil.getInstance().addCache(CacheUtil.CUSTOM_ELEMENT_CACHE_NAME, module.getModuleName(), module.getExtendFieldSet());

        Api api = new Api();
        api.setFunctionId("111500");
        api.setExpireDate("20190610");
        api.setApiName("TEST");
        api.setFlowControl("12");
        api.setExtendFieldSet(list);
        CacheUtil.getInstance().addCache(CacheUtil.API_CACHE_NAME, api.getFunctionId(), api);
        CacheUtil.getInstance().addCache(CacheUtil.CUSTOM_ELEMENT_CACHE_NAME, api.getFunctionId(), api.getExtendFieldSet());
        Map map = CacheUtil.getInstance().copy();
        System.out.println(map);
        CacheUtil.getInstance().deleteAll(CacheUtil.PRODUCT_CACHE_NAME);
        CacheUtil.getInstance().deleteAll(CacheUtil.MODULE_CACHE_NAME);
        CacheUtil.getInstance().deleteAll(CacheUtil.API_CACHE_NAME);
        CacheUtil.getInstance().deleteAll(CacheUtil.CUSTOM_ELEMENT_CACHE_NAME);
        System.out.println(map);
    }

}
