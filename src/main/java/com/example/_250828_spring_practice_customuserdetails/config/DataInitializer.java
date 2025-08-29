package com.example._250828_spring_practice_customuserdetails.config;

import com.example._250828_spring_practice_customuserdetails.model.Role;
import com.example._250828_spring_practice_customuserdetails.model.User;
import com.example._250828_spring_practice_customuserdetails.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0) {
            log.info("\uD83D\uDE80 테스트용 사용자 데이터 초기화 시작");
            createTestUsers();
            log.info("✅ 테스트용 사용자 데이터 초기화 완료");
            log.info("\uD83D\uDCCA 총 사용자 수: {}", userRepository.count());
        } else {
            log.info("\uD83D\uDCCB 기존 사용자 데이터 존재, 초기화 건너뜀");
        }
    }
    private void createTestUsers() {
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .email("admin@example.com")
                .fullName("관리자")
                .roles(Set.of(Role.ADMIN, Role.MANAGER, Role.USER))
                .enabled(true)
                .accountNonLocked(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        userRepository.save(admin);
        log.info("\uD83D\uDC68\u200D\uD83D\uDCBB 관리자 계정 생성: {}", admin.getUsername());
        User manager = User.builder()
                .username("manager")
                .password(passwordEncoder.encode("manager123"))
                .email("manager@example.com")
                .fullName("매니저")
                .roles(Set.of(Role.MANAGER, Role.USER))
                .enabled(true)
                .accountNonLocked(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        userRepository.save(manager);
        log.info("👨‍💼 매니저 계정 생성: {}", manager.getUsername());
        User lockedUser = User.builder()
                .username("disabled")
                .password(passwordEncoder.encode("disabled123"))
                .email("disabled@example.com")
                .fullName("잠긴 사용자")
                .roles(Set.of(Role.USER))
                .enabled(false)
                .accountNonLocked(true)
                .accountNonLocked(false)
                .credentialsNonExpired(true)
                .build();

        userRepository.save(lockedUser);
        log.info("❌ 비활성화 계정 생성: {}", lockedUser.getUsername());
    }
}
