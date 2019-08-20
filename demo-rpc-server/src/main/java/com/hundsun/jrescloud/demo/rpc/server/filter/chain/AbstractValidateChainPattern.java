package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import com.hundsun.jrescloud.demo.rpc.server.common.dto.PersonalizedElement;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.ValidateParam;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.result.LicenseResult;
import com.hundsun.jrescloud.rpc.exception.BaseRpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by jiayq24996 on 2019-08-05
 */
public abstract class AbstractValidateChainPattern {
    private static Logger logger = LoggerFactory.getLogger(AbstractValidateChainPattern.class);

    public static int API = 1;
    public static int MODULE = 2;
    public static int PRODUCT = 3;

    protected int level;
    protected AbstractValidateChainPattern nextValidate;

    public void setNextValidate(AbstractValidateChainPattern nextValidate) {
        this.nextValidate = nextValidate;
    }


    public void check(int level, ValidateParam param) {
        if (this.level <= level) {
            LicenseResult result = universalCheck(param);
            if (result != null && result.hasErrors()) {
                logger.error("通用校验结果：" + result.getAllErrors().toString());
                throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.UNAUTHORIZED, result.getAllErrors().toString());
            }
            result = personalizedCheck(param);
            if (result != null && result.hasErrors()) {
                logger.error("个性化校验结果：" + result.getAllErrors().toString());
                throw new BaseRpcException(com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode.LICENSE.CUSTOM_CHECK_FAILED, result.getAllErrors().toString());
            }
        }
        if (nextValidate != null) {
            nextValidate.check(level, param);
        }
    }

    public LicenseResult invoke(List<PersonalizedElement> personalizedElementSet) {
        LicenseResult result = new LicenseResult();
        for (PersonalizedElement personalizedElement : personalizedElementSet) {
            try {
                // TODO 将反射生成的对象缓存起来，留作下次比对直接使用
                Class clazz = Class.forName(personalizedElement.getClassName());
                Method method = clazz.getMethod(personalizedElement.getFunctionName());
                Object object = clazz.newInstance();
                result = (LicenseResult) method.invoke(object);
                if (result.hasErrors()) {
                    break;
                }
                /*for (Method method : object.getDeclaredMethods()) {
                    LicenseProduct licenseProduct = method.getAnnotation(LicenseProduct.class);
                    System.out.println(licenseProduct);
                }*/
            } catch (ClassNotFoundException e) {
                logger.error("找不到个性化校验类路径：" + e);
            } catch (NoSuchMethodException e) {
                logger.error("找不到个性化校验方法名称：" + e);
            } catch (IllegalAccessException e) {
                logger.error("个性化校验异常：" + e);
            } catch (InstantiationException e) {
                logger.error("个性化校验异常：" + e);
            } catch (InvocationTargetException e) {
                logger.error("个性化校验异常：" + e);
            }
        }
        return result;
    }

    /**
     * 通用校验
     *
     * @param param
     * @return
     */
    abstract protected LicenseResult universalCheck(ValidateParam param);

    /**
     * 个性化校验
     *
     * @param param
     * @return
     */
    abstract protected LicenseResult personalizedCheck(ValidateParam param);

}
