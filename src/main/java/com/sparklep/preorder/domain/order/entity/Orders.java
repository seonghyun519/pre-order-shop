package com.sparklep.preorder.domain.order.entity;

import com.sparklep.preorder.global.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Orders extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orderStatusEnum", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;

    @Column(name = "totalPrice", nullable = false)
    private int totalPrice;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @OneToMany
    private List<OrderDetail> orderDetail;

    public void updateOrderStatus(OrderStatusEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }
}
