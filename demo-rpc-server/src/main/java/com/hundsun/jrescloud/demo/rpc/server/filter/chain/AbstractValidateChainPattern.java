package com.hundsun.jrescloud.demo.rpc.server.filter.chain;

import com.hundsun.jrescloud.demo.rpc.server.common.dto.PersonalizedElement;
import com.hundsun.jrescloud.demo.rpc.server.common.dto.ValidateParam;
import com.hundsun.jrescloud.demo.rpc.server.common.util.CacheUtil;
import com.hundsun.jrescloud.demo.rpc.server.common.util.ErrorCode;
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
            //LicenseResult result = universalCheck(param);
            int result = universalCheck(param);
            if (result != ErrorCode.LICENSE.DEF_NORMAL) {
                logger.error("通用校验结果：" + result);
                throw new BaseRpcException(result);
            }
            result = personalizedCheck(param);
            if (result != ErrorCode.LICENSE.DEF_NORMAL) {
                logger.error("个性化校验结果：" + result);
                throw new BaseRpcException(result);
            }
        }
        if (nextValidate != null) {
            nextValidate.check(level, param);
        }
    }

    public int invoke(List<PersonalizedElement> personalizedElementSet) {
        // LicenseResult result = new LicenseResult();
        int result = ErrorCode.LICENSE.DEF_NORMAL;
        for (PersonalizedElement personalizedElement : personalizedElementSet) {
            try {
                // TODO 待测试
                Class clazz;
                boolean isExist = CacheUtil.getInstance().isExist(CacheUtil.REFLECT_CLASS_CACHE_NAME, personalizedElement.getClassName());
                if (isExist) {
                    clazz = (Class) CacheUtil.getInstance().getCache(CacheUtil.REFLECT_CLASS_CACHE_NAME, personalizedElement.getClassName());
                } else {
                    clazz = Class.forName(personalizedElement.getClassName());
                    CacheUtil.getInstance().addCache(CacheUtil.REFLECT_CLASS_CACHE_NAME, personalizedElement.getClassName(), clazz);
                }
                Method method = clazz.getMethod(personalizedElement.getFunctionName());
                Object object = clazz.newInstance();
                result = (int) method.invoke(object);
                if (result != ErrorCode.LICENSE.DEF_NORMAL) {
                    break;
                }
                /*result = (LicenseResult) method.invoke(object);
                if (result.hasErrors()) {
                    break;
                }*/
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
    abstract protected int universalCheck(ValidateParam param);

    /**
     * 个性化校验
     *
     * @param param
     * @return
     */
    abstract protected int personalizedCheck(ValidateParam param);

}
