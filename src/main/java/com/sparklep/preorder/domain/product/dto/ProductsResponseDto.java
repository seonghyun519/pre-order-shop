package com.sparklep.preorder.domain.product.dto;

import com.sparklep.preorder.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductsResponseDto {
    private String title;

    private int price;

    private String summary;

    private int stock;

    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
    public static ProductsResponseDto of(Product product) {
        return ProductsResponseDto.builder()
                .title(product.getTitle())
                .price(product.getPrice())
                .summary(product.getSummary())
                .stock(product.getStock())
                .image(product.getImage())
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .build();
    }
}
