package com.codestates.srdemoref.order.dto;

import com.codestates.srdemoref.order.entity.OrderEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderPatchDto {

    private long orderId;
    private OrderEntity.OrderStatus orderStatus;

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Builder
    OrderPatchDto(long orderId, OrderEntity.OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}
