package com.wolf.uc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolf.uc.database.dao.AccountDao;
import com.wolf.uc.entity.UserInfo;

/**
 * <p>Title: 帐号服务     </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2019     </p>
 * <p>Company: ND Co., Ltd.       </p>
 * <p>Create Time: 2019年1月24日           </p>
 * @author Administrator
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao dao;

	@Override
	public UserInfo login(String name, String password) {
		if (password == null) {
			return null;
		}
		UserInfo user = dao.load(name);
		if (user != null) {
			if (password.equals(user.getPassword())) {
				return user;
			}
		}
		return null;
	}

	@Override
	public UserInfo save(String name, String password) {
		// TODO Auto-generated method stub
		UserInfo user = new UserInfo();
		user.setName(name);
		user.setPassword("123456");
		return dao.save(user);
	}

	@Override
	public UserInfo load(String name) {
		return dao.load(name);
	}
}
