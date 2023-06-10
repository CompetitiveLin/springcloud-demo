package com.example.activity.controller;

import com.example.activity.service.UserService;
import com.example.common.core.response.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign/in")
    public ResponseResult<?> signIn(){
        userService.signIn();
        return ResponseResult.success();
    }

    @GetMapping("/sign/continuous/count")
    public ResponseResult<Integer> signContinuousCount() {
        return ResponseResult.success(userService.signContinuousCount());
    }

    @GetMapping("/sign/count")
    public ResponseResult<Integer> signCount(@RequestParam String date) {
        return ResponseResult.success(userService.signCount(date));
    }
}
