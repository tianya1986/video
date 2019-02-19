package com.wolf.uc.service;

import com.wolf.uc.entity.UserInfo;

public interface AccountService {

	UserInfo login(String name, String password);

	UserInfo save(String name, String password);

	UserInfo load(String name);
}
