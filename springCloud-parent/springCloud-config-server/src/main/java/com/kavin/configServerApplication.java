package com.kavin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //开启Eureka客户端功能
@EnableConfigServer

/**
 * 在依赖中添加了消息总线
 * @author kavin
 *  消息总线流程
 * 1.提交代码触发post请求给bus/refresh
 * 2.server端接收到请求并发送给Spring Cloud Bus
 * 3.Spring Cloud bus接到消息并通知给其它客户端
 * 4.其它客户端接收到通知，请求Server端获取最新配置
 * 5.全部客户端均获取到最新的配置
 * 
 * 只要Client 添加了spring-cloud-starter-bus-amqp，在获取远程仓库的属性的类上添加@RefreshScope注解，
 * 就可以获取最新的远程仓库的配置文件
 */

public class configServerApplication {

	
	
	//在浏览器中输入  http://localhost:7076/test-config.yml 访问的仓库中的配置文件
	public static void main(String[] args) {
		SpringApplication.run(configServerApplication.class, args);
	}
}
