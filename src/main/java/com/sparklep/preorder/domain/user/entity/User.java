package com.sparklep.preorder.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    //이메일 유니크 체크 요구사항에서 본것 같음
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "wishlistId", nullable = false)
    private Long wishlistId;

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateProfile(String newAddress, String newPhoneNumber) {
        this.address = newAddress;
        this.phoneNumber = newPhoneNumber;
    }
}
