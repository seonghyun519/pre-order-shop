package com.sparklep.preorder.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
//    @NotBlank
//    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*?[a-zA-Z])(?=.*?\\d)(?=.*?[~!@#$%^&*()_+=\\-`]).{8,30}",
            message = "비밀번호는 8자 이상, 30자 이하여야 합니다. 숫자, 문자, 특수문자 각 하나씩 포함되어야 합니다.")
//    @Pattern(regexp = "(?=.*?[a-zA-Z]{2,})(?=.*?\\d)(?=.*?[~!@#$%^&*()_+=\\-]).{8,30}",
//            message = "애플식 로그인")
    private String password;
}
