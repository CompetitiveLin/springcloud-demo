package com.example.activity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.activity.api.ProviderApi;
import com.example.activity.domain.UserInfo;
import com.example.activity.mapper.UserInfoMapper;
import com.example.common.email.service.EmailService;
import com.example.common.core.exception.CustomException;
import com.example.common.core.response.ResponseResult;
import com.example.common.core.response.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProviderController implements ProviderApi {

    private final UserInfoMapper userInfoMapper;

    private final EmailService emailService;


    @Override
    public ResponseResult<Boolean> sendEmail(String emailAddress) {
        emailService.sendEmail(emailAddress);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<HashMap<String, Boolean>> provider(){
        HashMap<String, Boolean> hashmap = new HashMap<>();
        hashmap.put("1", ResponseResult.success().isSuccess());
        hashmap.put("2", ResponseResult.success("data").isSuccess());
        hashmap.put("3", ResponseResult.failed().isSuccess());
        hashmap.put("4", ResponseResult.failed("message").isSuccess());
        hashmap.put("5", ResponseResult.failed(ErrorCode.FORBIDDEN).isSuccess());
        return ResponseResult.success(hashmap);
    }

    @Override
    public ResponseResult<?> insert() {
        if(true) throw new CustomException("CustomException");
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Page<UserInfo>> pageSelect(Integer pageNo, Integer pageSize) {
        Page<UserInfo> page = new Page<>(pageNo, pageSize);
        userInfoMapper.selectPage(page, null);
        return ResponseResult.success(page);

    }

    @Override
    public ResponseResult<?> mysqlDelete(Long id) {
        int status = userInfoMapper.deleteById(id);
        if(status == 1){
            return ResponseResult.success();
        }
        return ResponseResult.failed();
    }
}
