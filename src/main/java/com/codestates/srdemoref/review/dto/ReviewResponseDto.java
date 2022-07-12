package com.codestates.srdemoref.review.dto;

import com.codestates.srdemoref.member.entity.MemberEntity;
import com.codestates.srdemoref.order.entity.OrderEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewResponseDto {
    private String title;

    private String content;

    private String image;

    private long memberId;

    private long orderId;

    public void setMember(MemberEntity member) {
        this.memberId = member.getMemberId();
    }

    public void setOrder(OrderEntity order) {
        this.orderId = order.getOrderId();
    }
}
