package com.sparklep.preorder.domain.product.entity;

import com.sparklep.preorder.global.entity.TimeStamp;
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
public class Product extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNo;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "image")
    private String image;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductStatusEnum status;

    public void sellProduct(int count) {
        stock -= count;
    }
    public void restockProduct(int count) {
        stock += count;
    }
    public void updateProductStatus(ProductStatusEnum status) {
        this.status = status;
    }
}
