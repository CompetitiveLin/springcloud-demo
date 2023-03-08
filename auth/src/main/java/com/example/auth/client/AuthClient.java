package com.example.auth.client;


import com.example.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authClient", url = "localhost:8022")
public interface AuthClient {

    @GetMapping("/provider")
    ResponseResult provider();
}
