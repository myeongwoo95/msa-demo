package com.example.catalogservice.controller.dto.catalog;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatalogResponseDto {

    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer totalPrice;
    private Data createdDate;

}
