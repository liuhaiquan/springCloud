package com.kavin.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;


@EnableEurekaClient //开启Eureka客户端功能
@SpringBootApplication
/**
 * 使用自定义扫描注解  因为在order1项目中使用了commonService。而commonService是其他moulde下的class。
 * 所以扫描的时候需要扫描包为本项目和那个moudle项目下的的目录。否则那个moudle下的bean在此容器中不存在，也就无法注入。
 * @author Kavin
 */
@ComponentScan(basePackages = {"com.kavin.order","com.kavin.feign"})
public class OrderApplicaiton {
	public static void main(String[] args) {
		SpringApplication.run(OrderApplicaiton.class, args);
	}
}
