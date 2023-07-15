package com.myfood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myfood.common.BaseContext;
import com.myfood.common.CustomException;
import com.myfood.entity.*;
import com.myfood.mapper.OrderMapper;
import com.myfood.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    @Transactional
    public void submit(Orders orders) {
        Long userId = BaseContext.getCurrentId();

        LambdaQueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartQueryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(shoppingCartQueryWrapper);

        if(shoppingCarts == null || shoppingCarts.size() == 0){
            throw new CustomException("Cannot submit the order since the shopping cart is empty");
        }

        User userInfo = userService.getById(userId);

        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook == null){
            throw new CustomException("Wrong address info");
        }

        AtomicInteger amount = new AtomicInteger(0);

        Long orderId = IdWorker.getId();

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item)->{
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setRequirements(item.getRequirements());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setComboId(item.getComboId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(userInfo.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhoneNumber(addressBook.getPhoneNumber());
        orders.setAddress((addressBook.getCountry() == null ? "" : addressBook.getCountry()) + " "
                + (addressBook.getProvince() == null ? "" : addressBook.getProvince()) + " "
                + (addressBook.getCity() == null ? "" : addressBook.getCity()) + " "
                + (addressBook.getStreet() == null ? "" : addressBook.getStreet()) + " "
                + (addressBook.getPostCode() == null ? "" : addressBook.getPostCode())
        );
        this.save(orders);

        orderDetailService.saveBatch(orderDetails);

        shoppingCartService.remove(shoppingCartQueryWrapper);
    }
}
