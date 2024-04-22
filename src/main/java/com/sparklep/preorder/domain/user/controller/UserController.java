package com.sparklep.preorder.domain.user.controller;

import com.sparklep.preorder.domain.user.dto.*;
import com.sparklep.preorder.domain.user.service.UserService;
import com.sparklep.preorder.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public String signup(@RequestBody @Validated SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }
    @PostMapping("login")
    public String login(@RequestBody LoginRequestDto loginRequestDto) {
        return "login success";
    }
    @PutMapping("/password")
    public String updatePassword(@RequestBody @Validated UpdatePasswordRequestDto updatePasswordRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.updatePassword(updatePasswordRequestDto, userDetails.getUser());
    }
    @PutMapping("/profile")
    public String updateProfile(@RequestBody @Validated UpdateProfileRequestDto updateProfileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.updateProfile(updateProfileRequestDto, userDetails.getUser());
    }
    @GetMapping("/info")
    public MyPageResponseDto geMypage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getMyPage(userDetails.getUser());
    }
}
