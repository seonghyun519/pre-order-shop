package com.sparklep.preorder.domain.user.controller;

import com.sparklep.preorder.domain.user.dto.SignupRequestDto;
import com.sparklep.preorder.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public String signup(@RequestBody @Validated SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    //   /login
}
