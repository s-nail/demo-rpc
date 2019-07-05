package com.hundsun.licensesdk.lang.annotation;

import java.lang.annotation.*;

/**
 * Created by jiayq24996 on 2019-06-06
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LicenseProduct {
    /**
     * 许可证编号
     *
     * @return
     */
    String licenceNo() default "";

    /**
     * 许可证类型
     *
     * @return
     */
    String licenceType() default "";

}
