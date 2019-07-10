package com.hundsun.jrescloud.demo.rpc.server.common.util;

/**
 * Created by jiayq29996 on 2019-07-03
 */
public class ErrorCode {
    public class LICENSE {
        public static final int DEF_EXCEPTION = 2900;
        /**
         * 通用校验错误码
         */
        public static final int UNAUTHORIZED = 2901;
        /**
         * 自定义校验错误码
         */
        public static final int CUSTOM_CHECK_FAILED = 2902;
        public LICENSE() {
        }
    }
}
