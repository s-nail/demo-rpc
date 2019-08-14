package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import cn.hutool.core.date.DateUtil;
import com.hundsun.jrescloud.common.util.StringUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Api;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.PersonalizedElement;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.ValidateParam;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;
import com.hundsun.jrescloud.demo.rpc.server.common.util.CacheUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.ValidateEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public class ApiValidateChainPattern extends AbstractValidateChainPattern {

    public ApiValidateChainPattern(int level) {
        this.level = level;
    }

    @Override
    protected LicenseResult universalCheck(ValidateParam param) {
        LicenseResult result = new LicenseResult();
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.API_CACHE_NAME, param.getFunctionId());
        if (!flag) {
            result.add(param.getFunctionId() + ValidateEnum.API_FUNCTION_ID_ERROR.getMessage());
            return result;
        }
        Api api = (Api) CacheUtil.getInstance().getCache(CacheUtil.API_CACHE_NAME, param.getFunctionId());
        if (StringUtils.isNotEmpty(api.getBeginDate()) && DateUtil.parse(api.getBeginDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.API_BEGIN_DATE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(api.getExpireDate()) && !DateUtil.parse(api.getExpireDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.API_EXPIRE_DATE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(param.getFlowControl()) && StringUtils.isNotEmpty(api.getFlowControl()) && Integer.parseInt(param.getFlowControl()) > Integer.parseInt(api.getFlowControl())) {
            result.add(ValidateEnum.API_FLOW_CONTROL_ERROR.getMessage());
        }
        return result;
    }

    @Override
    protected LicenseResult personalizedCheck(ValidateParam param) {
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.PERSONALIZED_ELEMENT_CACHE_NAME, param.getFunctionId());
        if (!flag) {
            return null;
        }
        List<PersonalizedElement> personalizedElementSet = (ArrayList<PersonalizedElement>) CacheUtil.getInstance().getCache(CacheUtil.PERSONALIZED_ELEMENT_CACHE_NAME, param.getFunctionId());
        return this.invoke(personalizedElementSet);
    }
}
