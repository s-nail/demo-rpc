package com.hundsun.jrescloud.demo.rpc.client.filter;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.protocol.t3.T3RpcResult;
import com.hundsun.jrescloud.common.exception.BaseCommonException;
import com.hundsun.jrescloud.common.t2.util.ExceptionUtil;
import com.hundsun.jrescloud.common.util.ExceptionUtils;
import com.hundsun.jrescloud.rpc.def.util.RpcUtils;
import com.hundsun.jrescloud.rpc.exception.BaseRpcException;
import com.hundsun.jrescloud.rpc.result.RpcResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiayq24996 on 2019-06-17
 */
@Activate(group = {Constants.CONSUMER}, order = Integer.MAX_VALUE)
public class LicenseAuthResultHandleFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(LicenseAuthResultHandleFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String functionId = RpcUtils.getFunctionName(invoker, invocation);
        System.out.println(functionId);
        Result res = invoker.invoke(invocation);
        T3RpcResult t3RpcResult = new T3RpcResult(null, null);
        /*RpcResultDTO rpcResultDTO = new RpcResultDTO();
        rpcResultDTO.setErrorCode("-1");
        rpcResultDTO.setErrorMessage("TESTTESTTEST");*/
        invocation.getMethod().getReturnType();//Service服务方法返回值类型
        t3RpcResult.setValue("TESTTESTTEST");
        if (res.hasException()) {
            String errorCode = ExceptionUtils.getErrorCode(res.getException(), -1);
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println(errorCode);
        }
        return t3RpcResult;
    }
}
