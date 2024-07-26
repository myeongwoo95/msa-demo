package com.example.orderservice.controller.dto;

import lombok.Data;

@Data
public class OrdersRequestDto {

    private Long productId;
    private Long userId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

}
