package com.example.activity.controller;

import com.example.activity.service.UserService;
import com.example.common.core.response.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signIn")
    public ResponseResult<?> signIn(){
        return userService.signIn();
    }
}
