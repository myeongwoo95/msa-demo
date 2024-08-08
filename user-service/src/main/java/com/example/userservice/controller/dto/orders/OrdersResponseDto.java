package com.example.userservice.controller.dto.orders;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OrdersResponseDto {
    private String productId;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;
    private Integer qty;
    private LocalDateTime createdDate;
}
