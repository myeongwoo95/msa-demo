package com.example.userservice.controller.dto.orders;

import lombok.Data;

import java.util.Date;

@Data
public class OrderResponseDto {

    private String id;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;

    private String orderId;

}
