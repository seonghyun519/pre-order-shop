package com.sparklep.preorder.domain.order.repository;

import com.sparklep.preorder.domain.order.entity.OrderStatusEnum;
import com.sparklep.preorder.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByOrderStatusEnumAndCreatedAtBefore(OrderStatusEnum orderStatusEnum, LocalDateTime localDateTime);
    List<Orders> findAllByOrderStatusEnumAndModifiedAtBefore(OrderStatusEnum orderStatusEnum, LocalDateTime localDateTime);

    List<Orders> findAllByUserId(Long userId);
}