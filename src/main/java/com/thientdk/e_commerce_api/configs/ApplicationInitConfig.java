package com.thientdk.e_commerce_api.configs;

import com.thientdk.e_commerce_api.entities.UserEntity;
import com.thientdk.e_commerce_api.enums.Role;
import com.thientdk.e_commerce_api.enums.UserStatus;
import com.thientdk.e_commerce_api.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .active(UserStatus.ACTIVE.getValue())
                        .role(Role.ADMIN.getId())
                        .build();
                userRepository.save(user);
                log.warn("Admin account has been created with default password is 'admin'. Please change it ");
            }
        };
    }
}
