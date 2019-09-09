package com.hundsun.jrescloud.demo.rpc.server.common.base;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.hundsun.jrescloud.demo.rpc.api.annotation.LicenseApi;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiayq24996 on 2019-07-10
 */
public class CustomCheckTest {
    @LicenseApi
    public LicenseResult customCheck() {
        LicenseResult result = new LicenseResult();
        System.out.println("=============pass customCheck===============");

        //result.add("自定义校验失败");
        return result;
    }

    @LicenseApi
    public LicenseResult test() {
        LicenseResult result = new LicenseResult();
        System.out.println("=============test===============");
        return result;
    }

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        int i = 0;
        while (i < 1000000) {
            i++;
            CustomCheckTest test = new CustomCheckTest();
            test.customCheck();
        }
        System.out.println("正常耗时：" + (System.currentTimeMillis() - begin));

        long begin2 = System.currentTimeMillis();
        Map<String, Class> map = new HashMap<>(16);
        int j = 0;
        while (j < 1000000) {
            j++;
            try {
                Class clazz;

                if (map.containsKey("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest")) {
                    clazz = map.get("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest");
                } else {
                    clazz = Class.forName("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest");
                    map.put("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest", clazz);
                    MethodAccess.get(clazz);
                }
                Method method = clazz.getMethod("customCheck");
                method.setAccessible(true);
                Object object = clazz.newInstance();
                method.invoke(object);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        System.out.println("反射耗时：" + (System.currentTimeMillis() - begin2));
    }
}
