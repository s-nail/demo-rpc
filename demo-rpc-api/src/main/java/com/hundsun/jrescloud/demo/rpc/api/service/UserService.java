package com.hundsun.jrescloud.demo.rpc.api.service;

import com.hundsun.jrescloud.common.t2.dataset.IDatasets;
import com.hundsun.jrescloud.demo.rpc.api.annotation.LicenseApi;
import com.hundsun.jrescloud.demo.rpc.api.pojo.Page;
import com.hundsun.jrescloud.demo.rpc.api.pojo.UserAccount;
import com.hundsun.jrescloud.mq.exception.BaseMqException;
import com.hundsun.jrescloud.mq.message.Message;
import com.hundsun.jrescloud.rpc.annotation.CloudFunction;
import com.hundsun.jrescloud.rpc.annotation.CloudService;
import com.hundsun.jrescloud.rpc.result.RpcResultDTO;

@CloudService
public interface UserService {
	@CloudFunction("331100")
	@LicenseApi
	String login(UserAccount userAccount);
	
	@CloudFunction("331101")
	RpcResultDTO<Page<UserAccount>> list(UserAccount userAccount);

	@CloudFunction("331102")
	String teste();

	@CloudFunction("331103")
	IDatasets testd(IDatasets data);

	void publish(String instanceId, Message message) throws BaseMqException;
}
