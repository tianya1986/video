package com.wolf.uc.database.dao;

import com.wolf.uc.entity.UserInfo;

public interface AccountDao {

	public UserInfo load(String id);
	
	public UserInfo save(UserInfo user);
}
