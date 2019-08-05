package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;
import com.hundsun.jrescloud.rpc.exception.BaseRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public abstract class AbstractValidate {
    private static Logger logger = LoggerFactory.getLogger(AbstractValidate.class);

    public static int API = 1;
    public static int MODULE = 2;
    public static int PRODUCT = 3;

    protected int level;
    protected AbstractValidate nextValidate;

    public void setNextValidate(AbstractValidate nextValidate) {
        this.nextValidate = nextValidate;
    }


    public void check(int level, String message){
         if(this.level <= level){
            LicenseResult result=universalCheck(message);
            if (result.hasErrors()) {
                logger.error("通用校验结果：" + result.getAllErrors().toString());
                throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.UNAUTHORIZED, result.getAllErrors().toString());
            }
            result = personalizedCheck(message);
            if (result.hasErrors()) {
                logger.error("个性化校验结果：" + result.getAllErrors().toString());
                throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.CUSTOM_CHECK_FAILED, result.getAllErrors().toString());
            }
        }
        if(nextValidate !=null){
            nextValidate.check(level, message);
        }
    }
    abstract protected LicenseResult universalCheck(String message);
    abstract protected LicenseResult personalizedCheck(String message);

}
