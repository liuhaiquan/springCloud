package com.kavin.feign.fallback;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kavin.core.data.DataObject;
import com.kavin.feign.interfaces.OrderFeignClient;

/**
 * 降级机制。
 * 以下四种情况执行此方法(使用的是Hystrix)
 * （1）主方法抛出异常（2）主方法执行超时（3）线程池拒绝（4）断路器打开   
 * 接口失败返回自定义错误信息,此类需要实现带有fallback注解属性的接口
 * @author Administrator
 */
@Component
public class OrderFallBack implements OrderFeignClient{

	@SuppressWarnings("unchecked")
	@Override
	public DataObject commonAjax(String model, String method, DataObject dataObject) {
		Map result = new HashMap();
		result.put("result", "OrderFallBack中findOrder接口返回的错误信息");
		return new DataObject(result);
	}

}
