package com.sparklep.preorder.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

//    @NotBlank
//    @Email
//    private String email;
    //일단 유저네임으로 하고 추후 이메일 고려(이메일 인증 방식에 따라 달라질것 같음)
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
