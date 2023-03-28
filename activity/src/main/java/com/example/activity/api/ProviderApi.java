package com.example.activity.api;

import com.example.common.core.response.ResponseResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


public interface ProviderApi {
    @GetMapping("/activity/provider")
    ResponseResult provider();

    @GetMapping("/activity/insert")
    ResponseResult insert();

    @GetMapping("/activity/listPage")
    ResponseResult pageSelect(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    @DeleteMapping("/activity/delete/{id}")
    ResponseResult mysqlDelete(@PathVariable(value = "id") Long id);
}
