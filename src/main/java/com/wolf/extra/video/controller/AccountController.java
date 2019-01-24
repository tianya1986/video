package com.wolf.extra.video.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class AccountController {

    /**
     * 登录页面	
     * <p>Description:              </p>
     * <p>Create Time: 2019年1月24日   </p>
     * <p>Create author: Administrator   </p>
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String goLogin() {

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin() {

        return "login";
    }

}
