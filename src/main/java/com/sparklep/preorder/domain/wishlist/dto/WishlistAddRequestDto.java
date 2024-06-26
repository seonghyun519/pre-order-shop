package com.sparklep.preorder.domain.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistAddRequestDto {
    @NotNull
    private long productNo;
    @NotNull
    private int count;
}
