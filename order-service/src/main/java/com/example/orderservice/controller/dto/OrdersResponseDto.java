package com.example.orderservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdersResponseDto {

    private Long productId;
    private String productName;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;

    private Long orderId;

}
