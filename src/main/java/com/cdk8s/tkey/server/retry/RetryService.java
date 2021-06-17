package com.cdk8s.tkey.server.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.cdk8s.tkey.server.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.server.service.OauthThirdPartyApiService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RetryService {

	@Autowired
	private OauthThirdPartyApiService oauthThirdPartyApiService;

	@Retryable(value = {Exception.class}, maxAttempts = 2, backoff = @Backoff(delay = 2000L, multiplier = 1))
	public OauthUserAttribute getOauthUserAttributeBO(String username, String password) {
		return oauthThirdPartyApiService.getOauthUserAttributeDTO(username, password);
	}

	@Recover
	public OauthUserAttribute getOauthUserAttributeBORecover(Exception e) {
		log.error("多次重试调用验证用户名密码接口失败=<{}>", e.getMessage());
		return new OauthUserAttribute();
	}
}