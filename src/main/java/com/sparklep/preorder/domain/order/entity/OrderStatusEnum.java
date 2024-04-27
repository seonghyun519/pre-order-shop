package com.sparklep.preorder.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusEnum {

    ORDER_AWAITING_SHIPMENT("ORDER_AWAITING_SHIPMENT", "배송대기"),
    ORDER_IN_TRANSIT("ORDER_IN_TRANSIT", "배송중"),
    ORDER_DELIVERED("ORDER_DELIVERED", "배송완료"),
    ORDER_CANCELLED("ORDER_CANCELLED", "주문취소"),
    RETURN_IN_PROGRESS("RETURN_IN_PROGRESS", "반품중"),
    RETURN_COMPLETED("RETURN_COMPLETED", "반품완료");

    private final String code;
    private final String korean;
}