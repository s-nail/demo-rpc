package com.hundsun.jrescloud.demo.rpc.api.annotation;

import java.lang.annotation.*;

/**
 * Created by jiayq24996 on 2019-06-06
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LicenseApi {
    String jarname() default "";
    String version() default "";
    String log() default "";
    String type() default "";
    boolean openApi() default true;
}
