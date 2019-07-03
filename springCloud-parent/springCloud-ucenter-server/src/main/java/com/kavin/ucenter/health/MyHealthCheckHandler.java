package com.kavin.ucenter.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;


/**
 * 把Health.Builder(Status.UP).build() 的状态更新到Eureka服务器
 * 
 *  目前不写该类也可以更新状态到Eureka服务器
 * @author Administrator
 *
 */
@Component
public class MyHealthCheckHandler implements HealthCheckHandler {

	@Autowired
	private UcenterHealthIndicator halthIndicator;
	
	public InstanceStatus getStatus(InstanceStatus currentStatus) {
		Status status = halthIndicator.health().getStatus();
		if(status.equals(Status.UP)) {
			return InstanceStatus.UP;
		} else {
			return InstanceStatus.DOWN;
		}
	}

}
