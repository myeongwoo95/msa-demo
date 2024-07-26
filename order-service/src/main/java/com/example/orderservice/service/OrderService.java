package com.example.orderservice.service;

import com.example.orderservice.controller.dto.OrdersRequestDto;
import com.example.orderservice.controller.dto.OrdersResponseDto;
import com.example.orderservice.domain.orders.Orders;
import com.example.orderservice.domain.orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final ModelMapper strictMapper;

    public OrdersResponseDto createOrder(Long userId, OrdersRequestDto ordersRequestDto) {

        ordersRequestDto.setUserId(userId);
        ordersRequestDto.setTotalPrice(ordersRequestDto.getUnitPrice() * ordersRequestDto.getQty());

        System.out.println("ordersRequestDto = " + ordersRequestDto);

        Orders orders = strictMapper.map(ordersRequestDto, Orders.class);
        ordersRepository.save(orders);

        return strictMapper.map(orders, OrdersResponseDto.class);

    }

    public List<Orders> getOrdersByUserId(Long userId) {
        return ordersRepository.findByUserId(userId);
    }

    public OrdersResponseDto getOrderByOrderId(Long orderId) {
        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
        return strictMapper.map(order, OrdersResponseDto.class);
    }
}
