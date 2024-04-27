package com.sparklep.preorder.domain.wishlist.dto;

import com.sparklep.preorder.domain.product.entity.Product;
import com.sparklep.preorder.domain.product.entity.ProductStatusEnum;
import com.sparklep.preorder.domain.wishlist.entity.WishlistProduct;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WishlistResponseDto {
    long wishlistProductId;
    long productNo;
    int count;
    private LocalDateTime modifiedAt;
    String productTitle;
    long totalPrice;
    String summary;
    ProductStatusEnum productStatusEnum;

    public static WishlistResponseDto of(WishlistProduct wishlistProduct, Product product) {
        return WishlistResponseDto.builder()
                .wishlistProductId(wishlistProduct.getId())
                .productNo(wishlistProduct.getProductNo())
                .count(wishlistProduct.getCount())
                .modifiedAt(wishlistProduct.getModifiedAt())
                .productTitle(product.getTitle())
                .totalPrice(product.getPrice()*wishlistProduct.getCount())
                .summary(product.getSummary())
                .productStatusEnum(product.getStatus())
                .build();
    }
}
