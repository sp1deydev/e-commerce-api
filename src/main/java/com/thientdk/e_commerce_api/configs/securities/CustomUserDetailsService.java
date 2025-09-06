package com.thientdk.e_commerce_api.configs.securities;

import com.thientdk.e_commerce_api.entities.UserEntity;
import com.thientdk.e_commerce_api.enums.Role;
import com.thientdk.e_commerce_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        Role role = Role.getRole(entity.getRole());

        Set<GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("ROLE_" + role.getRole())
        );

        return new CustomUserDetails(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                authorities
        );
    }

}
