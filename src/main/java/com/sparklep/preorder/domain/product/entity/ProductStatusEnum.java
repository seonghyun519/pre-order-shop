package com.sparklep.preorder.domain.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatusEnum {
    SELLING("SELLING", "판매중"),
    OUT_OF_STOCK("OUT_OF_STOCK", "재고없음"),
    ON_HOLD("ON_HOLD", "일시중지"),
    DISCONTINUED("DISCONTINUED", "판매종료");

    private final String code;
    private final String korean;
}
