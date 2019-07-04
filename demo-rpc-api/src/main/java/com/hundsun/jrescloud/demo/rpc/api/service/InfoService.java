package com.hundsun.jrescloud.demo.rpc.api.service;

import com.hundsun.jrescloud.demo.rpc.api.pojo.UserAccount;
import com.hundsun.jrescloud.rpc.annotation.CloudFunction;
import com.hundsun.jrescloud.rpc.annotation.CloudService;

@CloudService
public interface InfoService {
	@CloudFunction("431100")
	String info(UserAccount userAccount);
}
