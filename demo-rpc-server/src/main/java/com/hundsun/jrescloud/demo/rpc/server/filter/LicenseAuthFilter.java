package com.hundsun.jrescloud.demo.rpc.server.filter;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.hundsun.jrescloud.common.code.ErrorCode;
import com.hundsun.jrescloud.common.exception.BaseBizException;
import com.hundsun.jrescloud.common.util.ConfigUtils;
import com.hundsun.jrescloud.common.util.StringUtils;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Api;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Module;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.Product;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;
import com.hundsun.jrescloud.demo.rpc.server.common.util.CacheUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.HttpClientUpgradesUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.ValidateUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.XStreamUtil;
import com.hundsun.jrescloud.rpc.def.util.RpcUtils;
import com.hundsun.jrescloud.rpc.exception.BaseRpcException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiayq24996 on 2019-06-17
 */
@Activate(group = {Constants.PROVIDER}, order = Integer.MAX_VALUE)
public class LicenseAuthFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(LicenseAuthFilter.class);

    private static String LICENSE_NO = ConfigUtils.get("hs.license.licenceNo", String.class);
    private static String PERMIT_CENTER_SERVER_IP = ConfigUtils.get("hs.permit-center.server.ip", String.class);
    private static String PERMIT_CENTER_SERVER_PORT = ConfigUtils.get("hs.permit-center.server.port", String.class);

    static {
        //1.HTTP请求许可中心获取对应系统的许可文件
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("LICENSE_NO", LICENSE_NO));
        String licenceInfo = null;
        try {
            licenceInfo = HttpClientUpgradesUtil.executePOST("http://" + PERMIT_CENTER_SERVER_IP + ":" + PERMIT_CENTER_SERVER_PORT + "/permit/toSDK", params);
        } catch (Exception e) {
            logger.error("HTTP请求许可中心失败，请检查配置文件，确认许可证编号和许可中心服务IP、Port是否正确", e);
        }
        if (StringUtils.isEmpty(licenceInfo)) {
            logger.error("许可中心返回许可文件为空");
        } else {
            //2.解析许可文件，存放系统缓存中
            Product product = null;
            try {
                product = XStreamUtil.xmlToBean(licenceInfo, new Class[]{Product.class, Module.class, Api.class});
            } catch (Exception e) {
                logger.error("解析许可文件失败", e);
            }
            if (product != null) {
                CacheUtil.getInstance().addCache(CacheUtil.PRODUCT_CACHE_NAME, product.getLicenceNo(), product);
                if (CollectionUtils.isNotEmpty(product.getModules())) {
                    for (Module module : product.getModules()) {
                        CacheUtil.getInstance().addCache(CacheUtil.MODULE_CACHE_NAME, module.getModuleNo(), module);
                        if (CollectionUtils.isNotEmpty(module.getApiSet())) {
                            for (Api api : module.getApiSet()) {
                                CacheUtil.getInstance().addCache(CacheUtil.API_CACHE_NAME, api.getFunctionId(), api);
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //String functionId = RpcUtils.getFunctionName(invoker, invocation);
        //LicenseResult result = ValidateUtil.apiCheck(functionId);
        /*LicenseResult commonResult = ValidateUtil.commonCheck(LICENSE_NO);

        if (commonResult.hasErrors()) {
            //System.out.println(commonResult.getAllErrors().toString());
            throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.UNAUTHORIZED, commonResult.getAllErrors().toString());
        }*/
        if (true) {
            throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.UNAUTHORIZED, "未授权");
        }
        return invoker.invoke(invocation);
    }
}
