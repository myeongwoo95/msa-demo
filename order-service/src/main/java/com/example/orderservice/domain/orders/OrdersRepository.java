package com.example.orderservice.domain.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByProductId(Long productId);
    List<Orders> findByUserId(Long userId);

}
