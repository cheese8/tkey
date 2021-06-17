package com.cdk8s.tkey.server.config;


import java.util.List;
import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cdk8s.tkey.server.pojo.dto.param.resolve.OauthAuthorizeParamArgumentResolver;
import com.cdk8s.tkey.server.pojo.dto.param.resolve.OauthFormParamArgumentResolver;
import com.cdk8s.tkey.server.pojo.dto.param.resolve.OauthIntrospectTokenParamArgumentResolver;
import com.cdk8s.tkey.server.pojo.dto.param.resolve.OauthRefreshTokenParamArgumentResolver;
import com.cdk8s.tkey.server.pojo.dto.param.resolve.OauthTokenParamArgumentResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new OauthAuthorizeParamArgumentResolver());
		argumentResolvers.add(new OauthFormParamArgumentResolver());
		argumentResolvers.add(new OauthRefreshTokenParamArgumentResolver());
		argumentResolvers.add(new OauthTokenParamArgumentResolver());
		argumentResolvers.add(new OauthIntrospectTokenParamArgumentResolver());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowCredentials(true)
				.maxAge(3600L);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (int i = 0; i < converters.size(); i++) {
			if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
				objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
				converter.setObjectMapper(objectMapper);
				converters.set(i, converter);
				break;
			}
		}
	}
}