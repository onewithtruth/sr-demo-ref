package com.codestates.srdemoref.order.controller;

import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import com.codestates.srdemoref.coffee.service.CoffeeService;
import com.codestates.srdemoref.member.service.MemberService;
import com.codestates.srdemoref.order.dto.OrderPatchDto;
import com.codestates.srdemoref.order.dto.OrderPostDto;
import com.codestates.srdemoref.order.entity.OrderCoffee;
import com.codestates.srdemoref.order.entity.OrderEntity;
import com.codestates.srdemoref.order.mapper.OrderMapper;
import com.codestates.srdemoref.order.service.OrderService;
import com.codestates.srdemoref.response.MultiResponseDto;
import com.codestates.srdemoref.response.SingleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper mapper;
    private final CoffeeService coffeeService;
    private final MemberService memberService;

    public OrderController(OrderService orderService,
                           OrderMapper mapper,
                           CoffeeService coffeeService,
                           MemberService memberService) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.coffeeService = coffeeService;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto) {
        OrderEntity order = orderService.createOrder(mapper.orderPostDtoToOrder(orderPostDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order, coffeeService)),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{order-id}")
    public ResponseEntity patchOrder(@PathVariable("order-id") @Positive long orderId,
                                     @Valid @RequestBody OrderPatchDto orderPatchDto) {
        orderPatchDto.setOrderId(orderId);
        OrderEntity order =
                orderService.updateOrder(mapper.orderPatchDtoToOrder(orderPatchDto));

        // patchOrder는 수정하지 마세요. 레퍼런스 코드에서 주문한 커피 정보가 포함 됩니다.
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order, coffeeService))
                , HttpStatus.OK);
    }
    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        OrderEntity order = orderService.findOrder(orderId, coffeeService);

        // TODO JPA 기능에 맞춰서 회원이 주문한 커피 정보를 ResponseEntity에 포함 시키세요.
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order, coffeeService)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<OrderEntity> pageOrders = orderService.findOrders(page - 1, size, coffeeService);
        List<OrderEntity> orders = pageOrders.getContent();

        // TODO JPA 기능에 맞춰서 회원이 주문한 커피 정보 목록을 ResponseEntity에 포함 시키세요.

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.ordersToOrderResponseDtos(orders, coffeeService), pageOrders),
                HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity cancelOrder(@PathVariable("order-id") @Positive long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
