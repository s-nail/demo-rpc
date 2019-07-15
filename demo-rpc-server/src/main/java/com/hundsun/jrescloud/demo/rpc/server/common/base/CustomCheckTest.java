package com.hundsun.jrescloud.demo.rpc.server.common.base;

import com.hundsun.jrescloud.common.util.StringUtils;
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
        /*if (StringUtils.isNotEmpty(licenseApi.jarname()) && !"spring".equals(licenseApi.jarname())){
            result.add("jar包名称不匹配");
        }
        if (StringUtils.isNotEmpty(licenseApi.type()) && !"1.0".equals(licenseApi.type())){
            result.add("Type不匹配");
        }
        if (StringUtils.isNotEmpty(licenseApi.version()) && !"1.0.1".equals(licenseApi.version())){
            result.add("version不匹配");
        }
        if (!licenseApi.openApi()){
            result.add("该接口不对外开放");
        }*/
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
