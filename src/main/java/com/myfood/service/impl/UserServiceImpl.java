package com.myfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.entity.User;
import com.myfood.mapper.UserMapper;
import com.myfood.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
