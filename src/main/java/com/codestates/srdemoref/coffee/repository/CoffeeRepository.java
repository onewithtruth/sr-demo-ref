package com.codestates.srdemoref.coffee.repository;

import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<CoffeeEntity, Long> {
    Optional<CoffeeEntity> findByCoffeeCode(String coffeeCode);

    @Query(value = "SELECT c FROM COFFEE c WHERE c.coffeeId = :coffeeId")
    Optional<CoffeeEntity> findByCoffee(@Param("coffeeId") long coffeeId);
}
