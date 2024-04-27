package com.sparklep.preorder.domain.wishlist.controller;

import com.sparklep.preorder.domain.wishlist.dto.WishlistAddRequestDto;
import com.sparklep.preorder.domain.wishlist.dto.WishlistResponseDto;
import com.sparklep.preorder.domain.wishlist.dto.WishlistUpdateRequestDto;
import com.sparklep.preorder.domain.wishlist.service.WishlistService;
import com.sparklep.preorder.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping
    public String addWishlist(@RequestBody @Validated WishlistAddRequestDto wishlistAddRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return wishlistService.addWishlist(wishlistAddRequestDto, userDetails.getUser());
    }
    @GetMapping
    public List<WishlistResponseDto> getWishlist(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return wishlistService.getWishlist(userDetails.getUser());
    }
    @PutMapping
    public String updateWishlist(@RequestBody @Validated WishlistUpdateRequestDto wishlistUpdateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return wishlistService.updateWishlist(wishlistUpdateRequestDto, userDetails.getUser());
    }
}
