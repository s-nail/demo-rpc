package com.hundsun.jrescloud.demo.rpc.server.common.util;

import com.hundsun.jrescloud.common.util.StringUtils;

import java.util.Map;
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
        private static CacheUtil cache = new CacheUtil();
    }

    public static CacheUtil getInstance() {
        return CacheUtilHolder.cache;
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

    /*public static void main(String[] args) {
        CacheUtil.getInstance().deleteAll("t");
        System.out.println(CacheUtil.getInstance().isExist("t", "t"));
    }*/


}
