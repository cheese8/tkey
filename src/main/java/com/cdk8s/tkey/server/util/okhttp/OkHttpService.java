package com.cdk8s.tkey.server.util.okhttp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdk8s.tkey.server.util.ExceptionUtil;
import com.cdk8s.tkey.server.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
@Slf4j
public class OkHttpService {

    @Autowired
    private OkHttpClient okHttpClient;

    public OkHttpResponse get(String url) {
        Request request = new Request.Builder().url(url).build();
        return getResponse(request);
    }

    public OkHttpResponse get(String url, Map<String, String> queries, Map<String, String> headers) {
        StringBuilder fullUrl = new StringBuilder(url);
        if (queries != null && queries.keySet().size() > 0) {
            fullUrl.append("?");

            for (Map.Entry<String, String> entry : queries.entrySet()) {
                if (StringUtil.isNotBlank(entry.getValue()) && !StringUtil.equalsIgnoreCase(entry.getValue(), "null")) {
                    fullUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
            }

            fullUrl.deleteCharAt(fullUrl.length() - 1);
        }

        Request.Builder builderRequest = new Request.Builder();

        if (headers != null && headers.keySet().size() > 0) {
            headers.forEach(builderRequest::addHeader);
        }

        Request request = builderRequest.url(fullUrl.toString()).build();
        return getResponse(request);
    }

    public OkHttpResponse post(String url, Map<String, String> params, Map<String, String> headers) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.keySet().size() > 0) {
            params.forEach(builder::add);
        }

        Request.Builder builderRequest = new Request.Builder();
        if (headers != null && headers.keySet().size() > 0) {
            headers.forEach(builderRequest::addHeader);
        }

        Request request = builderRequest.url(url).post(builder.build()).build();
        return getResponse(request);
    }

    private OkHttpResponse getResponse(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            OkHttpResponse okHttpResponse = new OkHttpResponse();
            okHttpResponse.setStatus(response.code());
            okHttpResponse.setResponse(response.body().string());
            return okHttpResponse;
        } catch (Exception e) {
            log.error("okhttp error = {}", ExceptionUtil.getStackTraceAsString(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
}