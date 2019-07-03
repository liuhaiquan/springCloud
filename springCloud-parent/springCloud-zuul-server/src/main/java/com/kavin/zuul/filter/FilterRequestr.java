package com.kavin.zuul.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.kavin.core.constant.Constant;
import com.kavin.core.util.JacksonUtils;
import com.kavin.zuul.utils.HttpClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/** 
 * 在请求前面过滤    下面的 filterType()起到了作用
 * 自定义路由器
 * @author Kavin
 *
 */

//通过 http://localhost:7075/order/order/callUI 地址去测试此过滤器
@Component
public class FilterRequestr extends ZuulFilter{
	private static Logger log = LoggerFactory.getLogger(FilterRequestr.class);
	@Value("${loginUrl}")
	private String RedirectUrl;
	
	 /**
     * 该Filter具体的执行活动
     */
	@SuppressWarnings("rawtypes")
	@Override
	public Object run() {
		
		try {
				System.out.println("pre 类型的过滤器执行了");
				Map result = new HashMap();
				RequestContext ctx = RequestContext.getCurrentContext();
				HttpServletResponse response =  ctx.getResponse();
				Map parameter = (Map)HttpClient.getJsonDataFromRequest(ctx).get(Constant.REQUEST_JSON);
				
				Object accessToken = parameter.get("accessToken");
				//request.setCharacterEncoding("UTF-8");
	            //这句话的意思，是让浏览器用utf8来解析返回的数据
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
				//response.setCharacterEncoding("UTF-8");

				if (accessToken != null) {
					
					Object username = parameter.get("username");
					Object password = parameter.get("password");
					if(StringUtils.equals("admin", (String)username) && StringUtils.equals("admin", (String)password)) {
						//进行路由转发
					}else {
						ctx.setSendZuulResponse(false);//false 为不路由该请求  true 为路由该请求
						result.put("result", "账号或密码错误!");
						//前台的ajax的type是json。所以这里需要返回json格式数据，否则会走error回调函数
						response.getWriter().write(JacksonUtils.getJsonFromMap(result)); 
					}
				}else {
					log.warn("token is empty");
					ctx.setResponseStatusCode(401);  ////设置错误码
					ctx.setSendZuulResponse(false);//false 为不路由该请求  true 为路由该请求
					result.put("result", "token is empty");
					//前台的ajax的type是json。所以这里需要返回json格式数据，否则会走error回调函数
					response.getWriter().write(JacksonUtils.getJsonFromMap(result));
					//ctx.getResponse().sendRedirect(RedirectUrl);
				}
			} catch (Exception e) {
		}
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
		return FilterConstants.PRE_TYPE;// 前置过滤器  
	}

}
