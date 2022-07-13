package com.codestates.srdemoref.order.mapper;

import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import com.codestates.srdemoref.coffee.service.CoffeeService;
import com.codestates.srdemoref.member.entity.MemberEntity;
import com.codestates.srdemoref.order.dto.OrderCoffeeResponseDto;
import com.codestates.srdemoref.order.dto.OrderPatchDto;
import com.codestates.srdemoref.order.dto.OrderPostDto;
import com.codestates.srdemoref.order.dto.OrderResponseDto;
import com.codestates.srdemoref.order.entity.OrderCoffee;
import com.codestates.srdemoref.order.entity.OrderEntity;
import org.hibernate.validator.internal.metadata.aggregated.rule.VoidMethodsMustNotBeReturnValueConstrained;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    default OrderEntity orderPostDtoToOrder(OrderPostDto orderPostDto) {

        OrderEntity order = new OrderEntity();
        MemberEntity member = new MemberEntity();
        member.setMemberId(orderPostDto.getMemberId());

        List<OrderCoffee> orderCoffees = orderPostDto.getOrderCoffees()
                .stream()
                .map(orderCoffeeDto -> {
                    CoffeeEntity coffee = new CoffeeEntity();
                    coffee.setCoffeeId(orderCoffeeDto.getCoffeeId());
                    OrderCoffee orderCoffee = new OrderCoffee();
                    orderCoffee.addOrder(order);
                    orderCoffee.addCoffee(coffee);
                    orderCoffee.setQuantity(orderCoffeeDto.getQuantity());
                    order.addOrderCoffee(orderCoffee);
                    return orderCoffee;
                })
                .collect(Collectors.toList());
        order.setMember(member);
        order.setOrderCoffees(orderCoffees);
        return order;
    }
    OrderEntity orderPatchDtoToOrder(OrderPatchDto orderPatchDto);

    default OrderResponseDto orderToOrderResponseDto(OrderEntity order, CoffeeService coffeeService) {

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .memberId(order.getMember().getMemberId())
                .orderStatus(order.getOrderStatus())
                .orderCoffees(order.getOrderCoffees()
                        .stream()
                        .map(orderCoffee -> {
                            CoffeeEntity coffee = coffeeService.findCoffee(
                                    orderCoffee.getCoffee().getCoffeeId()
                            );
                            OrderCoffeeResponseDto orderCoffeeResponseDto = OrderCoffeeResponseDto.builder()
                                    .coffeeId(orderCoffee.getCoffee().getCoffeeId())
                                    .quantity(orderCoffee.getQuantity())
                                    .korName(coffee.getKorName())
                                    .engName(coffee.getEngName())
                                    .price(coffee.getPrice())
                                    .build();
                            return orderCoffeeResponseDto;
                        })
                        .collect(Collectors.toList()))
                .createdAt(order.getCreatedAt())
                .build();
        return orderResponseDto;
    }

    default List<OrderResponseDto> ordersToOrderResponseDtos(List<OrderEntity> orders, CoffeeService coffeeService) {

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        orders.stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                            .orderId(order.getOrderId())
                            .memberId(order.getMember().getMemberId())
                            .orderStatus(order.getOrderStatus())
                            .orderCoffees(order.getOrderCoffees()
                                    .stream()
                                    .map(orderCoffee -> {
                                        CoffeeEntity coffee = coffeeService.findCoffee(
                                                orderCoffee.getCoffee().getCoffeeId()
                                        );
                                        OrderCoffeeResponseDto orderCoffeeResponseDto = OrderCoffeeResponseDto.builder()
                                                .coffeeId(orderCoffee.getCoffee().getCoffeeId())
                                                .quantity(orderCoffee.getQuantity())
                                                .korName(coffee.getKorName())
                                                .engName(coffee.getEngName())
                                                .price(coffee.getPrice())
                                                .build();
                                        return orderCoffeeResponseDto;
                                    })
                                    .collect(Collectors.toList()))
                            .createdAt(order.getCreatedAt())
                            .build();
                    return orderResponseDtos.add(orderResponseDto);
                })
                .collect(Collectors.toList());

        return orderResponseDtos;
    }
}
