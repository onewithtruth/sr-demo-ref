package com.codestates.srdemoref.order.service;

import com.codestates.srdemoref.coffee.service.CoffeeService;
import com.codestates.srdemoref.exception.BusinessLogicException;
import com.codestates.srdemoref.exception.ExceptionCode;
import com.codestates.srdemoref.member.service.MemberService;
import com.codestates.srdemoref.order.entity.OrderEntity;
import com.codestates.srdemoref.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {
    private final MemberService memberService;
    private final OrderRepository orderRepository;
    private final CoffeeService coffeeService;

    public OrderService(MemberService memberService,
                        OrderRepository orderRepository,
                        CoffeeService coffeeService) {
        this.memberService = memberService;
        this.orderRepository = orderRepository;
        this.coffeeService = coffeeService;
    }

    public OrderEntity createOrder(OrderEntity order) {
        // 회원이 존재하는지 확인
        memberService.findVerifiedMember(order.getMember().getMemberId());

        // TODO 커피가 존재하는지 조회하는 로직이 포함되어야 합니다.

        return orderRepository.save(order);
    }

    // 메서드 추가
    public OrderEntity updateOrder(OrderEntity order) {
        OrderEntity findOrder = findVerifiedOrder(order.getOrderId());

        Optional.ofNullable(order.getOrderStatus())
                .ifPresent(orderStatus -> findOrder.setOrderStatus(orderStatus));
        findOrder.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(findOrder);
    }

    public OrderEntity findOrder(long orderId, CoffeeService coffeeService) {
        return findVerifiedOrder(orderId);
    }

    public Page<OrderEntity> findOrders(int page, int size, CoffeeService coffeeService) {
        return orderRepository.findAll(PageRequest.of(page, size,
                Sort.by("orderId").descending()));
    }

    public void cancelOrder(long orderId) {
        OrderEntity findOrder = findVerifiedOrder(orderId);
        int step = findOrder.getOrderStatus().getStepNumber();

        // OrderStatus의 step이 2 이상일 경우(ORDER_CONFIRM)에는 주문 취소가 되지 않도록한다.
        if (step >= 2) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }
        findOrder.setOrderStatus(OrderEntity.OrderStatus.ORDER_CANCEL);
        findOrder.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(findOrder);
    }

    private OrderEntity findVerifiedOrder(long orderId) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);
        OrderEntity findOrder =
                optionalOrder.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }
}
