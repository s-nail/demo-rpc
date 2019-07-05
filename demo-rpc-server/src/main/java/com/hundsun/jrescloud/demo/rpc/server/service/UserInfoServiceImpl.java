package com.hundsun.jrescloud.demo.rpc.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hundsun.jrescloud.common.exception.BaseBizException;
import com.hundsun.jrescloud.common.t2.dataset.IDatasets;
import com.hundsun.jrescloud.demo.rpc.api.pojo.Page;
import com.hundsun.jrescloud.demo.rpc.api.pojo.UserAccount;
import com.hundsun.jrescloud.demo.rpc.api.service.UserService;
import com.hundsun.jrescloud.rpc.annotation.CloudComponent;
import com.hundsun.jrescloud.rpc.api.IRpcContext;
import com.hundsun.jrescloud.rpc.result.RpcResultDTO;

@CloudComponent
public class UserInfoServiceImpl implements UserService {

	@Autowired
	private IRpcContext rpcContext;

	@Override
	public String login(UserAccount userAccount) {
		return "登录成功！";
	}

	@Override
	public RpcResultDTO<Page<UserAccount>> list(UserAccount userAccount) {
		Page<UserAccount> page = new Page<UserAccount>();
		List<UserAccount> list = new ArrayList<UserAccount>();
		list.add(userAccount);
		page.setList(list);
		RpcResultDTO<Page<UserAccount>> dto = new RpcResultDTO<Page<UserAccount>>(page);
		return dto;
	}
	
	@Override
	public String teste() {
		System.out.println(rpcContext.getProcessID());
		System.out.println(rpcContext.getLocale());
		throw new BaseBizException("9111", "test1", "test2");
	}

	@Override
	public IDatasets testd(IDatasets data) {
		return data;
	}

	public static void main(String[] args) {
		try {
			Class<?> classUserService = Class.forName("com.hundsun.jrescloud.demo.rpc.server.service.UserInfoServiceImpl");
			UserInfoServiceImpl userService = (UserInfoServiceImpl) classUserService.newInstance();
			System.out.println(userService.login(null));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Class<?> clazz = UserInfoServiceImpl.class;



	}

}
