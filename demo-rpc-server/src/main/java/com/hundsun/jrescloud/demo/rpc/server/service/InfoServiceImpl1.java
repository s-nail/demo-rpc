package com.hundsun.jrescloud.demo.rpc.server.service;

import com.hundsun.jrescloud.demo.rpc.api.pojo.UserAccount;
import com.hundsun.jrescloud.demo.rpc.api.service.InfoService;
import com.hundsun.jrescloud.rpc.annotation.CloudComponent;

@CloudComponent("info1")
public class InfoServiceImpl1 implements InfoService {

	@Override
	public String info(UserAccount userAccount) {
		return "info1";
	}
}
