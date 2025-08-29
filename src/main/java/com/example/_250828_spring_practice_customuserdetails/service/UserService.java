package com.example._250828_spring_practice_customuserdetails.service;

import com.example._250828_spring_practice_customuserdetails.dto.UserRegistrationDto;
import com.example._250828_spring_practice_customuserdetails.model.Role;
import com.example._250828_spring_practice_customuserdetails.model.User;
import com.example._250828_spring_practice_customuserdetails.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User registerUser(UserRegistrationDto registrationDto) {
        log.info("\uD83D\uDC64 새 사용자 등록 시도: {}", registrationDto.getUsername());
        validateUserUniqueness(registrationDto);
        User user = User.builder()
                .username(registrationDto.getUsername())
                .email(registrationDto.getEmail())
                .fullName(registrationDto.getFullName())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .roles(Set.of(Role.USER))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        User savedUser = userRepository.save(user);
        log.info("✅ 사용자 등록 완료: {} (ID: {})", savedUser.getUsername(), savedUser.getId());
        return savedUser;
    }
    private void validateUserUniqueness(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsernameIgnoreCase(registrationDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + registrationDto.getUsername());
        };
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new IllegalArgumentException(("이미 등록된 이메일입니다: " + registrationDto.getEmail()));
        }
    }
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }
    @Transactional(readOnly = true)
    public List<User> findAllEnabledUsers() {
        return userRepository.findAllEnabledUsers();
    }
    public void updateAccountStatus(Long userId, boolean enabled, boolean accountNonLocked) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
        user.setEnabled(enabled);
        user.setAccountNonLocked(accountNonLocked);
        userRepository.save(user);
        log.info("\uD83D\uDD27 사용자 상태 변경: {} - Enabled: {}, NonLocked: {}", user.getUsername(), enabled, accountNonLocked);
    }
    public void addRoleToUser(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
        user.addRole(role);
        userRepository.save(user);
        log.info("\uD83D\uDEE1\uFE0F 사용자 권한 추가: {} - 추가된 권한: {}", user.getUsername(), role);
    }
    public void updateLastLoginTime(String username) {
        userRepository.findByUsernameIgnoreCase(username)
                .ifPresent(user -> {
                    user.updateLastLoginAt();
                    userRepository.save(user);
                    log.debug("⏰ 마지막 로그인 시간 업데이트: {}", username);
                });
    }
}
