package com.sparklep.preorder.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*?[a-zA-Z])(?=.*?\\d)(?=.*?[~!@#$%^&*()_+=\\-`]).{8,30}",
            message = "비밀번호는 8자 이상, 30자 이하여야 합니다. 숫자, 문자, 특수문자 각 하나씩 포함되어야 합니다.")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")//@Email ""빈문자열 허용 NotBlank 추가
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;
}
