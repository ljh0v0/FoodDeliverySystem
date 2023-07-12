package com.myfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.entity.Requirements;
import com.myfood.mapper.RequirementsMapper;
import com.myfood.service.RequirementsService;
import org.springframework.stereotype.Service;

@Service
public class RequirementsServiceImpl extends ServiceImpl<RequirementsMapper, Requirements> implements RequirementsService {
}
