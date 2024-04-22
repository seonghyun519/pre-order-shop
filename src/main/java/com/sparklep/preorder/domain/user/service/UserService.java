package com.sparklep.preorder.domain.user.service;

import com.sparklep.preorder.domain.user.dto.SignupRequestDto;
import com.sparklep.preorder.domain.user.dto.UpdatePasswordDto;
import com.sparklep.preorder.domain.user.entity.User;
import com.sparklep.preorder.domain.user.entity.UserRoleEnum;
import com.sparklep.preorder.domain.user.repository.UserRepository;
import com.sparklep.preorder.global.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptUtil encryptUtil;

    private final UserService userService;

    public String signup(SignupRequestDto signupRequestDto) {
        Optional<User> findUserEmail = userRepository.findByEmail(signupRequestDto.getEmail());
        if (findUserEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        String userEmail = signupRequestDto.getEmail();
        String encodePassword = passwordEncoder.encode(signupRequestDto.getPassword());
        String encodeName = encryptUtil.encrypt(signupRequestDto.getName());
        String encodeAddress = encryptUtil.encrypt(signupRequestDto.getAddress());
        String encodePhoneNumber = encryptUtil.encrypt(signupRequestDto.getPhoneNumber());

        User user = User.builder()
                .email(userEmail)
                .password(encodePassword)
                .name(encodeName)
                .address(encodeAddress)
                .phoneNumber(encodePhoneNumber)
                .role(UserRoleEnum.USER)
                .build();
        userRepository.save(user);

        return signupRequestDto.getEmail() + "님 가입완료";
    }
}
