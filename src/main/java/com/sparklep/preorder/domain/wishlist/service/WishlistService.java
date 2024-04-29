package com.sparklep.preorder.domain.wishlist.service;

import com.sparklep.preorder.domain.product.entity.Product;
import com.sparklep.preorder.domain.product.entity.ProductStatusEnum;
import com.sparklep.preorder.domain.product.repository.ProductRepository;
import com.sparklep.preorder.domain.user.entity.User;
import com.sparklep.preorder.domain.wishlist.dto.WishlistAddRequestDto;
import com.sparklep.preorder.domain.wishlist.dto.WishlistResponseDto;
import com.sparklep.preorder.domain.wishlist.dto.WishlistUpdateRequestDto;
import com.sparklep.preorder.domain.wishlist.entity.Wishlist;
import com.sparklep.preorder.domain.wishlist.entity.WishlistProduct;
import com.sparklep.preorder.domain.wishlist.repository.WishlistProductRepository;
import com.sparklep.preorder.domain.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistProductRepository wishlistProductRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<WishlistResponseDto> getWishlist(User user) {
        Wishlist wishlist = wishlistRepository.findById(user.getWishlistId()).orElseThrow(
                () -> new NullPointerException("해당 장바구니를 찾을 수 없습니다. 관리자에게 문의해주세요."));
        List<WishlistProduct> wishlistProducts = wishlist.getWishlistProduct();
        // modifiedAt 필드 기준으로 정렬
        Collections.sort(wishlistProducts, (product1, product2) -> product2.getModifiedAt().compareTo(product1.getModifiedAt()));
        List<WishlistResponseDto> wishlistResponseDto = new ArrayList<>();
        for (WishlistProduct checkWishlistProduct : wishlistProducts) {
            //장바구니에서 상품이 삭제 여부, 미판매 상품이라도 관심 상품으로 상태 확인 필요함.
            if (!checkWishlistProduct.isDeleted()) {
                Product product = productRepository.findByProductNo(checkWishlistProduct.getProductNo()).get();
                wishlistResponseDto.add(WishlistResponseDto.of(checkWishlistProduct, product));
            }
        }
        return wishlistResponseDto;
    }
    @Transactional
    public String addWishlist(WishlistAddRequestDto wishlistAddRequestDto, User user) {
        Wishlist wishlist = wishlistRepository.findById(user.getWishlistId()).orElseThrow(
                () -> new NullPointerException("해당 장바구니를 찾을 수 없습니다. 관리자에게 문의해주세요."));
        Product product = productRepository.findById(wishlistAddRequestDto.getProductNo()).orElseThrow(
                ()-> new NullPointerException("해당 상품을 찾을 수 없습니다."));
        if (product.getStatus() != ProductStatusEnum.SELLING) {
            throw new IllegalArgumentException("해당 상품은 " + product.getStatus() + "입니다.");
        } else if (product.getStock() < wishlistAddRequestDto.getCount()){
            throw new IllegalArgumentException("해당 상품의 수량을 초과하였습니다. 최대 수량 " + product.getStock());
        }
        Optional<WishlistProduct> findWishlistProduct = wishlistProductRepository.findWishlistProductByWishlistIdAndProductNo(wishlist.getId(),product.getProductNo());
        WishlistProduct wishlistProduct;
        //findWishlistProduct에 존재하고 삭제 여부 확인
        if (findWishlistProduct.isPresent() && !findWishlistProduct.get().isDeleted()){
            findWishlistProduct.get().updateCount(wishlistAddRequestDto.getCount()); //modifiedAt 변동 체크 필요
        } else {
            wishlistProduct = WishlistProduct.builder()
                    .productNo(product.getProductNo())
                    .count(wishlistAddRequestDto.getCount())
                    .wishlist(wishlist)
                    .build();
            wishlistProductRepository.save(wishlistProduct);
        }
        return "addWishlist success";
    }

    @Transactional
    public String updateWishlist(WishlistUpdateRequestDto wishlistUpdateRequestDto, User user) {
        WishlistProduct wishlistProduct = wishlistProductRepository.findById(wishlistUpdateRequestDto.getWishlistProductId()).orElseThrow(
                () -> new NullPointerException("해당 상품을 장바구니에서 찾을 수 없습니다."));
        Product product = productRepository.findById(wishlistProduct.getProductNo()).orElseThrow(
                ()-> new NullPointerException("해당 상품을 찾을 수 없습니다."));
        if (product.getStatus() != ProductStatusEnum.SELLING) {
            throw new IllegalArgumentException("해당 상품은 " + product.getStatus() + "입니다.");
        } else if (product.getStock() < wishlistUpdateRequestDto.getCount()){
            throw new IllegalArgumentException("해당 상품의 수량을 초과하였습니다. 최대 수량 " + product.getStock());
        }
        wishlistProduct.updateCount(wishlistUpdateRequestDto.getCount());
    return "updateWishlist success";
    }
}
