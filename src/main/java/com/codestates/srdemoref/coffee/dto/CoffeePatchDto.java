package com.codestates.srdemoref.coffee.dto;

import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import com.codestates.srdemoref.validator.NotSpace;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class CoffeePatchDto {
    private long coffeeId;

    @NotSpace(message = "커피명(한글)은 공백이 아니어야 합니다.")
    private String korName;

    @Pattern(regexp = "^([A-Za-z])(\\s?[A-Za-z])*$", message = "커피명(영문)은 영문이어야 합니다. 예) Cafe Latte")
    private String engName;

    @Range(min= 100, max= 50000)
    private int price;

    // TODO 추가된 부분. 커피 상태 값을 사전에 체크하는 Custom Validator를 만들수도 있다.
    private CoffeeEntity.CoffeeStatus coffeeStatus;

    @NotBlank
    @Pattern(regexp = "^([A-Za-z]){3}$",
            message = "커피 코드는 3자리 영문이어야 합니다.")
    private String coffeeCode;

    public void setCoffeeId(long coffeeId) {
        this.coffeeId = coffeeId;
    }

    @Builder
    public CoffeePatchDto(long coffeeId,
                          String korName,
                          String engName,
                          int price,
                          CoffeeEntity.CoffeeStatus coffeeStatus,
                          String coffeeCode) {
        this.coffeeId = coffeeId;
        this.korName = korName;
        this.engName = engName;
        this.price = price;
        this.coffeeStatus = coffeeStatus;
        this.coffeeCode = coffeeCode;
    }
}
