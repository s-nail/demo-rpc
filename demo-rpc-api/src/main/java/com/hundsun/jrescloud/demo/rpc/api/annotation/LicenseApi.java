package com.hundsun.jrescloud.demo.rpc.api.annotation;

import java.lang.annotation.*;

/**
 * Created by jiayq24996 on 2019-06-06
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LicenseApi {
    /**
     * 接口名称
     *
     * @return
     */
    String apiName() default "";

    /**
     * 接口功能号
     *
     * @return
     */
    String functionId() default "";


    /*String beginDate() default "";

    String expireDate() default "";

    String flowControl() default "";*/
}
