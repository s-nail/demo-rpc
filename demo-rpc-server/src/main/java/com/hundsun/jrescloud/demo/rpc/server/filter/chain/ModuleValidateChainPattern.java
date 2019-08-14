package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import cn.hutool.core.date.DateUtil;
import com.hundsun.jrescloud.common.util.StringUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.ExtendField;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Module;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.ValidateParam;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;
import com.hundsun.jrescloud.demo.rpc.server.common.util.CacheUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.ValidateEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public class ModuleValidateChainPattern extends AbstractValidateChainPattern {
    public ModuleValidateChainPattern(int level) {
        this.level = level;
    }

    @Override
    protected LicenseResult universalCheck(ValidateParam param) {
        LicenseResult result = new LicenseResult();
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.MODULE_CACHE_NAME, param.getGsv());
        if (!flag) {
            result.add(param.getGsv() + ValidateEnum.MODULE_LICENSE_MODULE_NAME_NOT_EXIST_ERROR.getMessage());
            return result;
        }
        Module module = (Module) CacheUtil.getInstance().getCache(CacheUtil.MODULE_CACHE_NAME, param.getGsv());
        if (!CacheUtil.getInstance().getMachineCode().equals(module.getMachineCode())) {
            result.add(ValidateEnum.MODULE_LICENSE_MACHINE_CODE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(param.getModuleNo()) && !param.getModuleNo().equals(module.getModuleNo())) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_NO_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(module.getBeginDate()) && DateUtil.parse(module.getBeginDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_BEGIN_DATE_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(module.getExpireDate()) && !DateUtil.parse(module.getExpireDate()).after(DateUtil.parse(DateUtil.now()))) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_EXPIRE_DATE_ERROR.getMessage());
        }
        if (param.getMaxConnections() != null && module.getMaxConnections() != null && param.getMaxConnections() > module.getMaxConnections()) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_MAX_CONNECTIONS_ERROR.getMessage());
        }
        if (StringUtils.isNotEmpty(param.getFlowControl()) && StringUtils.isNotEmpty(module.getFlowControl()) && Integer.parseInt(param.getFlowControl()) > Integer.parseInt(module.getFlowControl())) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_FLOW_CONTROL_ERROR.getMessage());
        }
        return result;
    }

    @Override
    protected LicenseResult personalizedCheck(ValidateParam param) {
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.CUSTOM_ELEMENT_CACHE_NAME, param.getGsv());
        if (!flag) {
            return null;
        }
        List<ExtendField> extendFieldSet = (ArrayList<ExtendField>) CacheUtil.getInstance().getCache(CacheUtil.CUSTOM_ELEMENT_CACHE_NAME, param.getGsv());
        return this.invoke(extendFieldSet);
    }
}
