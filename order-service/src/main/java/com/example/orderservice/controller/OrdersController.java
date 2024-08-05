package com.example.orderservice.controller;

import com.example.orderservice.controller.dto.OrdersRequestDto;
import com.example.orderservice.controller.dto.OrdersResponseDto;
import com.example.orderservice.domain.orders.Orders;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrdersController {

    private final OrderService orderService;
    private final ModelMapper strictMapper;

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrdersResponseDto> createOrder(@PathVariable("userId") Long userId, @RequestBody OrdersRequestDto ordersRequestDto) {

        OrdersResponseDto ordersResponseDto = orderService.createOrder(userId, ordersRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ordersResponseDto);

    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrdersResponseDto>> getOrdersByUserId(@PathVariable("userId") Long userId) {
        List<Orders> orders = orderService.getOrdersByUserId(userId);

        List<OrdersResponseDto> result = new ArrayList<>();

        orders.forEach(v -> {
            result.add(strictMapper.map(v, OrdersResponseDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
