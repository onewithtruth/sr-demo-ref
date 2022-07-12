package com.codestates.srdemoref.order.entity;

import com.codestates.srdemoref.coffee.entity.CoffeeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ORDER_Coffee")
public class OrderCoffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrderCoffeeId;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "COFFEE_ID")
    private CoffeeEntity coffee;

    @Column(nullable = false)
    private int quantity;

    public void addOrder(OrderEntity order) {
        this.order = order;
        if (!this.order.getOrderCoffees().contains(this)) {
            this.order.getOrderCoffees().add(this);
        }
    }

    public void addCoffee(CoffeeEntity coffee) {
        this.coffee = coffee;
        if (!this.coffee.getOrderCoffees().contains(this)) {
            this.coffee.getOrderCoffees().add(this);
        }
    }

}
