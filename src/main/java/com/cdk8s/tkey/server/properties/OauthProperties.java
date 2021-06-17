package com.cdk8s.tkey.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "tkey.sso.oauth")
public class OauthProperties {

	private String errorUriMsg = "See the full API docs at https://github.com/cdk8s";
	private Integer nodeNumber = 10;
	private Boolean tgcCookieSecure = true;
	private Integer rememberMeMaxTimeToLiveInSeconds = 7*24*3600;
	private Integer codeMaxTimeToLiveInSeconds = 120;
	private Integer accessTokenMaxTimeToLiveInSeconds = 12*3600;
	private Integer refreshTokenMaxTimeToLiveInSeconds = 24*3600;
	private Integer tgcAndUserInfoMaxTimeToLiveInSeconds = 24*3600;

}