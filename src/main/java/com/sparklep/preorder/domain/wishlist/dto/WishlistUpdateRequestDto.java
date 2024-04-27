package com.sparklep.preorder.domain.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistUpdateRequestDto {
    @NotNull
    long wishlistProductId;
    @NotNull
    int count;
}
