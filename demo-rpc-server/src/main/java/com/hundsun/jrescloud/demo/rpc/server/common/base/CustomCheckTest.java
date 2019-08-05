package com.hundsun.jrescloud.demo.rpc.server.common.base;

import com.hundsun.jrescloud.demo.rpc.api.annotation.LicenseApi;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

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

}
