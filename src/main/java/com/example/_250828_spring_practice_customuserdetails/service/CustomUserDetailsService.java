package com.example._250828_spring_practice_customuserdetails.service;

import com.example._250828_spring_practice_customuserdetails.model.User;
import com.example._250828_spring_practice_customuserdetails.repository.UserRepository;
import com.example._250828_spring_practice_customuserdetails.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("\uD83D\uDD0D 사용자 조회 시도: {}", username);
        User user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> {
                    log.warn("❌ 사용자를 찾을 수 없음: {}", username);
                    return new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
                });
        log.debug("✅ 사용자 조회 성공: {} (ID: {})", user.getUsername(), user.getId());
        CustomUserDetails userDetails = new CustomUserDetails(user);
        logUserStatus(user);
        return userDetails;
    }
    private void logUserStatus(User user) {
        log.debug("\uD83D\uDCCA 사용자 상태 확인:");
        log.debug("  - Username: {}", user.getUsername());
        log.debug("  - Email: {}", user.getEmail());
        log.debug("  - Enabled: {}", user.getEnabled());
        log.debug("  - AccountNonExpired: {}", user.getAccountNonExpired());
        log.debug("  - AccountNonLocked: {}", user.getAccountNonLocked());
        log.debug("  - CredentialsNonExpired: {}", user.getCredentialsNonExpired());
        log.debug("  - Roles: {}", user.getRoles());
    }
}
