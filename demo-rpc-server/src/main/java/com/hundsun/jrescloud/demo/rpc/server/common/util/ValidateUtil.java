package com.hundsun.jrescloud.demo.rpc.server.common.util;

import java.io.File;
import java.io.FileNotFoundException;

import com.hundsun.jrescloud.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;

import com.hundsun.jrescloud.demo.rpc.server.common.dto.Api;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Module;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Product;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;

import cn.hutool.core.date.DateUtil;

/**
 * Created by jiayq24996 on 2019-06-14
 */
public class ValidateUtil {

    public static LicenseResult commonCheck(String licenceNo) {
        LicenseResult result = new LicenseResult();
        SAXReader reader = new SAXReader();
        if (licenceNo == null) {
            result.add(ValidateEnum.PRODUCT_LICENSE_NO_IS_NULL_ERROR.getMessage());
            return result;
        }
        try {
            File file = ResourceUtils.getFile("classpath:licence/" + licenceNo + ".lic");
            if (file == null) {
                result.add(ValidateEnum.FILE_NOT_FOUND_OR_DOCUMENT_ERROR.getMessage());
                return result;
            }
            Document document = reader.read(file);
            Product busiProduct = XStreamUtil.xmlToBean(document.asXML(), new Class[]{Product.class, Module.class, Api.class});
            Product sdkProduct = (Product) CacheUtil.getInstance().getCache(CacheUtil.PRODUCT_CACHE_NAME, licenceNo);
            if (productCheck(busiProduct, sdkProduct).hasErrors()) {
                return productCheck(busiProduct, sdkProduct);
            }
            if (CollectionUtils.isNotEmpty(busiProduct.getModules())) {
                for (Module m : busiProduct.getModules()) {
                    Module sdkModule = (Module) CacheUtil.getInstance().getCache(CacheUtil.MODULE_CACHE_NAME, m.getModuleNo());
                    LicenseResult moduleCheckResult = moduleCheck(m, sdkModule);
                    if (moduleCheckResult.hasErrors()) {
                        return moduleCheckResult;
                    }
                    if (CollectionUtils.isNotEmpty(m.getApiSet())) {
                        for (Api api : m.getApiSet()) {
                            Api sdkApi = (Api) CacheUtil.getInstance().getCache(CacheUtil.API_CACHE_NAME, api.getFunctionId());
                            LicenseResult apiCheckResult = apiCheck(api, sdkApi);
                            if (apiCheckResult.hasErrors()) {
                                return apiCheckResult;
                            }
                        }
                    }
                }
            }
        } catch (DocumentException | FileNotFoundException e) {
            result.add(ValidateEnum.UNKNOWN_ERROR.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static LicenseResult productCheck(Product busiProduct, Product sdkProduct) {
        LicenseResult result = new LicenseResult();
        if (!busiProduct.getLicenceType().endsWith(sdkProduct.getLicenceType())) {
            result.add(ValidateEnum.PRODUCT_LICENSE_TYPE_ERROR.getMessage());
        }
        if (DateUtil.parse(busiProduct.getBeginDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.PRODUCT_LICENSE_BEGIN_DATE_ERROR.getMessage());
        }
        if (!DateUtil.parse(busiProduct.getExpireDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.PRODUCT_LICENSE_EXPRIE_DATE_ERROR.getMessage());
        }
        if (Integer.parseInt(busiProduct.getFlowControl()) > Integer.parseInt(sdkProduct.getFlowControl())) {
            result.add(ValidateEnum.PRODUCT_LICENSE_FOLLOW_CONTTROL_ERROR.getMessage());
        }
        return result;
    }

    public static LicenseResult moduleCheck(Module busiModule, Module sdkModule) {
        LicenseResult result = new LicenseResult();
        if (StringUtils.isNotEmpty(busiModule.getModuleName()) && !busiModule.getModuleName().equals(sdkModule.getModuleName())) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_NAME_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(busiModule.getModuleNo()) && !busiModule.getModuleNo().equals(sdkModule.getModuleNo())) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_NO_ERROR.getMessage());
        }
        if (DateUtil.parse(busiModule.getBeginDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_BEGIN_DATE_ERROR.getMessage());
        }
        if (!DateUtil.parse(busiModule.getExpireDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_EXPIRE_DATE_ERROR.getMessage());
        }
        if (busiModule.getMaxConnections() > sdkModule.getMaxConnections()) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_MAX_CONNECTIONS_ERROR.getMessage());
        }
        if (Integer.parseInt(busiModule.getFlowControl()) > Integer.parseInt(sdkModule.getFlowControl())) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_FLOW_CONTROL_ERROR.getMessage());
        }
        return result;
    }

    public static LicenseResult apiCheck(Api busiApi, Api sdkApi) {
        LicenseResult result = new LicenseResult();
        if (StringUtils.isNotEmpty(busiApi.getApiName()) && !busiApi.getApiName().equals(sdkApi.getApiName())) {
            result.add(ValidateEnum.API_NAME_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(busiApi.getFunctionId()) && !busiApi.getFunctionId().equals(sdkApi.getFunctionId())) {
            result.add(ValidateEnum.API_FUNCTION_ID_ERROR.getMessage());
        }
        if (DateUtil.parse(busiApi.getBeginDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.API_BEGIN_DATE_ERROR.getMessage());
        }
        if (!DateUtil.parse(busiApi.getExpireDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.API_EXPIRE_DATE_ERROR.getMessage());
        }
        if (Integer.parseInt(busiApi.getFlowControl()) > Integer.parseInt(sdkApi.getFlowControl())) {
            result.add(ValidateEnum.API_FLOW_CONTROL_ERROR.getMessage());
        }
        return result;

    }

    public static LicenseResult apiCheck1(String functionId) {
        //System.out.println("+++++++++++++++++++++++"+functionId);
        LicenseResult result = new LicenseResult();
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.API_CACHE_NAME, functionId);
        if (!flag) {
            result.add(functionId + ValidateEnum.API_FUNCTION_ID_ERROR.getMessage());
        }
        return result;
    }


}
