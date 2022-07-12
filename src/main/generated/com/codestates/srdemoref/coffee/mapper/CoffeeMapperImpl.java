package com.codestates.srdemoref.coffee.mapper;

import com.codestates.srdemoref.coffee.dto.CoffeePatchDto;
import com.codestates.srdemoref.coffee.dto.CoffeePostDto;
import com.codestates.srdemoref.coffee.dto.CoffeeResponseDto;
import com.codestates.srdemoref.coffee.dto.CoffeeResponseDto.CoffeeResponseDtoBuilder;
import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T16:02:09+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class CoffeeMapperImpl implements CoffeeMapper {

    @Override
    public CoffeeEntity coffeePostDtoToCoffee(CoffeePostDto coffeePostDto) {
        if ( coffeePostDto == null ) {
            return null;
        }

        CoffeeEntity coffeeEntity = new CoffeeEntity();

        coffeeEntity.setKorName( coffeePostDto.getKorName() );
        coffeeEntity.setEngName( coffeePostDto.getEngName() );
        coffeeEntity.setPrice( coffeePostDto.getPrice() );
        coffeeEntity.setCoffeeCode( coffeePostDto.getCoffeeCode() );

        return coffeeEntity;
    }

    @Override
    public CoffeeEntity coffeePatchDtoToCoffee(CoffeePatchDto coffeePatchDto) {
        if ( coffeePatchDto == null ) {
            return null;
        }

        CoffeeEntity coffeeEntity = new CoffeeEntity();

        coffeeEntity.setCoffeeId( coffeePatchDto.getCoffeeId() );
        coffeeEntity.setKorName( coffeePatchDto.getKorName() );
        coffeeEntity.setEngName( coffeePatchDto.getEngName() );
        coffeeEntity.setPrice( coffeePatchDto.getPrice() );
        coffeeEntity.setCoffeeCode( coffeePatchDto.getCoffeeCode() );
        coffeeEntity.setCoffeeStatus( coffeePatchDto.getCoffeeStatus() );

        return coffeeEntity;
    }

    @Override
    public CoffeeResponseDto coffeeToCoffeeResponseDto(CoffeeEntity coffee) {
        if ( coffee == null ) {
            return null;
        }

        CoffeeResponseDtoBuilder coffeeResponseDto = CoffeeResponseDto.builder();

        if ( coffee.getCoffeeId() != null ) {
            coffeeResponseDto.coffeeId( coffee.getCoffeeId() );
        }
        coffeeResponseDto.korName( coffee.getKorName() );
        coffeeResponseDto.engName( coffee.getEngName() );
        coffeeResponseDto.price( coffee.getPrice() );
        coffeeResponseDto.coffeeStatus( coffee.getCoffeeStatus() );

        return coffeeResponseDto.build();
    }

    @Override
    public List<CoffeeResponseDto> coffeesToCoffeeResponseDtos(List<CoffeeEntity> coffees) {
        if ( coffees == null ) {
            return null;
        }

        List<CoffeeResponseDto> list = new ArrayList<CoffeeResponseDto>( coffees.size() );
        for ( CoffeeEntity coffeeEntity : coffees ) {
            list.add( coffeeToCoffeeResponseDto( coffeeEntity ) );
        }

        return list;
    }
}
