package com.hundsun.jrescloud.demo.rpc.server.common.util;

import cn.hutool.core.date.DateUtil;
import com.hundsun.jrescloud.common.util.StringUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Api;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Module;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Product;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

/**
 * Created by jiayq24996 on 2019-06-14
 */
public class ValidateUtil {

    public static LicenseResult apiCheck(String functionId, String flowControl) {
        LicenseResult result = new LicenseResult();
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.API_CACHE_NAME, functionId);
        if (!flag) {
            result.add(functionId + ValidateEnum.API_FUNCTION_ID_ERROR.getMessage());
            return result;
        }
        Api api = (Api) CacheUtil.getInstance().getCache(CacheUtil.API_CACHE_NAME, functionId);
        if (StringUtils.isNotEmpty(api.getBeginDate()) && DateUtil.parse(api.getBeginDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.API_BEGIN_DATE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(api.getExpireDate()) && !DateUtil.parse(api.getExpireDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.API_EXPIRE_DATE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(flowControl) && StringUtils.isNotEmpty(api.getFlowControl()) && Integer.parseInt(flowControl) > Integer.parseInt(api.getFlowControl())) {
            result.add(ValidateEnum.API_FLOW_CONTROL_ERROR.getMessage());
        }
        return result;
    }

    public static LicenseResult moduleCheck(String moduleName, String moduleNo, Integer maxConnections, String flowControl) {
        LicenseResult result = new LicenseResult();
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.MODULE_CACHE_NAME, moduleName);
        if (!flag) {
            result.add(moduleName + ValidateEnum.MODULE_LICENSE_MODULE_NAME_NOT_EXIST_ERROR.getMessage());
            return result;
        }
        Module module = (Module) CacheUtil.getInstance().getCache(CacheUtil.MODULE_CACHE_NAME, moduleName);
        if (StringUtils.isNotEmpty(moduleNo) && !moduleNo.equals(module.getModuleNo())) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_NO_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(module.getBeginDate()) && DateUtil.parse(module.getBeginDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_BEGIN_DATE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(module.getExpireDate()) && !DateUtil.parse(module.getExpireDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_EXPIRE_DATE_ERROR.getMessage());
        }
        if (maxConnections != null && module.getMaxConnections() != null && maxConnections > module.getMaxConnections()) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_MAX_CONNECTIONS_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(flowControl) && StringUtils.isNotEmpty(module.getFlowControl()) && Integer.parseInt(flowControl) > Integer.parseInt(module.getFlowControl())) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_FLOW_CONTROL_ERROR.getMessage());
        }
        return result;
    }

    public static LicenseResult productCheck(String licenceNo, String licenceType, String flowControl) {
        LicenseResult result = new LicenseResult();
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.PRODUCT_CACHE_NAME, licenceNo);
        if (!flag) {
            result.add(licenceNo + ValidateEnum.PRODUCT_LICENSE_NO_IS_NULL_ERROR.getMessage());
            return result;
        }
        Product product = (Product) CacheUtil.getInstance().getCache(CacheUtil.PRODUCT_CACHE_NAME, licenceNo);
        if (StringUtils.isNotEmpty(licenceType) && !licenceType.equals(product.getLicenceType())) {
            result.add(ValidateEnum.PRODUCT_LICENSE_TYPE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(product.getBeginDate()) && DateUtil.parse(product.getBeginDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.PRODUCT_LICENSE_BEGIN_DATE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(product.getExpireDate()) && !DateUtil.parse(product.getExpireDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.PRODUCT_LICENSE_EXPRIE_DATE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(flowControl) && StringUtils.isNotEmpty(product.getFlowControl()) && Integer.parseInt(flowControl) > Integer.parseInt(product.getFlowControl())) {
            result.add(ValidateEnum.PRODUCT_LICENSE_FOLLOW_CONTTROL_ERROR.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        Product product = new Product();
        product.setLicenceNo("1111");
        product.setBeginDate("20190610");
        product.setExpireDate("20110610");
        product.setProductInfo("操作员中心");
        product.setUserInfo("admin from 操作员中心");
        CacheUtil.getInstance().addCache(CacheUtil.PRODUCT_CACHE_NAME, product.getLicenceNo(), product);

        Module module = new Module();
        module.setModuleName("demo-rpc-server");
        CacheUtil.getInstance().addCache(CacheUtil.MODULE_CACHE_NAME, module.getModuleName(), module);
        Api api = new Api();
        api.setFunctionId("111500");
        api.setApiName("TEST");
        api.setFlowControl("12");
        CacheUtil.getInstance().addCache(CacheUtil.API_CACHE_NAME, api.getFunctionId(), api);

        LicenseResult result = ValidateUtil.productCheck("1111", "1", null);
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors().toString());
        }
        result = ValidateUtil.moduleCheck("demo-rpc-server", null, null, null);
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors().toString());
        }
        result = ValidateUtil.apiCheck("111500", "133");
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors().toString());
        }

    }


}
