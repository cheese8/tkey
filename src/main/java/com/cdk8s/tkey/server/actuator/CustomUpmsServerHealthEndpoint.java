package com.cdk8s.tkey.server.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cdk8s.tkey.server.util.okhttp.OkHttpResponse;
import com.cdk8s.tkey.server.util.okhttp.OkHttpService;

// 模拟检测第三方验证用户名密码接口
@Component
public class CustomUpmsServerHealthEndpoint extends AbstractHealthIndicator {

    @Autowired
    private OkHttpService okHttpService;

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        OkHttpResponse okHttpResponse = okHttpService.get("https://www.baidu.com");
        if (okHttpResponse == null || okHttpResponse.getStatus() != HttpStatus.OK.value()) {
            builder.down();
        }else {
            builder.up();
        }
    }
}