package com.kavin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class configController {

	@Autowired
	private Environment Env;
	
	/**
	 * 获取环境变量中的属性值
	 * @return
	 */
	@RequestMapping(value = "getValue")
	public String getConfigValue() {
		String value =  Env.getProperty("com.kavin.password");
		System.out.println(value);
		return value;
	}
	
	
}
