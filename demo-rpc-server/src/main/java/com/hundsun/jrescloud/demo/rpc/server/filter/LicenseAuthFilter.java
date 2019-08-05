package com.hundsun.jrescloud.demo.rpc.server.filter;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.hundsun.jrescloud.common.util.ConfigUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;
import com.hundsun.jrescloud.demo.rpc.server.common.util.HttpClientUpgradesUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.LicenseContentLoader;
import com.hundsun.jrescloud.demo.rpc.server.common.util.ValidateUtil;
import com.hundsun.jrescloud.rpc.def.util.RpcUtils;
import com.hundsun.jrescloud.rpc.exception.BaseRpcException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayq24996 on 2019-06-17
 */
@Activate(group = {Constants.PROVIDER}, order = Integer.MAX_VALUE)
public class LicenseAuthFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(LicenseAuthFilter.class);

    private static final String LICENSE_NO = ConfigUtils.get("hs.license.licenceNo", String.class);
    private static final String PERMIT_CENTER_SERVER_IP = ConfigUtils.get("hs.permit-center.server.ip", String.class);
    private static final String PERMIT_CENTER_SERVER_PORT = ConfigUtils.get("hs.permit-center.server.port", String.class);
    //private static final String CUSTOM_CHECK_CLASSNAME = ConfigUtils.get("hs.license.custom.check.className", String.class);

    private static final int REQUEST_FAILED_TIMES = 3;

    static {
        //1.HTTP请求许可中心获取对应系统的许可文件
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("LICENSE_NO", LICENSE_NO));
        String licenceInfo = null;
        int i = 0;
        while (i < REQUEST_FAILED_TIMES) {
            try {
                licenceInfo = HttpClientUpgradesUtil.executePOST("http://" + PERMIT_CENTER_SERVER_IP + ":" + PERMIT_CENTER_SERVER_PORT + "/permit/toSDK", params);
                break;
            } catch (Exception e) {
                logger.error("第" + (++i) + "次HTTP请求许可中心失败，请检查配置文件，确认许可证编号和许可中心服务IP、Port是否正确! 异常信息：" + e.getMessage());
            }
        }
        LicenseContentLoader.getInstance().clear();
        LicenseContentLoader.getInstance().init(licenceInfo);
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //通用校验---产品
        LicenseResult result = ValidateUtil.productCheck(LICENSE_NO, null, null);
        if (result.hasErrors()) {
            logger.error("产品校验结果：" + result.getAllErrors().toString());
            throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.UNAUTHORIZED, result.getAllErrors().toString());
        }
        result = ValidateUtil.productCustomCheck(LICENSE_NO);
        if (result.hasErrors()) {
            logger.error("产品个性化校验结果：" + result.getAllErrors().toString());
            throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.CUSTOM_CHECK_FAILED, result.getAllErrors().toString());
        }
        //通用校验---模块
        String applicationName = invoker.getUrl().getParameter("application");
        logger.info("======================= 模块名称：" + applicationName);
        result = ValidateUtil.moduleCheck(applicationName, null, null, null);
        if (result.hasErrors()) {
            logger.error("模块校验结果：" + result.getAllErrors().toString());
            throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.UNAUTHORIZED, result.getAllErrors().toString());
        }
        result = ValidateUtil.moduleCustomCheck(applicationName);
        if (result.hasErrors()) {
            logger.error("模块个性化校验结果：" + result.getAllErrors().toString());
            throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.CUSTOM_CHECK_FAILED, result.getAllErrors().toString());
        }
        //通用校验---接口
        String functionId = RpcUtils.getFunctionName(invoker, invocation);
        result = ValidateUtil.apiCheck(functionId, null);
        if (result.hasErrors()) {
            logger.error("接口校验结果：" + result.getAllErrors().toString());
            throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.UNAUTHORIZED, result.getAllErrors().toString());
        }
        result = ValidateUtil.apiCustomCheck(functionId);
        if (result.hasErrors()) {
            logger.error("接口个性化校验结果：" + result.getAllErrors().toString());
            throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.CUSTOM_CHECK_FAILED, result.getAllErrors().toString());
        }
        return invoker.invoke(invocation);
    }
}
