package com.example.auth.server.controller;

import com.example.common.core.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class TokenController {

    @GetMapping("/test")
    public ResponseResult test(HttpServletRequest request){
        return ResponseResult.success(request.toString());
    }
}
