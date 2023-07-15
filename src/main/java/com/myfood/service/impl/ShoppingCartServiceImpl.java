package com.myfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.entity.ShoppingCart;
import com.myfood.mapper.ShoppingCartMapper;
import com.myfood.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService{
}
