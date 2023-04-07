package com.example.auth.server.controller;

import com.example.auth.server.service.SysAuthService;
import com.example.common.core.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class TokenController {
    private final HttpServletRequest httpServletRequest;

    private final SysAuthService sysAuthService;

    @GetMapping("/captcha")
    public ResponseResult captcha(){
        return ResponseResult.success(sysAuthService.captcha());
    }
}
