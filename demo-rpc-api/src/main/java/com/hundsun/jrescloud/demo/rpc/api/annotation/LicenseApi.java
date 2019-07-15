package com.hundsun.jrescloud.demo.rpc.api.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LicenseApi {
    String validateField() default "";

    String functionName() default "";

    String libName() default "";

}
