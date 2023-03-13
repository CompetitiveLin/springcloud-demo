package com.example.activity.api;

import com.example.common.response.ResponseResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProviderApi {
    @GetMapping("/provider")
    ResponseResult provider();

    @GetMapping("/mysqlTest")
    ResponseResult mysqlTest();

    @GetMapping("/pageSearch")
    ResponseResult pageSelect();

    @DeleteMapping("/delete/{id}")
    ResponseResult mysqlDelete(@PathVariable Long id);
}
