package com.myfood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myfood.entity.Orders;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
