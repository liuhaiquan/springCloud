package com.kavin.feign.router;


import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kavin.core.data.DataObject;
import com.kavin.feign.interfaces.OrderFeignClient;
import com.kavin.feign.interfaces.UcenterFeignClient;

/**
 * 路由控制器
 * @author Kavin
 *
 */
@Component
public class RouterBox  {

	@Autowired
	private UcenterFeignClient ucenterFeignClient;
	
	@Autowired
	private OrderFeignClient orderFeignClient;

    public  DataObject routeForward(String project,String model,String method,DataObject dataObject){
    	
    	if(StringUtils.equals("ucenter", project)) {
    		return ucenterFeignClient.commonAjax(model, method, dataObject);
    	}else if(StringUtils.equals("order", project)) {
    		return orderFeignClient.commonAjax(model, method, dataObject);
    	}
    	
    	return null;
    }

       
}
