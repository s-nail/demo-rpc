package com.hundsun.jrescloud.demo.rpc.api.annotation;

import java.lang.annotation.*;

/**
 * Created by jiayq24996 on 2019-06-06
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LicenseProduct {
    /**
     * 校验名称
     *
     * @return
     */
    String validateName() default "";

    /**
     * 许可证编号
     *
     * @return
     */
    String licenceNo() default "";

}
