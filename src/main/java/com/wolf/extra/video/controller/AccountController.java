package com.wolf.extra.video.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wolf.uc.entity.UserInfo;
import com.wolf.uc.service.AccountService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;

	/**
	 * 登录页面	
	 * <p>Description:              </p>
	 * <p>Create Time: 2019年1月24日   </p>
	 * <p>Create author: Administrator   </p>
	 */
	@RequestMapping(value = "/")
	public String goLogin(HttpServletRequest request) {
		return "index.html";
	}

	/**
	 * 登录 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(@RequestParam Map<String, Object> params,
			HttpServletRequest request) {
		Object name = params.get("username");
		Object password = params.get("password");
		if (name == null && password == null) {
			return "error";
		}
		UserInfo user = accountService.login(name.toString(),
				password.toString());
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			return "manager/video-list";
		}
		return "error";
	}

}
