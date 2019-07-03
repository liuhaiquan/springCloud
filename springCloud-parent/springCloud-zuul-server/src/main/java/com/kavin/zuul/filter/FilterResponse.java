package com.kavin.zuul.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import com.kavin.zuul.utils.HttpClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 在route和error过滤器之后被调用.过滤响应结果
 * @author kavin
 *
 */


//通过 http://localhost:7075/order/order/callUI 地址去测试此过滤器
@Component
public class FilterResponse extends ZuulFilter{

	private static Logger log = LoggerFactory.getLogger(FilterResponse.class);

	
	 /**
     * 该Filter具体的执行活动
     */
	@Override
	public Object run() {
		System.out.println("post 类型的过滤器执行了");
		RequestContext context = RequestContext.getCurrentContext();
		HttpClient.getJsonDataFromResponse(context);
		return null;
	}

    /**
     * 指定需要执行该Filter的规则
     * 返回true则执行run()
     * 返回false则不执行run()
     */
	@Override
	public boolean shouldFilter() {
		return true;
	}

    /**
     * 指定该Filter执行的顺序（Filter从小到大执行）
     * DEBUG_FILTER_ORDER = 1;
     * FORM_BODY_WRAPPER_FILTER_ORDER = -1;
     * PRE_DECORATION_FILTER_ORDER = 5;
     * RIBBON_ROUTING_FILTER_ORDER = 10;
     * SEND_ERROR_FILTER_ORDER = 0;
     * SEND_FORWARD_FILTER_ORDER = 500;
     * SEND_RESPONSE_FILTER_ORDER = 1000;
     * SIMPLE_HOST_ROUTING_FILTER_ORDER = 100;
     * SERVLET_30_WRAPPER_FILTER_ORDER = -2;
     * SERVLET_DETECTION_FILTER_ORDER = -3;
	 **/
	@Override
	public int filterOrder() {
		return 0; // 优先级为0，数字越大，优先级越低  
	}

	
    /**
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
	 * 		pre：可以在请求被路由之前调用
	 * 		route：在路由请求时候被调用
	 * 		post：在route和error过滤器之后被调用
	 * 		error：处理请求时发生错误时被调用
     */
	@Override
	public String filterType() {
		
		return FilterConstants.POST_TYPE;// 在route和error过滤器之后被调用
	}


}
