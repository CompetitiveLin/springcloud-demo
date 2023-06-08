package com.example.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Value("${github.token}")
    private String token;

    @Override
    public void apply(RequestTemplate template) {
        template.header(HttpHeaders.ACCEPT, "application/vnd.github+json");
        template.header(HttpHeaders.AUTHORIZATION, token);
    }
}

