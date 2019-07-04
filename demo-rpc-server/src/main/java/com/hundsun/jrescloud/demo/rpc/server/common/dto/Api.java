package com.hundsun.jrescloud.demo.rpc.server.common.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * Created by jiayq24996 on 2019-06-04
 */
@XStreamAlias("api")
public class Api {
    @XStreamAlias("api_name")
    private String apiName;
    @XStreamAlias("function_id")
    private String functionId;
    @XStreamAlias("begin_date")
    private String beginDate;
    @XStreamAlias("expire_date")
    private String expireDate;
    @XStreamAlias("flow_control")
    private String flowControl;
    @XStreamAlias("extend_field_set")
    private List<ExtendField> extendFieldSet;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getFlowControl() {
        return flowControl;
    }

    public void setFlowControl(String flowControl) {
        this.flowControl = flowControl;
    }

    public List<ExtendField> getExtendFieldSet() {
        return extendFieldSet;
    }

    public void setExtendFieldSet(List<ExtendField> extendFieldSet) {
        this.extendFieldSet = extendFieldSet;
    }

	@Override
	public String toString() {
		return "[apiName=" + apiName + ", functionId=" + functionId + ", beginDate=" + beginDate + ", expireDate="
				+ expireDate + ", flowControl=" + flowControl + ", extendFieldSet=" + extendFieldSet + "]";
	}
    
    
}
