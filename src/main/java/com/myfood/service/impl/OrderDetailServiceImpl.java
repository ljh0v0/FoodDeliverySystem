package com.myfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.entity.OrderDetail;
import com.myfood.mapper.OrderDetailMapper;
import com.myfood.mapper.OrderMapper;
import com.myfood.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
