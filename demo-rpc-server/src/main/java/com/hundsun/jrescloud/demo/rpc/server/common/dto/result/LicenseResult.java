package com.hundsun.jrescloud.demo.rpc.server.common.dto.result;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jiayq24996 on 2019-06-13
 */
public class LicenseResult {

    private final List<String> errors = new LinkedList<String>();

    public void add(String val){
        errors.add(val);
    }

    /**
     * 是否存在校验失败信息
     * @return
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * 失败信息条数
     * @return
     */
    public int getErrorCount() {
        return errors.size();
    }

    /**
     * 获取所有失败信息条数
     * @return
     */
    public List<String> getAllErrors() {
        return errors;
    }

}
