package com.sparklep.preorder.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    @NotNull
    private long productNo;
    @NotNull
    private int count;
    @NotNull
    private long wishlistProductId;
}
