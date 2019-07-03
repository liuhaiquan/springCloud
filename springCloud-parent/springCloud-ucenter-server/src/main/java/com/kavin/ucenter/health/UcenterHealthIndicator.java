package com.kavin.ucenter.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import com.kavin.ucenter.controller.UserController;

@Component
public class UcenterHealthIndicator implements HealthIndicator{

	

	/**
	 * 根据获取到的数据库状态来设置Health的状态。
	 * 此状态会自动推送到Eureka服务器。服务器上对应的该服务状态为DOWN。
	 * （低版本的可能需要自己重写一个HealthCheckHandler(检查处理器) 来帮助Eureka 获取最新状态。
	 * 也就是MyHealthCheckHandler(有待考察)）
	 */
	@Override
	public Health health() {
		if(UserController.canVisitDb) {
			return new Health.Builder(Status.UP).build();
		}else {
			return new Health.Builder(Status.DOWN).build();
		}
	}

}
