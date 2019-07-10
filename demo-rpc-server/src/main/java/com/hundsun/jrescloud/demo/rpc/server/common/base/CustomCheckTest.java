package com.hundsun.jrescloud.demo.rpc.server.common.base;

import com.hundsun.jrescloud.demo.rpc.api.annotation.LicenseApi;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

/**
 * Created by jiayq24996 on 2019-07-10
 */
public class CustomCheckTest implements BaseCustomCheck {
    /**
     * 用户自定义校验
     *
     * @param licenseApi
     * @return
     */
    @Override
    public LicenseResult customCheck(LicenseApi licenseApi) {
        LicenseResult result = new LicenseResult();
        result.add("test ha");
        return result;
    }

    public static void main(String[] args) {
        try {
            BaseCustomCheck customCheck = (BaseCustomCheck) Class.forName("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest").newInstance();
            LicenseResult result = customCheck.customCheck(null);
            System.out.println(result.getAllErrors().toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
