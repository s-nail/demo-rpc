package com.hundsun.jrescloud.demo.rpc.server.common.dto;

/**
 * Created by jiayq24996 on 2019-08-06
 */
public class ValidateParam {
    private String productName;
    private String licenceType;
    private Integer flowControl;

    private String gsv;
    private String moduleNo;
    private Integer maxConnections;

    private String functionId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public Integer getFlowControl() {
        return flowControl;
    }

    public void setFlowControl(Integer flowControl) {
        this.flowControl = flowControl;
    }

    public String getGsv() {
        return gsv;
    }

    public void setGsv(String gsv) {
        this.gsv = gsv;
    }

    public String getModuleNo() {
        return moduleNo;
    }

    public void setModuleNo(String moduleNo) {
        this.moduleNo = moduleNo;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
}
