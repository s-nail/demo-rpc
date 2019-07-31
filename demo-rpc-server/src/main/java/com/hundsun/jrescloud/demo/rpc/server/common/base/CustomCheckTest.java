package com.hundsun.jrescloud.demo.rpc.server.common.base;

import com.hundsun.jrescloud.demo.rpc.api.annotation.LicenseApi;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by jiayq24996 on 2019-07-10
 */
public class CustomCheckTest {
    @LicenseApi
    public LicenseResult customCheck() {
        LicenseResult result = new LicenseResult();
        System.out.println("=============customCheck===============");
        result.add("自定义校验失败");
        return result;
    }

    @LicenseApi
    public LicenseResult test() {
        LicenseResult result = new LicenseResult();
        System.out.println("=============test===============");
        return result;
    }

    public static void main(String[] args) {
        //CacheUtil.getInstance().getCache(, )
        try {
            Class clazz = Class.forName("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest");
            Method method = clazz.getMethod("customCheck");
            Object object = clazz.newInstance();
            LicenseResult result = (LicenseResult) method.invoke(object);
            System.out.println("============================" + result.hasErrors());
            /*for (Method method : object.getDeclaredMethods()) {
                LicenseProduct licenseProduct = method.getAnnotation(LicenseProduct.class);
                System.out.println(licenseProduct);
                method.invoke(object);
            }
*/

//            BaseCustomCheck customCheck = (BaseCustomCheck) Class.forName("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest").newInstance();
//            LicenseResult result = customCheck.customCheck(null);
//            System.out.println(result.getAllErrors().toString());
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
}
