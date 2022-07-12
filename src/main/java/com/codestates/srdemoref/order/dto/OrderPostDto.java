package com.codestates.srdemoref.order.dto;

import com.codestates.srdemoref.member.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderPostDto {
    @Positive
    private long memberId;

    @Valid
    private List<OrderCoffeeDto> orderCoffees;


//    public MemberEntity getMember() {
//        MemberEntity member = new MemberEntity();
//        member.setMemberId(memberId);
//        return member;
//    }

    @Builder
    OrderPostDto(long memberId, List<OrderCoffeeDto> orderCoffees) {
        this.memberId = memberId;
        this.orderCoffees = orderCoffees;
    }
}
