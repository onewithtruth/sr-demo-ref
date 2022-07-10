package com.codestates.srdemoref.order.entity;

import com.codestates.srdemoref.member.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ORDERS")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDER_REQUEST;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "UPDATED_AT")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private MemberEntity member;

    public void addMember(MemberEntity member) {
        this.member = member;
    }

    public enum OrderStatus {
        ORDER_REQUEST(1, "ORDER REQUEST"),
        ORDER_CONFIRM(2, "ORDER CONFIRM"),
        ORDER_COMPLETE(3, "ORDER COMPLETE"),
        ORDER_CANCEL(4, "ORDER CANCEL");

        @Getter
        private int stepNumber;

        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }
}
