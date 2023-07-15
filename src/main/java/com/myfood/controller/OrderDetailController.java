package com.myfood.controller;

import com.myfood.common.R;
import com.myfood.entity.Orders;
import com.myfood.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order_detail")
@Slf4j
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

}
