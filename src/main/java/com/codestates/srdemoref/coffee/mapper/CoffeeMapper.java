package com.codestates.srdemoref.coffee.mapper;

import com.codestates.srdemoref.coffee.dto.CoffeePatchDto;
import com.codestates.srdemoref.coffee.dto.CoffeePostDto;
import com.codestates.srdemoref.coffee.dto.CoffeeResponseDto;
import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {
    CoffeeEntity coffeePostDtoToCoffee(CoffeePostDto coffeePostDto);
    CoffeeEntity coffeePatchDtoToCoffee(CoffeePatchDto coffeePatchDto);
    CoffeeResponseDto coffeeToCoffeeResponseDto(CoffeeEntity coffee);
    List<CoffeeResponseDto> coffeesToCoffeeResponseDtos(List<CoffeeEntity> coffees);
}
