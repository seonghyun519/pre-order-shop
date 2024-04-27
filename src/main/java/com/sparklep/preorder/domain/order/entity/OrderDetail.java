package com.sparklep.preorder.domain.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productNo", nullable = false)
    private Long productNo;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "productTitle")
    private String productTitle;

    @Column(name = "summary")
    private String summary;
}
