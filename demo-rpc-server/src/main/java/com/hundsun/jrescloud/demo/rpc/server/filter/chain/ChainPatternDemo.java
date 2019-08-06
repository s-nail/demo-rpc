package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import com.hundsun.jrescloud.demo.rpc.server.common.dto.*;
import com.hundsun.jrescloud.demo.rpc.server.common.util.CacheUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public class ChainPatternDemo {
    private static AbstractValidateChainPattern getChainOfValidate() {

        AbstractValidateChainPattern productValidate = new ProductValidateChainPattern(AbstractValidateChainPattern.PRODUCT);
        AbstractValidateChainPattern moduleValidate = new ModuleValidateChainPattern(AbstractValidateChainPattern.MODULE);
        AbstractValidateChainPattern apiValidate = new ApiValidateChainPattern(AbstractValidateChainPattern.API);

        productValidate.setNextValidate(moduleValidate);
        moduleValidate.setNextValidate(apiValidate);

        return productValidate;
    }

    public static void main(String[] args) {

        ExtendField field =new ExtendField();
        field.setClassName("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest");
        field.setFunctionName("customCheck");

        ExtendField field1 =new ExtendField();
        field1.setClassName("com.hundsun.jrescloud.demo.rpc.server.common.base.CustomCheckTest");
        field1.setFunctionName("test");

        List<ExtendField> list = new ArrayList<>();
        list.add(field);
        list.add(field1);
        Product product = new Product();
        product.setLicenceNo("1111");
        product.setBeginDate("20190610");
        product.setExpireDate("20210610");
        product.setProductInfo("操作员中心");
        product.setUserInfo("admin from 操作员中心");
        //product.setExtendFieldSet(list);
        CacheUtil.getInstance().addCache(CacheUtil.PRODUCT_CACHE_NAME, product.getLicenceNo(), product);
        //CacheUtil.getInstance().addCache(CacheUtil.CUSTOM_ELEMENT_CACHE_NAME, product.getLicenceNo(), product.getExtendFieldSet());

        Module module = new Module();
        module.setModuleName("demo-rpc-server");
        //module.setExpireDate("20190610");
        module.setExtendFieldSet(list);
        CacheUtil.getInstance().addCache(CacheUtil.MODULE_CACHE_NAME, module.getModuleName(), module);
        CacheUtil.getInstance().addCache(CacheUtil.CUSTOM_ELEMENT_CACHE_NAME, module.getModuleName(), module.getExtendFieldSet());

        Api api = new Api();
        api.setFunctionId("111500");
        api.setExpireDate("20190610");
        api.setApiName("TEST");
        api.setFlowControl("12");
        api.setExtendFieldSet(list);
        CacheUtil.getInstance().addCache(CacheUtil.API_CACHE_NAME, api.getFunctionId(), api);
        CacheUtil.getInstance().addCache(CacheUtil.CUSTOM_ELEMENT_CACHE_NAME, api.getFunctionId(), api.getExtendFieldSet());

        ValidateParam param = new ValidateParam();
        param.setLicenceNo("1111");
        param.setModuleName("demo-rpc-server");
        param.setFunctionId("111500");
        AbstractValidateChainPattern loggerChain = getChainOfValidate();

       /* loggerChain.check(AbstractValidateChainPattern.API, "This is a API.");

        loggerChain.check(AbstractValidateChainPattern.MODULE,
                "This is a MODULE.");*/

        loggerChain.check(AbstractValidateChainPattern.PRODUCT, param);
    }
}
