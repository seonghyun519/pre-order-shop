package com.sparklep.preorder.domain.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WishlistAddRequestDto {
    @NotBlank
    private Long productNo;
    @NotBlank
    private int count;
}
