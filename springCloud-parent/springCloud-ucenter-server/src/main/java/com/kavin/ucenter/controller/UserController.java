package com.kavin.ucenter.controller;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.kavin.core.data.DataObject;


@RestController
@RequestMapping("/user")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/verify")
	public DataObject verify(@RequestBody DataObject dataObject) {
		Map parameter =  dataObject.getMap();
		System.out.println(parameter);
		System.out.println("验证成功");
		Map result = new HashMap();
		result.put("result", "验证成功！");
		return new DataObject(result);
	}
	
	
	// 能否访问数据库的标识
	public static boolean canVisitDb = true;
	
	
	/**
	 * 模拟数据库不能连接，但是该服务正常的情况下，
	 * 修改健康状态为DOWN.client端获取服务列表就不会获取到down的实例了。
	 * 也就不是使用该实例进行调用了
	 * @return
	 * 
	 * 访问地址 http://localhost:7071/user/db/false或者是true
	 */
	@RequestMapping(value = "/db/{can}", method = RequestMethod.GET)
	public void SetDB(@PathVariable boolean can) {
		canVisitDb = can;
	}
	
}
