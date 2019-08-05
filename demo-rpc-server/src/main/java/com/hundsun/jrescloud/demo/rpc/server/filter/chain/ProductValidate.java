package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public class ProductValidate extends AbstractValidate {
    public ProductValidate(int level) {
        this.level = level;
    }

    @Override
    protected LicenseResult universalCheck(String message) {
        System.out.println("ProductValidate universalCheck" + message);
        LicenseResult result = new LicenseResult();
        //result.add("产品通用校验失败");
        return result;
    }

    @Override
    protected LicenseResult personalizedCheck(String message) {
        System.out.println("ProductValidate personalizedCheck" + message);
        LicenseResult result = new LicenseResult();
        return result;
    }
}
