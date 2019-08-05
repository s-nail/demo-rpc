package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public class ModuleValidate extends AbstractValidate {
    public ModuleValidate(int level) {
        this.level = level;
    }

    @Override
    protected LicenseResult universalCheck(String message) {
        System.out.println("ModuleValidate universalCheck" + message);
        LicenseResult result = new LicenseResult();
        return result;
    }

    @Override
    protected LicenseResult personalizedCheck(String message) {
        System.out.println("ModuleValidate personalizedCheck" + message);
        LicenseResult result = new LicenseResult();
        return result;
    }
}
