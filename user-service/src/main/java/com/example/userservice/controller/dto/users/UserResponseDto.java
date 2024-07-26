package com.example.userservice.controller.dto.users;

import com.example.userservice.controller.dto.orders.OrderResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private Long userId;
    private String email;
    private String name;
    private List<OrderResponseDto> orders;

}
