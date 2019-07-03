package com.kavin.zuul.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kavin.core.constant.Constant;
import com.kavin.core.util.JacksonUtils;
import com.netflix.zuul.context.RequestContext;

public class HttpClient {

	
	/**
	 * 从request中获取传来的json格式的参数封装成Map返回
	 * @param request
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String,Object> getJsonDataFromRequest(RequestContext context) throws JsonParseException, JsonMappingException, IOException{
		HttpServletRequest request = context.getRequest();
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			sb.append(inputStr);

		}
		Map parameter = new HashMap();
		String requestJson = sb.toString();
		if(StringUtils.isNotBlank(requestJson)) {
			parameter = JacksonUtils.getMapFromJson(requestJson);
		}else {
			//防止get请求获取不到Constant.REQUEST_JSON 这个Key
			parameter.put(Constant.REQUEST_JSON, new HashMap());
		}
		return parameter;
	}
	/**
	 * 对请求的结果进行封装.只提取map的值
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static void getJsonDataFromResponse(RequestContext context) {
		Map<String,Object> result = new HashMap<String,Object>();
		Object zuulResponse = context.get("zuulResponse");
		String reponseBody =  null;
	     if (zuulResponse != null) {
	         RibbonHttpResponse resp = (RibbonHttpResponse) zuulResponse;
	        
			try {
				String body = IOUtils.toString(resp.getBody());
				result = (Map<String,Object>)JacksonUtils.getMapFromJson(body).get("map");
				reponseBody = JacksonUtils.getJsonFromMap(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("响应结果:"+reponseBody);
			
	         resp.close();
	         context.setResponseBody(reponseBody);
	     }
	}

}
