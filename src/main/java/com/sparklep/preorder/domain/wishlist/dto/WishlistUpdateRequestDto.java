package com.sparklep.preorder.domain.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistUpdateRequestDto {
    @NotBlank
    long wishlistProductId;
    @NotBlank
    long productNo;
    @NotBlank
    int count;
}
