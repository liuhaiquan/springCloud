package com.kavin.order.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kavin.core.constant.Constant;
import com.kavin.core.data.DataObject;
import com.kavin.feign.service.CommonService;

@RestController
@RequestMapping(value = "/order")
public class orderController {
	@Value("${server.port}")
	private String serverPort;
	
	/**
	 * 此处设计到一个跨包注入问题，commonService是其他moudle路径下的，所以应该在启动类上使用自定义扫描扩大扫描范围
	 * @ComponentScan(basePackages = {"com.kavin.order1","com.kavin.feign"})
	 * 扫描路径下增加调用的模块的需要扫描的包路径
	 */
	@Autowired
	private CommonService commonService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/findOrder")
	public DataObject findOrder(@RequestBody DataObject dataObject) throws Exception {
		Map parameter = dataObject.getMap();
		/*Object token = parameter.get("token");
		 if(token == null ) {
			 throw new Exception("token为空,调用端口号为:"+serverPort+"的服务出现异常！");
		 }*/

		Map result = new HashMap();
		result.put("result","端口号为:"+serverPort+"的服务被访问");
		return new DataObject(result);
	}
	
	/**
	 * 使用feign 调用实现服务与服务之间的互相调用
	 * @return
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/callUI")
	public DataObject callUcenterI(DataObject dataObject) throws JsonProcessingException {
		Map parameter = dataObject.getMap();
		parameter.put(Constant.PROJECT_NAME, "ucenter");
		parameter.put(Constant.MODEL_NAME, "user");
		parameter.put(Constant.METHOD_NAME, "verify");
		return commonService.commonAjax(parameter);
	}
	
}
