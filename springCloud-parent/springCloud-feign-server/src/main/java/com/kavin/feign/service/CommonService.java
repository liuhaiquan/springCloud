package com.kavin.feign.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kavin.core.constant.Constant;
import com.kavin.core.data.DataObject;
import com.kavin.core.util.JacksonUtils;
import com.kavin.feign.router.RouterBox;


@Service             
public class CommonService {
	
	@Autowired
	private RouterBox routerBox;

	

	@SuppressWarnings("unchecked")
	public DataObject commonAjax(Map<String,Object> parameter) throws JsonProcessingException {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RTN_CODE, Constant.RTN_CODE_SUCCESS);
		if( StringUtils.isBlank((String)parameter.get(Constant.PROJECT_NAME)) || 
			StringUtils.isBlank((String)parameter.get(Constant.MODEL_NAME)) ||
			StringUtils.isBlank((String)parameter.get(Constant.METHOD_NAME))){
			result.put(Constant.RTN_CODE, Constant.RTN_CODE_FAILURE);
			return new DataObject(result);
		}
		String project = (String)parameter.get(Constant.PROJECT_NAME);
		String model = (String)parameter.get(Constant.MODEL_NAME);
		String method = (String)parameter.get(Constant.METHOD_NAME);
		Map<String,Object> requestJson  =  (Map<String,Object>)parameter.get(Constant.REQUEST_JSON);
		Map rtnMap = routerBox.routeForward(project,model,method,new DataObject(requestJson)).getMap();
		result.putAll(rtnMap);
		return new DataObject(result);
	}
}
