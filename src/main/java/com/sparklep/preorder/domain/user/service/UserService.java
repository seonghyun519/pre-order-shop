package com.sparklep.preorder.domain.user.service;

import com.sparklep.preorder.domain.user.dto.SignupRequestDto;
import com.sparklep.preorder.domain.user.entity.User;
import com.sparklep.preorder.domain.user.entity.UserRoleEnum;
import com.sparklep.preorder.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public String signup(SignupRequestDto signupRequestDto) {
        Optional<User> findUsername = userRepository.findByUsername(signupRequestDto.getUsername());
        if (findUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        String username = signupRequestDto.getUsername();
        String encodePassword = passwordEncoder.encode(signupRequestDto.getPassword());
        String encodeEmail = passwordEncoder.encode(signupRequestDto.getEmail());
        String encodeName = passwordEncoder.encode(signupRequestDto.getName());
        String encodeAddress = passwordEncoder.encode(signupRequestDto.getAddress());
        String encodePhoneNumber = passwordEncoder.encode(signupRequestDto.getPhoneNumber());

        User user = User.builder()
                .username(username)
                .password(encodePassword)
                .email(encodeEmail)
                .name(encodeName)
                .address(encodeAddress)
                .phoneNumber(encodePhoneNumber)
                .role(UserRoleEnum.USER)
                .build();
        userRepository.save(user);

        return signupRequestDto.getUsername() + "님 가입완료";
    }
}
