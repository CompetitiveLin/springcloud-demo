package com.example.activity.api;

import com.example.common.response.ResponseResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProviderApi {
    @GetMapping("/provider")
    ResponseResult provider();

    @GetMapping("/insert")
    ResponseResult insert();

    @GetMapping("/listPage")
    ResponseResult pageSelect(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    @DeleteMapping("/delete/{id}")
    ResponseResult mysqlDelete(@PathVariable(value = "id") Long id);
}
