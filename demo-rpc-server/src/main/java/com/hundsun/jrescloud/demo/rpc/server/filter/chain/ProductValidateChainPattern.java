package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import cn.hutool.core.date.DateUtil;
import com.hundsun.jrescloud.common.util.StringUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.PersonalizedElement;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Product;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.ValidateParam;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;
import com.hundsun.jrescloud.demo.rpc.server.common.util.CacheUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.ValidateEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public class ProductValidateChainPattern extends AbstractValidateChainPattern {
    public ProductValidateChainPattern(int level) {
        this.level = level;
    }

    @Override
    protected LicenseResult universalCheck(ValidateParam param) {
        LicenseResult result = new LicenseResult();
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.PRODUCT_CACHE_NAME, param.getProductName());
        if (!flag) {
            result.add(param.getProductName() + ValidateEnum.PRODUCT_PRODUCT_NAME_IS_NULL_ERROR.getMessage());
            return result;
        }
        Product product = (Product) CacheUtil.getInstance().getCache(CacheUtil.PRODUCT_CACHE_NAME, param.getProductName());
        if (StringUtils.isNotEmpty(param.getLicenceType()) && !param.getLicenceType().equals(product.getLicenceType())) {
            result.add(ValidateEnum.PRODUCT_LICENSE_TYPE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(product.getBeginDate()) && DateUtil.parse(product.getBeginDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.PRODUCT_LICENSE_BEGIN_DATE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(product.getExpireDate()) && !DateUtil.parse(product.getExpireDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.PRODUCT_LICENSE_EXPRIE_DATE_ERROR.getMessage());
        }
        if (param.getFlowControl() != null && product.getFlowControl() != null && param.getFlowControl() > product.getFlowControl()) {
            result.add(ValidateEnum.PRODUCT_LICENSE_FOLLOW_CONTTROL_ERROR.getMessage());
        }
        return result;
    }

    @Override
    protected LicenseResult personalizedCheck(ValidateParam param) {
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.PERSONALIZED_ELEMENT_CACHE_NAME, param.getProductName());
        if (!flag) {
            return null;
        }
        List<PersonalizedElement> personalizedElementSet = (ArrayList<PersonalizedElement>) CacheUtil.getInstance().getCache(CacheUtil.PERSONALIZED_ELEMENT_CACHE_NAME, param.getProductName());
        return this.invoke(personalizedElementSet);
    }
}
