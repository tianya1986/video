package com.wolf.uc.entity;

import java.io.Serializable;

/**
 * <p>Title: 用户信息       </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2019     </p>
 * <p>Company: ND Co., Ltd.       </p>
 * <p>Create Time: 2019年1月24日           </p>
 * @author Administrator
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 9028994540627116243L;

	/**
	 * 用户名
	 */
	private String name;

	/**
	 * 用户年龄
	 */
	private int age;

	/**
	 * 密码
	 */
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
