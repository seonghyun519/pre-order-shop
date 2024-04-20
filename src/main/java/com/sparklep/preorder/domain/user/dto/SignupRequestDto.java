package com.sparklep.preorder.domain.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    private String username;

    private String password;

    @Email
    private String email;

    private String name;
    private String address;
    private String phoneNumber;

}
