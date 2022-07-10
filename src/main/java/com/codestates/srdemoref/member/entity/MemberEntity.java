package com.codestates.srdemoref.member.entity;

import com.codestates.srdemoref.order.entity.OrderEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "MEMBER")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 13, nullable = false, unique = true)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "UPDATED_AT")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "member")
    private List<OrderEntity> orderEntities = new ArrayList<>();

    public MemberEntity(String email) {
        this.email = email;
    }

    public MemberEntity(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public void addOrder(OrderEntity orderEntity) {
        orderEntities.add(orderEntity);
    }

    // 추가 된 부분
    public enum MemberStatus {
        MEMBER_ACTIVE("ACTIVE MEMBER"),
        MEMBER_SLEEP("SLEEP MEMBER"),
        MEMBER_QUIT("QUIT MEMBER");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
