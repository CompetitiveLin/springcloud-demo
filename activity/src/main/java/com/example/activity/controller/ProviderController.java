package com.example.activity.controller;

import com.example.activity.api.ProviderApi;
import com.example.common.response.ResponseResult;
import com.example.common.response.code.ErrorCode;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ProviderController implements ProviderApi {

    public ResponseResult provider(){
        HashMap<String, Boolean> hashmap = new HashMap<>();
        hashmap.put("1", ResponseResult.success().isSuccess());
        hashmap.put("2", ResponseResult.success("data").isSuccess());
        hashmap.put("3", ResponseResult.failed().isSuccess());
        hashmap.put("4", ResponseResult.failed("message").isSuccess());
        hashmap.put("5", ResponseResult.failed(ErrorCode.FORBIDDEN).isSuccess());
        return ResponseResult.success(hashmap);
    }

}
