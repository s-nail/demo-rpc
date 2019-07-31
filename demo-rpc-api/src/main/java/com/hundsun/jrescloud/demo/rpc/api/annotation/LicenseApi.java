package com.hundsun.jrescloud.demo.rpc.api.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LicenseApi {
    /**
     * 校验名称
     *
     * @return
     */
    String validateName() default "";

    /**
     * 方法功能号
     *
     * @return
     */
    String functionId() default "";

}
