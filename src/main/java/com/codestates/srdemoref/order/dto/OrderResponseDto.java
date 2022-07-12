package com.codestates.srdemoref.order.dto;

import com.codestates.srdemoref.member.entity.MemberEntity;
import com.codestates.srdemoref.order.entity.OrderEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponseDto {
    private long orderId;
    private long memberId;
    private OrderEntity.OrderStatus orderStatus;
    private List<OrderCoffeeResponseDto> orderCoffees;
    private LocalDateTime createdAt;

    public void setMember(MemberEntity member) {
        this.memberId = member.getMemberId();
    }
}
