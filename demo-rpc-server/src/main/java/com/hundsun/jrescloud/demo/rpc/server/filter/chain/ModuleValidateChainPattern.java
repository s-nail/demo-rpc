package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import cn.hutool.core.date.DateUtil;
import com.hundsun.jrescloud.common.util.StringUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.PersonalizedElement;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Module;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.ValidateParam;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;
import com.hundsun.jrescloud.demo.rpc.server.common.util.CacheUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.DmcUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.ValidateEnum;
import org.apache.commons.collections.CollectionUtils;

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
        //缓存校验成功的机器码，避免每次请求都生成一次机器码
        if (!CacheUtil.getInstance().isExist(CacheUtil.MACHINE_CODE_CACHE_NAME, param.getGsv())) {
            String machineCode = DmcUtil.getMachineCode();
            if (module.getMachineCodeSet() != null && CollectionUtils.isNotEmpty(module.getMachineCodeSet().getMachineCode()) && !module.getMachineCodeSet().getMachineCode().contains(machineCode)) {
                result.add(ValidateEnum.MODULE_LICENSE_MACHINE_CODE_ERROR.getMessage());
            } else {
                CacheUtil.getInstance().addCache(CacheUtil.MACHINE_CODE_CACHE_NAME, param.getGsv(), machineCode);
            }
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
        if (param.getFlowControl() != null && module.getFlowControl() != null && param.getFlowControl() > module.getFlowControl()) {
            result.add(ValidateEnum.MODULE_LICENSE_MODULE_FLOW_CONTROL_ERROR.getMessage());
        }
        return result;
    }

    @Override
    protected LicenseResult personalizedCheck(ValidateParam param) {
        boolean flag = CacheUtil.getInstance().isExist(CacheUtil.PERSONALIZED_ELEMENT_CACHE_NAME, param.getGsv());
        if (!flag) {
            return null;
        }
        List<PersonalizedElement> personalizedElementSet = (ArrayList<PersonalizedElement>) CacheUtil.getInstance().getCache(CacheUtil.PERSONALIZED_ELEMENT_CACHE_NAME, param.getGsv());
        return this.invoke(personalizedElementSet);
    }
}
