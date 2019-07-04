package com.hundsun.jrescloud.demo.rpc.server.common.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by jiayq24996 on 2019-06-04
 */

@XStreamAlias("extend_field")
public class ExtendField {
    @XStreamAlias("validate_field")
    private String validateField;
    @XStreamAlias("function_name")
    private String functionName;
    @XStreamAlias("lib_name")
    private String libName;

    public String getValidateField() {
        return validateField;
    }

    public void setValidateField(String validateField) {
        this.validateField = validateField;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }
}
