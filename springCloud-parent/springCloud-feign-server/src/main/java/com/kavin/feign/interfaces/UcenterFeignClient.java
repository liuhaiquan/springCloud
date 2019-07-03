package com.kavin.feign.interfaces;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kavin.core.data.DataObject;

@FeignClient("ucenter")  //此处的值是需要调用的服务名称，也就是applicaiton.yml 文件中的spring.application.name的值
public interface UcenterFeignClient {
	@RequestMapping(value = "/{model}/{method}")
	public DataObject commonAjax(@PathVariable("model") String model,@PathVariable("method") String method,@RequestBody DataObject dataObject);
	
	
}
