package com.example.order.controller;

import com.example.common.core.response.ResponseResult;
import com.example.order.client.ConsumerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class TestController {
    private final ConsumerClient consumerClient;

    @GetMapping("/test")
    public ResponseResult test(){
        return consumerClient.sendEmail("EEEEEEEEEMMMMMAAAAAILLLLL");
    }
}
