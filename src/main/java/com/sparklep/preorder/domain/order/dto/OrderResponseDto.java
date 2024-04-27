package com.sparklep.preorder.domain.order.dto;

import com.sparklep.preorder.domain.order.entity.OrderDetail;
import com.sparklep.preorder.domain.order.entity.OrderStatusEnum;
import com.sparklep.preorder.domain.order.entity.Orders;
import com.sparklep.preorder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponseDto {
    long ordersId;
    List<OrderDetail> orderDetails;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    OrderStatusEnum orderStatusEnum;
    private String address;
    private String phoneNumber;
    public static OrderResponseDto of(Orders orders, String address, String phoneNumber) {
        return OrderResponseDto.builder()
                .ordersId(orders.getId())
                .orderDetails(orders.getOrderDetail())
                .createdAt(orders.getCreatedAt())
                .modifiedAt(orders.getModifiedAt())
                .orderStatusEnum(orders.getOrderStatusEnum())
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }
}
