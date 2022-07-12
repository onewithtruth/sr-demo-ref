package com.codestates.srdemoref.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class OrderCoffeeDto {
    @Positive
    private long coffeeId;

    @Positive
    private int quantity;

    @Builder
    OrderCoffeeDto(long coffeeId, int quantity) {
        this.coffeeId = coffeeId;
        this.quantity = quantity;
    }
}
