package com.kavin.zuul;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;

@EnableZuulProxy  // 开启路由网关
@EnableEurekaClient  //开启Eureka客户端功能
@SpringBootApplication


/**
 * 
	1、zuul作为整个应用的流量入口，接收所有的请求，如app、网页等，并且将不同的请求转发至不同的处理微服务模块，其作用可视为nginx。
	2、feign则是将当前微服务的部分服务接口暴露出来，并且主要用于各个微服务之间的服务调用。两者的应用层次以及原理均不相同。
	3.zuul也含有hystrix和ribbon，基于http通讯的，可以直接代理服务就行。在它和服务间增加feign只会增加通讯消耗，没有特别的意义。
		feign在服务互相调用的时候用就行了，可以仿rpc通讯。
	4.Feign主要作客户端流控，Feign的负载均衡是基于Eureka实现的
		Zuul主要作服务端流控，并且Zuul的负载均衡结合Eureka实现易用性较好，并且Zuul我一般用在对第三方提供访问接口。
 * @author kavin
 *
 */
public class zuulApplication {
	
	
	/**
	 * 实现动态加载过滤器   加载Groovy文件
	 */
	// 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。
	//PostConstruct在构造函数之后执行,init()方法之前执行。PreDestroy（）方法在destroy()方法执行执行之后执行
	@PostConstruct
	public void zuulInit() {
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        // 读取配置，获取脚本根目录
        String scriptRoot = System.getProperty("zuul.filter.root", "com/kavin/zuul/groovy/filters");
        // 获取刷新间隔
        String refreshInterval = System.getProperty("zuul.filter.refreshInterval", "5");
        if (scriptRoot.length() > 0) scriptRoot = scriptRoot + File.separator;  // 如果根路径不为空，路径后在拼接上 /
        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            //每隔5秒加载一下这些路径下的filter
            //路径是绝对路径，可以使用db加载。实现不用重启服务添加过滤器
            FilterFileManager.init(Integer.parseInt(refreshInterval), scriptRoot + "pre", 
            		scriptRoot + "route", scriptRoot + "post");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(zuulApplication.class, args);
		System.out.println("zuul服务启动成功！");
	}
}
