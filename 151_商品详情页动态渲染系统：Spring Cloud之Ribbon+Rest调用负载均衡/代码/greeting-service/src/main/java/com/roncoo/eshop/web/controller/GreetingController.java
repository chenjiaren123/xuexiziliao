package com.roncoo.eshop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roncoo.eshop.service.GreetingService;

@RestController
public class GreetingController {

	@Autowired
	private GreetingService greetingService;
	
	@RequestMapping("/greeting")
	public String greeting(@RequestParam String name) {
		return greetingService.greeting(name);
	}
	
}
