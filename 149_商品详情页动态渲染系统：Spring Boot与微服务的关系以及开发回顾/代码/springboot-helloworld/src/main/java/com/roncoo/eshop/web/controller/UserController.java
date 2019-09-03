package com.roncoo.eshop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户Controller控制器
 * @author Administrator
 *
 */
@Controller
public class UserController {

	@RequestMapping("/sayHello")
	@ResponseBody
	public String sayHello(String name) {
		return "hello, " + name;
	}

}
