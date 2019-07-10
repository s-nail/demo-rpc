package com.hundsun.jrescloud.demo.rpc.server.common.base;

import com.hundsun.jrescloud.demo.rpc.api.annotation.LicenseApi;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

/**
 * Created by jiayq24996 on 2019-07-10
 */
public interface BaseCustomCheck {

    /**
     * 用户自定义校验
     * @param licenseApi
     * @return
     */
    LicenseResult customCheck(LicenseApi licenseApi);
}
