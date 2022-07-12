package com.codestates.srdemoref.review.dto;

import com.codestates.srdemoref.member.entity.MemberEntity;
import com.codestates.srdemoref.order.entity.OrderEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class ReviewPostDto {
    @NotBlank(message = "title should not be empty")
    private String title;

    @NotBlank(message = "content should not be empty")
    private String content;

    private String image;

    @Positive
    private long memberId;

    @Positive
    private long orderId;

    public MemberEntity getMember() {
        MemberEntity member = new MemberEntity();
        member.setMemberId(memberId);
        return member;
    }

    public OrderEntity getOrder() {
        OrderEntity order = new OrderEntity();
        order.setOrderId(orderId);
        return order;
    }


}
