package com.sparklep.preorder.domain.order.controller;

import com.sparklep.preorder.domain.order.dto.OrderRequestDto;
import com.sparklep.preorder.domain.order.dto.OrderResponseDto;
import com.sparklep.preorder.domain.order.service.OrderService;
import com.sparklep.preorder.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderResponseDto> getMyOrders(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.getMyOrders(userDetails.getUser());
    }
    @PostMapping
    public String addWishlist(@RequestBody List<OrderRequestDto> orderRequestDtoList, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrdersFromWishlist(orderRequestDtoList, userDetails.getUser());
    }
    @DeleteMapping("/{ordersId}")
    public String cancelOrder(@PathVariable(value = "ordersId") Long ordersId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.cancelOrder(ordersId, userDetails.getUser());
    }
    @PutMapping("/{ordersId}")
    public String returnOrder(@PathVariable(value = "ordersId") Long ordersId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.returnOrder(ordersId, userDetails.getUser());
    }
}
