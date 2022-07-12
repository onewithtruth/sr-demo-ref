package com.codestates.srdemoref.coffee.dto;

import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CoffeeResponseDto {
    private long coffeeId;
    private String korName;
    private String engName;
    private int price;
    private CoffeeEntity.CoffeeStatus coffeeStatus; // 커피 상태 추가

    public String getCoffeeStatus() {
        return coffeeStatus.getStatus();
    }
}
