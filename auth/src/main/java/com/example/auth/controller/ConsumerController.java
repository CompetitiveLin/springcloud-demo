package com.example.auth.controller;

import com.example.auth.client.ConsumerClient;
import com.example.common.response.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConsumerController {
    private final ConsumerClient consumerClient;

    @GetMapping("/consumer")
    public ResponseResult consumer(){
        return consumerClient.provider();
    }

}
