package com.example.userservice.controller.dto.users;

import com.example.userservice.controller.dto.orders.OrdersResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersResponseDto {
    private Long userId;
    private String email;
    private String name;
    private List<OrdersResponseDto> orders;

}
