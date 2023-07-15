package com.myfood.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车
 */
@Data
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long userId;

    private Long dishId;

    private Long comboId;

    private String requirements;

    private Integer number;

    private BigDecimal amount;

    private String image;

    private LocalDateTime createTime;
}
