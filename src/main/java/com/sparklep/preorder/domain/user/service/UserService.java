package com.sparklep.preorder.domain.user.service;

import com.sparklep.preorder.domain.user.dto.MyPageResponseDto;
import com.sparklep.preorder.domain.user.dto.SignupRequestDto;
import com.sparklep.preorder.domain.user.dto.UpdatePasswordRequestDto;
import com.sparklep.preorder.domain.user.dto.UpdateProfileRequestDto;
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

    @Transactional
    public String updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto, User user) {
        log.info("user.getPassword() =====" + user.getPassword());
        log.info("updatePasswordDto.getPassword() =====" + updatePasswordRequestDto.getPassword());
        log.info("updatePasswordDto.getNewPassword() =====" + updatePasswordRequestDto.getNewPassword());
        user = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("유저 정보를 찾지 못하였습니다."));

        log.info("boolean === " + passwordEncoder.matches(updatePasswordRequestDto.getPassword(), user.getPassword()));
        if (!passwordEncoder.matches(updatePasswordRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }
        user.updatePassword(passwordEncoder.encode(updatePasswordRequestDto.getNewPassword()));
        log.info("비밀번호 변경 성공 ======" + user.getPassword());
        return "updatePassword";
    }

    @Transactional
    public String updateProfile(UpdateProfileRequestDto updateProfileRequestDto, User user) {
        user = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("유저 정보를 찾지 못하였습니다."));
        String encodeAddress = encryptUtil.encrypt(updateProfileRequestDto.getAddress());
        String encodePhoneNumber = encryptUtil.encrypt(updateProfileRequestDto.getPhoneNumber());

        user.updateProfile(encodeAddress, encodePhoneNumber);
    return "updateProfile";
    }

    public MyPageResponseDto getMyPage(User user) {
        user = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("유저 정보를 찾지 못하였습니다."));
        String email = user.getEmail();
        String decodeName = encryptUtil.decrypt(user.getName());
        String decodeAddress =encryptUtil.decrypt(user.getAddress());
        String decodePhoneNumber=encryptUtil.decrypt(user.getPhoneNumber());
        return MyPageResponseDto.builder()
                .email(email)
                .address(decodeAddress)
                .name(decodeName)
                .phoneNumber(decodePhoneNumber)
                .build();
    }
}
