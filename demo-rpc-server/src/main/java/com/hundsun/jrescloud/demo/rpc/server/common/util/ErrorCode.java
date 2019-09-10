package com.hundsun.jrescloud.demo.rpc.server.common.util;

/**
 * Created by jiayq29996 on 2019-07-03
 */
public class ErrorCode {
    public class LICENSE {
        public static final int DEF_NORMAL = 2900;
        /**
         * 通用校验错误码
         */
        public static final int UNAUTHORIZED = 2901;
        /**
         * 自定义校验错误码
         */
        public static final int CUSTOM_CHECK_FAILED = 2902;

        /**
         * 产品名称不存在
         */
        public static final int PRODUCT_PRODUCT_NAME_IS_NULL_ERROR = 2910;
        /**
         * 许可证类型错误
         */
        public static final int PRODUCT_LICENSE_TYPE_ERROR = 2911;
        /**
         * 许可证过期
         */
        public static final int PRODUCT_LICENSE_EXPIRE_DATE_ERROR = 2912;
        /**
         * 许可证授权时间还未开始
         */
        public static final int PRODUCT_LICENSE_BEGIN_DATE_ERROR = 2913;
        /**
         * 许可证流量异常
         */
        public static final int PRODUCT_LICENSE_FOLLOW_CONTROL_ERROR = 2914;
        /**
         * 模块GSV不存在
         */
        public static final int MODULE_LICENSE_MODULE_NAME_NOT_EXIST_ERROR = 2920;
        /**
         * 模块编号异常
         */
        public static final int MODULE_LICENSE_MODULE_NO_ERROR = 2921;
        /**
         * 模块过期
         */
        public static final int MODULE_LICENSE_MODULE_EXPIRE_DATE_ERROR = 2922;
        /**
         * 模块授权时间还未开始
         */
        public static final int MODULE_LICENSE_MODULE_BEGIN_DATE_ERROR = 2923;
        /**
         * 模块最大连接数异常
         */
        public static final int MODULE_LICENSE_MODULE_MAX_CONNECTIONS_ERROR = 2924;
        /**
         * 模块流量异常
         */
        public static final int MODULE_LICENSE_MODULE_FLOW_CONTROL_ERROR = 2925;
        /**
         * 机器码不匹配
         */
        public static final int MODULE_LICENSE_MACHINE_CODE_ERROR = 2926;
        /**
         * 接口功能号不存在
         */

        public static final int API_FUNCTION_ID_ERROR = 2930;
        /**
         * 接口授权时间还未开始
         */
        public static final int API_BEGIN_DATE_ERROR = 2931;
        /**
         * 接口授权已过期
         */
        public static final int API_EXPIRE_DATE_ERROR = 2932;
        /**
         * 接口名异常
         */
        public static final int API_NAME_ERROR = 2933;
        /**
         * 接口流量异常
         */
        public static final int API_FLOW_CONTROL_ERROR = 2934;
        /**
         * 文件不存在或文档异常
         */
        public static final int FILE_NOT_FOUND_OR_DOCUMENT_ERROR = 2935;
        /**
         * 自定义方法验证失败
         */
        public static final int EXTEND_FIELD_VALIDATE_ERROR = 2936;

        public LICENSE() {
        }
    }
}
