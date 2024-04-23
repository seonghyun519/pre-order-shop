package com.sparklep.preorder.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private String title;

    private int price;

    private String summary;

    private String description;

    private int stock;

    private String image;
}
