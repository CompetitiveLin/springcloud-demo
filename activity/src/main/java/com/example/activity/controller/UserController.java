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

    @PostMapping("/checkout")
    public ResponseResult<?> checkout(){
        userService.checkOut();
        return ResponseResult.success();
    }

    @PostMapping("/checkout/late")
    public ResponseResult<?> lateCheckout(@RequestParam String date){
        userService.lateCheckout(date);
        return ResponseResult.success();
    }


    @GetMapping("/checkout/continuous/count")
    public ResponseResult<Integer> checkoutContinuousCount() {
        return ResponseResult.success(userService.checkoutContinuousCount());
    }

    /**
     *
     * @param date
     * @return
     */
    @GetMapping("/checkout/count")
    public ResponseResult<Integer> checkoutCount(@RequestParam String date) {
        return ResponseResult.success(userService.checkoutCount(date));
    }
}
