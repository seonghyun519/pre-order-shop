package com.sparklep.preorder.domain.order.service;


import com.sparklep.preorder.domain.order.dto.OrderRequestDto;
import com.sparklep.preorder.domain.order.dto.OrderResponseDto;
import com.sparklep.preorder.domain.order.entity.OrderDetail;
import com.sparklep.preorder.domain.order.entity.OrderStatusEnum;
import com.sparklep.preorder.domain.order.entity.Orders;
import com.sparklep.preorder.domain.order.repository.OrderDetailRepository;
import com.sparklep.preorder.domain.order.repository.OrdersRepository;
import com.sparklep.preorder.domain.product.entity.Product;
import com.sparklep.preorder.domain.product.entity.ProductStatusEnum;
import com.sparklep.preorder.domain.product.repository.ProductRepository;
import com.sparklep.preorder.domain.user.entity.User;
import com.sparklep.preorder.domain.wishlist.entity.WishlistProduct;
import com.sparklep.preorder.domain.wishlist.repository.WishlistProductRepository;
import com.sparklep.preorder.global.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final WishlistProductRepository wishlistProductRepository;
    private final EncryptUtil encryptUtil;


    @Transactional(readOnly = true)
    public List<OrderResponseDto> getMyOrders(User user) {
        List<OrderResponseDto> getMyOrders = new ArrayList<>();
        List<Orders> ordersList = ordersRepository.findAllByUserId(user.getId());
        for (Orders orders: ordersList) {
            String address = encryptUtil.decrypt(orders.getAddress());
            String phoneNumber = encryptUtil.decrypt(orders.getPhoneNumber());

            getMyOrders.add(OrderResponseDto.of(orders,address,phoneNumber));
        }
        return getMyOrders;
    }

    @Transactional
    public String createOrdersFromWishlist(List<OrderRequestDto> orderRequestDtoList, User user) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        int totalPrice = 0;
        for (OrderRequestDto orderRequestDto : orderRequestDtoList) {
            Product product = productRepository.findById(orderRequestDto.getProductNo()).orElseThrow(
                    () -> new NullPointerException("해당 상품을 찾을 수 없습니다."));
            if (product.getStock() < orderRequestDto.getCount()) {
                throw new IllegalArgumentException("해당 상품의 수량을 초과하였습니다.");
            } else if (product.getStatus() != ProductStatusEnum.SELLING) {
                throw new IllegalArgumentException("해당 상품은 " + product.getStatus() + "입니다.");
            }
            //주문 불가 조건 통과
            //장바구니 상태 변경
            WishlistProduct wishlistProduct = wishlistProductRepository.findById(orderRequestDto.getWishlistProductId()).orElseThrow(
                    () -> new NullPointerException("해당 상품을 장바구니에서 찾을 수 없습니다."));
            if (wishlistProduct.isDeleted()){
                throw new IllegalArgumentException("해당 장바구니는 존재하지 않습니다.");
            }
            wishlistProduct.setDeleted();
            //제품 가격 * 개수
            totalPrice += product.getPrice() * orderRequestDto.getCount();
            OrderDetail orderDetail = OrderDetail.builder()
                    .productNo(wishlistProduct.getProductNo())
                    .count(orderRequestDto.getCount())
                    .price(product.getPrice())
                    .productTitle(product.getTitle())
                    .summary(product.getSummary())
                    .build();
            orderDetailRepository.save(orderDetail);
            orderDetails.add(orderDetail);
            //상품 개수 변동
            product.sellProduct(orderRequestDto.getCount());
            if (product.getStock() == 0) {
                product.updateProductStatus(ProductStatusEnum.OUT_OF_STOCK);
            }
        }
        Orders orders = Orders.builder()
                .orderStatusEnum(OrderStatusEnum.ORDER_AWAITING_SHIPMENT)
                .totalPrice(totalPrice)
                .userId(user.getId())
                .orderDetail(orderDetails)
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
        ordersRepository.save(orders);
        //재고 0 이면 제품 상태값 변경
        return "success createOrdersFromWishlist";
    }

    @Transactional
    public String cancelOrder(Long ordersId, User user) {
        Orders orders = ordersRepository.findById(ordersId).orElseThrow(
                () -> new NullPointerException("주문 정보를 찾을 수 없습니다. 관리자에게 문의해주세요."));
        //주문 취소불가 조건
        if (orders.getUserId() != user.getId()) {
            throw new IllegalArgumentException("주문 유저정보와 다릅니다.");
        } else if (orders.getOrderStatusEnum() != OrderStatusEnum.ORDER_AWAITING_SHIPMENT) {
            throw new IllegalArgumentException("주문 유저정보와 다릅니다.");
        }
        for (OrderDetail orderDetail : orders.getOrderDetail()) {
            Product product = productRepository.findByProductNo(orderDetail.getProductNo()).orElseThrow(
                    () -> new NullPointerException("해당 상품을 찾을 수 없습니다. "));
            product.restockProduct(orderDetail.getCount());
        }
        orders.updateOrderStatus(OrderStatusEnum.ORDER_CANCELLED);
        return "cancelOrder success";
    }

    @Transactional
    public String returnOrder(Long ordersId, User user) {
        Orders orders = ordersRepository.findById(ordersId).orElseThrow(
                () -> new NullPointerException("주문 정보를 찾을 수 없습니다. 관리자에게 문의해주세요."));
        //반품 불가 조건
        if (orders.getUserId() != user.getId()) {
            throw new IllegalArgumentException("주문 유저정보와 다릅니다.");
        } else if (orders.getOrderStatusEnum() != OrderStatusEnum.ORDER_DELIVERED) {
            throw new IllegalArgumentException("배송 완료 후 반품 신청이 가능합니다.");
        }
        LocalDate ordersDate = orders.getModifiedAt().toLocalDate();
        //당일, Day+1일까지 유효
        if (LocalDate.now().equals(ordersDate) || LocalDate.now().plusDays(1).equals(ordersDate)) {
            orders.updateOrderStatus(OrderStatusEnum.RETURN_IN_PROGRESS);
        } else {
            throw new IllegalArgumentException("반품 정책 기간 초과로 인해 반품할 수 없습니다.");
        }
        return "returnOrder success";
    }

    @Transactional
    public void updateOrdersStatus() {
        List<Orders> orderAwaitingShipment = ordersRepository.findAllByOrderStatusEnumAndCreatedAtBefore(OrderStatusEnum.ORDER_AWAITING_SHIPMENT, LocalDateTime.now().minusDays(1));
        for (Orders order : orderAwaitingShipment) {
            order.updateOrderStatus(OrderStatusEnum.ORDER_IN_TRANSIT);
        }
        List<Orders> orderInTransit = ordersRepository.findAllByOrderStatusEnumAndCreatedAtBefore(OrderStatusEnum.ORDER_IN_TRANSIT, LocalDateTime.now().minusDays(2));
        for (Orders order : orderInTransit) {
            order.updateOrderStatus(OrderStatusEnum.ORDER_DELIVERED);
        }
        List<Orders> returnInProgress = ordersRepository.findAllByOrderStatusEnumAndModifiedAtBefore(OrderStatusEnum.RETURN_IN_PROGRESS, LocalDateTime.now().minusDays(1));
        for (Orders order : returnInProgress) {
            for (OrderDetail orderDetail : order.getOrderDetail()) {
                Product product = productRepository.findByProductNo(orderDetail.getProductNo()).orElseThrow(
                        () -> new NullPointerException("해당 상품을 찾을 수 없습니다. 오류 일자: " + LocalDateTime.now()));
                product.restockProduct(orderDetail.getCount());
            }
            order.updateOrderStatus(OrderStatusEnum.RETURN_COMPLETED);
        }
    }
}
