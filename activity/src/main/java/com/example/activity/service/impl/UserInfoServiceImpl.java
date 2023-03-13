package com.example.activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activity.domain.UserInfo;
import com.example.activity.service.UserInfoService;
import com.example.activity.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author ZELIN7
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2023-03-13 11:02:45
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




