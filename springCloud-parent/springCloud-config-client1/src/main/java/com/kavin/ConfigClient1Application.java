package com.kavin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConfigClient1Application {

	
		public static void main(String[] args) {
			SpringApplication.run(ConfigClient1Application.class, args);
		}
}
