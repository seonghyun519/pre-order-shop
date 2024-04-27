package com.sparklep.preorder.domain.order.repository;

import com.sparklep.preorder.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
//    Optional<OrderDetail> findOrderDetailByOrdersIdAndProductNo(long ordersId, long productNo);
}