package com.sparklep.preorder.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryScheduler {
    private  final OrderService orderService;
    //매일 00:00 자정 업데이트
    @Scheduled(cron = "0 0 0 * * *")
    public void deliveryScheduler() {
        orderService.updateOrdersStatus();
    }
}
