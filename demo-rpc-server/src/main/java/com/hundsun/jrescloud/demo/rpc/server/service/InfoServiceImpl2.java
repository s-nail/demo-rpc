package com.hundsun.jrescloud.demo.rpc.server.service;

import com.hundsun.jrescloud.demo.rpc.api.pojo.UserAccount;
import com.hundsun.jrescloud.demo.rpc.api.service.InfoService;
import com.hundsun.jrescloud.rpc.annotation.CloudComponent;

@CloudComponent("info2")
public class InfoServiceImpl2 implements InfoService {

	@Override
	public String info(UserAccount userAccount) {
		return "info2";
	}
}
