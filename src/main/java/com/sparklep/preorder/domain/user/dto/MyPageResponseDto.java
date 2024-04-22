package com.sparklep.preorder.domain.user.dto;

import com.sparklep.preorder.domain.user.entity.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResponseDto {

    private String name;

    private String email;

    private String address;

    private String phoneNumber;

}
