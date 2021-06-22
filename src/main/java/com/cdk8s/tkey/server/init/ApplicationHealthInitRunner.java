package com.cdk8s.tkey.server.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import com.cdk8s.tkey.server.actuator.CustomUpmsServerHealthEndpoint;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ApplicationHealthInitRunner implements ApplicationRunner {

	@Autowired
	private CustomUpmsServerHealthEndpoint customUpmsServerHealthEndpoint;

	@Autowired
	private ReactiveHealthIndicator redisReactiveHealthIndicator;
	
	private boolean checkHealthOnStartup = false;

	@Override
	public void run(ApplicationArguments args) {
	    if (checkHealthOnStartup) {
	        http();
	        redis(); 
	    }
	}

	private void http() {
		Health customUpmsHealth = customUpmsServerHealthEndpoint.health();
		if (customUpmsHealth.getStatus().equals(Status.DOWN)) {
			log.error("启动请求 UPMS 接口失败");
		}
	}

	private void redis() {
		Mono<Health> redisHealth = redisReactiveHealthIndicator.health();
		redisHealth.subscribe(h -> {
			if (h.getStatus().equals(Status.DOWN)) {
				log.error("启动连接 Redis 失败");
			}
		});
	}
}