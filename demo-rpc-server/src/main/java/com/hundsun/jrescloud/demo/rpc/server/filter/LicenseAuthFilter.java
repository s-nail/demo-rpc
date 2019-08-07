package com.hundsun.jrescloud.demo.rpc.server.filter;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.hundsun.jrescloud.common.util.ConfigUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.ValidateParam;
import com.hundsun.jrescloud.demo.rpc.server.common.util.LicenseContentLoader;
import com.hundsun.jrescloud.demo.rpc.server.filter.chain.AbstractValidateChainPattern;
import com.hundsun.jrescloud.demo.rpc.server.filter.chain.ApiValidateChainPattern;
import com.hundsun.jrescloud.demo.rpc.server.filter.chain.ModuleValidateChainPattern;
import com.hundsun.jrescloud.demo.rpc.server.filter.chain.ProductValidateChainPattern;
import com.hundsun.jrescloud.rpc.def.util.RpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiayq24996 on 2019-06-17
 */
@Activate(group = {Constants.PROVIDER}, order = Integer.MAX_VALUE)
public class LicenseAuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LicenseAuthFilter.class);

    private static final String LICENSE_NO = ConfigUtils.get("hs.license.licenceNo", String.class);

    static {
        //1.HTTP请求许可中心获取对应系统的许可文件
        String licenceContent = LicenseContentLoader.getInstance().callPermitCenterApi();
        //2.存入本地缓存
        LicenseContentLoader.getInstance().clear();
        LicenseContentLoader.getInstance().init(licenceContent);
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String applicationName = invoker.getUrl().getParameter("application");
        logger.info("======================= 模块名称：" + applicationName);
        String functionId = RpcUtils.getFunctionName(invoker, invocation);
        ValidateParam param = new ValidateParam();
        param.setLicenceNo(LICENSE_NO);
        param.setModuleName(applicationName);
        param.setFunctionId(functionId);
        AbstractValidateChainPattern loggerChain = getChainOfValidate();
        loggerChain.check(AbstractValidateChainPattern.PRODUCT, param);
        return invoker.invoke(invocation);
    }

    /**
     * 创建不同类型的记录器
     *
     * @return
     */
    private AbstractValidateChainPattern getChainOfValidate() {
        AbstractValidateChainPattern productValidate = new ProductValidateChainPattern(AbstractValidateChainPattern.PRODUCT);
        AbstractValidateChainPattern moduleValidate = new ModuleValidateChainPattern(AbstractValidateChainPattern.MODULE);
        AbstractValidateChainPattern apiValidate = new ApiValidateChainPattern(AbstractValidateChainPattern.API);
        productValidate.setNextValidate(moduleValidate);
        moduleValidate.setNextValidate(apiValidate);
        return productValidate;
    }
}
