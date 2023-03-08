package com.example.activity.api;

import com.example.common.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;

public interface ProviderApi {
    @GetMapping("/provider")
    ResponseResult provider();
}
