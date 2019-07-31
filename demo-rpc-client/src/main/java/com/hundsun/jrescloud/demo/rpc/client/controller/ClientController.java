package com.hundsun.jrescloud.demo.rpc.client.controller;

import com.alibaba.dubbo.rpc.RpcContext;
import com.hundsun.jrescloud.common.exception.BaseCommonException;
import com.hundsun.jrescloud.common.t2.dataset.*;
import com.hundsun.jrescloud.demo.rpc.api.pojo.Page;
import com.hundsun.jrescloud.demo.rpc.api.pojo.UserAccount;
import com.hundsun.jrescloud.demo.rpc.api.service.InfoService;
import com.hundsun.jrescloud.demo.rpc.api.service.UserService;
import com.hundsun.jrescloud.rpc.annotation.CloudReference;
import com.hundsun.jrescloud.rpc.constant.RpcConstants;
import com.hundsun.jrescloud.rpc.result.RpcResultDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    @CloudReference
    private UserService userService;

    @CloudReference(uniqueId = "info1")
    private InfoService infoService;

    @CloudReference(uniqueId = "info2")
    private InfoService infoService2;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        try {
            UserAccount userAccount = new UserAccount();
            userAccount.setId("id");
            userAccount.setName("name");
            return "调用生产者：" + userService.login(userAccount);
        } catch (BaseCommonException e) {
            System.out.println("=====================================" + e.getMessage());
            if ("2901".equals(e.getErrorCode())||"2902".equals(e.getErrorCode())){
                return e.getErrorMessage();
            }
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "调用生产者：失败！！！";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        try {
            UserAccount userAccount = new UserAccount();
            userAccount.setId("id");
            userAccount.setName("name");
            RpcResultDTO<Page<UserAccount>> rpcResultDTO = userService.list(userAccount);
            return "调用生产者成功：" + rpcResultDTO.getErrorMessage() + rpcResultDTO.getErrorCode();
        } catch (BaseCommonException e) {
            System.out.println("=====================================" + e.getMessage());
            if ("2901".equals(e.getErrorCode())||"2902".equals(e.getErrorCode())){
                return e.getErrorMessage();
            }
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "调用生产者：失败！！！";
    }

    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public String teste() {
        RpcContext.getContext().set(RpcConstants.BODYINFO_KEY_CONTENT, "test");
        RpcContext.getContext().set(RpcConstants.BODYINFO_KEY_LOCALE, "zh_TW");
        return "调用生产者：" + userService.teste();
    }

    @RequestMapping(value = "/testd", method = RequestMethod.GET)
    public String testd() {
        IDataset subDataset = DatasetService.getDefaultInstance().getDataset();
        subDataset.addColumn("subid", DatasetColumnType.DS_INT);
        subDataset.addColumn("subname", DatasetColumnType.DS_STRING);
        subDataset.appendRow();
        subDataset.updateValue("subid", 1);
        subDataset.updateValue("subname", "name");

        IDatasets datasets = new CommonDatasets();
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("id", DatasetColumnType.DS_INT);
        dataset.addColumn("name", DatasetColumnType.DS_STRING);
        dataset.addColumn("sub", DatasetColumnType.DS_SUB_DATASET);
        dataset.appendRow();
        dataset.updateValue("id", 1);
        dataset.updateValue("name", "name");
        dataset.updateSubDataset("sub", subDataset);
        datasets.putDataset(dataset);

        return "调用生产者：" + userService.testd(datasets);
    }

    @RequestMapping(value = "/info1", method = RequestMethod.GET)
    public String info1() {
        UserAccount userAccount = new UserAccount();
        userAccount.setId("id");
        userAccount.setName("name");
        return "调用生产者：" + infoService.info(userAccount);
    }

    @RequestMapping(value = "/info2", method = RequestMethod.GET)
    public String info2() {
        UserAccount userAccount = new UserAccount();
        userAccount.setId("id");
        userAccount.setName("name");
        return "调用生产者：" + infoService2.info(userAccount);
    }
}
