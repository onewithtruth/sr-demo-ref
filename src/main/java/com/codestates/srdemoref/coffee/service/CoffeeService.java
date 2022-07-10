package com.codestates.srdemoref.coffee.service;

import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import com.codestates.srdemoref.coffee.repository.CoffeeRepository;
import com.codestates.srdemoref.exception.BusinessLogicException;
import com.codestates.srdemoref.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public CoffeeEntity createCoffee(CoffeeEntity coffee) {
        // 커피 코드를 대문자로 변경
        String coffeeCode = coffee.getCoffeeCode().toUpperCase();

        // 이미 등록된 커피 코드인지 확인
        verifyExistCoffee(coffeeCode);
        coffee.setCoffeeCode(coffeeCode);

        return coffeeRepository.save(coffee);
    }

    public CoffeeEntity updateCoffee(CoffeeEntity coffee) {
        // 조회하려는 커피가 검증된 커피인지 확인(존재하는 커피인지 확인 등)
        CoffeeEntity findCoffee = findVerifiedCoffee(coffee.getCoffeeId());

        Optional.ofNullable(coffee.getKorName())
                .ifPresent(korName -> findCoffee.setKorName(korName));
        Optional.ofNullable(coffee.getEngName())
                .ifPresent(engName -> findCoffee.setEngName(engName));
        Optional.ofNullable(coffee.getPrice())
                .ifPresent(price -> findCoffee.setPrice(price));

        // 추가된 부분
        Optional.ofNullable(coffee.getCoffeeStatus())
                .ifPresent(coffeeStatus -> findCoffee.setCoffeeStatus(coffeeStatus));

        return coffeeRepository.save(findCoffee);
    }

    public CoffeeEntity findCoffee(long coffeeId) {
        return findVerifiedCoffeeByQuery(coffeeId);
    }

    public Page<CoffeeEntity> findCoffees(int page, int size) {
        return coffeeRepository.findAll(PageRequest.of(page, size,
                Sort.by("coffeeId").descending()));
    }

    public void deleteCoffee(long coffeeId) {
        CoffeeEntity coffee = findVerifiedCoffee(coffeeId);
        coffeeRepository.delete(coffee);
    }

    public CoffeeEntity findVerifiedCoffee(long coffeeId) {
        Optional<CoffeeEntity> optionalCoffee = coffeeRepository.findById(coffeeId);
        CoffeeEntity findCoffee =
                optionalCoffee.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));

        return findCoffee;
    }

    private void verifyExistCoffee(String coffeeCode) {
        Optional<CoffeeEntity> coffee = coffeeRepository.findByCoffeeCode(coffeeCode);
        if(coffee.isPresent())
            throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
    }

    private CoffeeEntity findVerifiedCoffeeByQuery(long coffeeId) {
        Optional<CoffeeEntity> optionalCoffee = coffeeRepository.findByCoffee(coffeeId);
        CoffeeEntity findCoffee =
                optionalCoffee.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));

        return findCoffee;
    }
}
