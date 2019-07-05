package com.hundsun.jrescloud.demo.rpc.server.common.util;
/**
* @author xiacm
* 日期: 2019年6月27日 下午1:59:25
*/
public enum ValidateEnum {

	UNKNOWN_ERROR("未知错误"),
	PRODUCT_LICENSE_NO_IS_NULL_ERROR("许可证编号不存在"),
	PRODUCT_LICENSE_TYPE_ERROR("许可证类型错误"),
    PRODUCT_LICENSE_EXPRIE_DATE_ERROR("许可证过期"),
	PRODUCT_LICENSE_BEGIN_DATE_ERROR("许可证开始时间异常"),
	PRODUCT_LICENSE_FOLLOW_CONTTROL_ERROR("许可证流量异常"),
	
	MODULE_LICENSE_MODULE_NAME_ERROR("模块名异常"),
	MODULE_LICENSE_MODULE_NO_ERROR("模块编号异常"),
	MODULE_LICENSE_MODULE_EXPIRE_DATE_ERROR("模块过期"),
	MODULE_LICENSE_MODULE_BEGIN_DATE_ERROR("模块开始时间异常"),
	MODULE_LICENSE_MODULE_MAX_CONNECTIONS_ERROR("模块最大连接数异常"),
    MODULE_LICENSE_MODULE_FLOW_CONTROL_ERROR("模块流量异常"),
	
    API_FUNCTION_ID_ERROR("接口功能号不存在"),
    API_BEGIN_DATE_ERROR("接口功开始时间异常"),
    API_EXPIRE_DATE_ERROR("接口授权已过期"),
    API_NAME_ERROR("接口名异常"),
    API_FLOW_CONTROL_ERROR("接口流量异常"),
	
	FILE_NOT_FOUND_OR_DOCUMENT_ERROR("文件不存在或文档异常"),
	EXTEND_FIELD_VALIDATE_ERROR("自定义方法验证失败");
	
	private String message;
	
    ValidateEnum( String message) {
	this.message = message;
    }
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
