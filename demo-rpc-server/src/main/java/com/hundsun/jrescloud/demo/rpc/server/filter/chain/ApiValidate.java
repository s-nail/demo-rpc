package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public class ApiValidate extends AbstractValidate {

    public ApiValidate(int level) {
        this.level = level;
    }

    @Override
    protected LicenseResult universalCheck(String message) {
        System.out.println("ApiValidate universalCheck" + message);
        LicenseResult result = new LicenseResult();
        return result;
    }

    @Override
    protected LicenseResult personalizedCheck(String message) {
        System.out.println("ApiValidate personalizedCheck" + message);
        LicenseResult result = new LicenseResult();
        return result;
    }
}
