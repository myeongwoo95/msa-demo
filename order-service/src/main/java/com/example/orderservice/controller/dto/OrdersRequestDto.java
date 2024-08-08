package com.example.orderservice.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrdersRequestDto {
    private Long productId;
    private Long userId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
}
