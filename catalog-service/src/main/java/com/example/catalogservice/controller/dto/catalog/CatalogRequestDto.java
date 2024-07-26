package com.example.catalogservice.controller.dto.catalog;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class CatalogRequestDto {

    private Long productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

}
