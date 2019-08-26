package com.hundsun.jrescloud.demo.rpc.server.common.dto;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by jiayq24996 on 2019-08-26
 */
public class MachineCodeSet {
    @XStreamImplicit(itemFieldName="machineCode")
    private List<String> machineCode;

    public List<String> getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(List<String> machineCode) {
        this.machineCode = machineCode;
    }
}
