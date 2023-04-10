package com.example.activity.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activity.domain.UserInfo;
import com.example.common.core.response.ResponseResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;


public interface ProviderApi {
    
    @GetMapping("/activity/sendEmail")
    ResponseResult<Boolean> sendEmail(@RequestParam("emailAddress") String emailAddress);
    @GetMapping("/activity/provider")
    ResponseResult<HashMap<String, Boolean>> provider();

    @GetMapping("/activity/insert")
    ResponseResult<?> insert();

    @GetMapping("/activity/listPage")
    ResponseResult<Page<UserInfo>> pageSelect(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    @DeleteMapping("/activity/delete/{id}")
    ResponseResult<?> mysqlDelete(@PathVariable(value = "id") Long id);
}
