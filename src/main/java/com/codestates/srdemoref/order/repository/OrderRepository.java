package com.codestates.srdemoref.order.repository;

import com.codestates.srdemoref.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
