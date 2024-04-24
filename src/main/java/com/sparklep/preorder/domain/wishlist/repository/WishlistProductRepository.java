package com.sparklep.preorder.domain.wishlist.repository;

import com.sparklep.preorder.domain.wishlist.entity.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistProductRepository extends JpaRepository<WishlistProduct, Long> {
    Optional<WishlistProduct> findWishlistProductByWishlistIdAndProductNo(long wishlistId, long productNo);
}