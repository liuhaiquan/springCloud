package com.kavin.feign;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * feign 主要用于微服务之间的的接口调用，
 * 前台过来的请求一般使用zuul进行分发处理、
 * @author kavin
 *
 */
@SpringBootApplication
@EnableEurekaClient //开启Eureka客户端功能
@EnableFeignClients // 开启feign 功能 ,可以进行负载均衡。
//@ComponentScan(basePackages = { "com.kavin.feign.service" })
public class FeignApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(FeignApplication.class, args);
		System.out.println("Feign服务启动成功！");

	}
	
}
