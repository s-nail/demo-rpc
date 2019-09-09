package com.hundsun.jrescloud.demo.rpc.server.common.dto;

import com.hundsun.jrescloud.demo.rpc.server.common.util.XStreamUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiayq24996 on 2019-06-04
 */
@XStreamAlias("module")
public class Module {
    /**
     *
     */
    @XStreamAlias("gsv")
    private String gsv;
    @XStreamAlias("module_no")
    private String moduleNo;
    @XStreamAlias("begin_date")
    private String beginDate;
    @XStreamAlias("expire_date")
    private String expireDate;
    @XStreamAlias("max_connections")
    private Integer maxConnections;
    @XStreamAlias("flow_control")
    private Integer flowControl;
    @XStreamAlias("machine_code_set")
    private MachineCodeSet machineCodeSet;
    @XStreamAlias("extend_field")
    private String extendField;
    @XStreamAlias("personalized_element_set")
    private List<PersonalizedElement> personalizedElementSet;
    @XStreamAlias("api_set")
    private List<Api> apiSet;


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

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public Integer getFlowControl() {
        return flowControl;
    }

    public void setFlowControl(Integer flowControl) {
        this.flowControl = flowControl;
    }

    public List<Api> getApiSet() {
        return apiSet;
    }

    public void setApiSet(List<Api> apiSet) {
        this.apiSet = apiSet;
    }

    public List<PersonalizedElement> getPersonalizedElementSet() {
        return personalizedElementSet;
    }

    public void setPersonalizedElementSet(List<PersonalizedElement> personalizedElementSet) {
        this.personalizedElementSet = personalizedElementSet;
    }

    public MachineCodeSet getMachineCodeSet() {
        return machineCodeSet;
    }

    public void setMachineCodeSet(MachineCodeSet machineCodeSet) {
        this.machineCodeSet = machineCodeSet;
    }

    public String getExtendField() {
        return extendField;
    }

    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }

    public static void main(String[] args) {
        Module module = new Module();
        module.setGsv("test-module");
        MachineCodeSet machineCodeSet = new MachineCodeSet();
        List list = new ArrayList();
        list.add("1221");
        list.add("1111");
        machineCodeSet.setMachineCode(list);
        module.setMachineCodeSet(machineCodeSet);
        String test = XStreamUtil.beanToXml(module);
        System.out.println(test);
    }
}
