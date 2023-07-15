package com.myfood.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String number;

    //order state 1 pending payment, 2 processing, 3 delivered, 4 arrived, 5 canceled
    private Integer status;

    private Long userId;

    private Long addressBookId;

    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;

    //payment 1 credit, 2 google pay
    private Integer payMethod;

    private BigDecimal amount;

    private String remark;

    private String userName;

    private String phoneNumber;

    private String address;

    private String consignee;
}
