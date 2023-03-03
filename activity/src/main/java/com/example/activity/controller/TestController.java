package com.example.activity.controller;

import com.example.common.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseResult test(){
        return ResponseResult.success();
    }

}
