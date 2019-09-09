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
    private Integer flowControl;
    @XStreamAlias("extend_field")
    private String extendField;
    @XStreamAlias("personalized_element_set")
    private List<PersonalizedElement> personalizedElementSet;

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

    public Integer getFlowControl() {
        return flowControl;
    }

    public void setFlowControl(Integer flowControl) {
        this.flowControl = flowControl;
    }

    public String getExtendField() {
        return extendField;
    }

    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }

    public List<PersonalizedElement> getPersonalizedElementSet() {
        return personalizedElementSet;
    }

    public void setPersonalizedElementSet(List<PersonalizedElement> personalizedElementSet) {
        this.personalizedElementSet = personalizedElementSet;
    }
}
