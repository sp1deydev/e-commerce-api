package com.thientdk.e_commerce_api.services;

import com.thientdk.e_commerce_api.aop.exceptions.ApiException;
import com.thientdk.e_commerce_api.aop.exceptions.ErrorCode;
import com.thientdk.e_commerce_api.configs.securities.JwtUtils;
import com.thientdk.e_commerce_api.entities.UserEntity;
import com.thientdk.e_commerce_api.enums.Role;
import com.thientdk.e_commerce_api.enums.UserStatus;
import com.thientdk.e_commerce_api.models.requests.LoginRequest;
import com.thientdk.e_commerce_api.models.requests.SignupRequest;
import com.thientdk.e_commerce_api.models.responses.LoginResponse;
import com.thientdk.e_commerce_api.models.responses.UserResponse;
import com.thientdk.e_commerce_api.repositories.UserRepository;
import com.thientdk.e_commerce_api.utils.DataUtils;
import com.thientdk.e_commerce_api.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final LoginAttemptService loginAttemptService;


    @Value("${jwt.expiration-time}")
    private long jwtExpiration;


    public LoginResponse login(LoginRequest request) {
        //validate request
        validateLoginRequest(request);

        log.info("[login] - login START with username: {}", request.getUsername());

        try {
            UserEntity userEntity = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> {
                        log.info("[login] - user not found ERROR");
                        loginAttemptService.saveLoginAttempt(null, request.getUsername(), false, "User not found");
                        return new ApiException(ErrorCode.BAD_REQUEST, "User not found!");
                    });

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            if (userEntity.getActive() != 1) {
                loginAttemptService.saveLoginAttempt(userEntity.getId(), request.getUsername(), false, "User is disabled");
                log.info("[login] - user is disabled ERROR with username: {}", request.getUsername());
                throw new ApiException(ErrorCode.BAD_REQUEST, "User is disabled!");
            }

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("id", userEntity.getId());
            extraClaims.put("authorities", userDetails.getAuthorities());
            String jwt = jwtUtils.generateToken(extraClaims, userDetails);

            loginAttemptService.saveLoginAttempt(userEntity.getId(), request.getUsername(), true, null);

            log.info("[login] - login DONE");

            return LoginResponse.builder()
                    .accessToken(jwt)
                    .tokenType("Bearer")
                    .expiresIn(jwtExpiration)
                    .build();

        } catch (Exception e) {
            loginAttemptService.saveLoginAttempt(null, request.getUsername(), false, e.getMessage());
            throw e;
        }
    }

    public UserResponse signup(SignupRequest request) {

        //validate request
        this.validateSignupRequest(request);

        log.info("[signup] - Signup START with username: {}", request.getUsername());

        Optional<UserEntity> entity = userRepository.findByUsername(request.getUsername());

        if (entity.isPresent()) {
            log.info("[signup] - User is existed ERROR with username: {}", request.getUsername());
            throw new ApiException(ErrorCode.BAD_REQUEST, "User is existed!");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setActive(UserStatus.ACTIVE.getValue());
        newUser.setRole(Role.USER.getId());
        UserEntity savedUser = userRepository.save(newUser);

        UserResponse response = UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .active(savedUser.getActive())
                .role(Role.USER.getRole())
                .createdAt(savedUser.getCreatedAt())
                .createdBy(savedUser.getCreatedBy())
                .updatedAt(savedUser.getUpdatedAt())
                .updatedBy(savedUser.getUpdatedBy())
                .build();

        log.info("[signup] - Signup DONE");
        return response;
    }

    public void validateLoginRequest(LoginRequest request) {

        if (StringUtil.isNullOrEmpty(request.getUsername())) {
            log.info("[validateLoginRequest] - username is invalid ERROR");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Username is invalid!");
        }
        if (StringUtil.isNullOrEmpty(request.getPassword()) || !DataUtils.isSecurePassword(request.getPassword())) {
            log.info("[validateLoginRequest] - password is invalid ERROR");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Password is invalid!");
        }
    }

    public void validateSignupRequest(SignupRequest request) {

        if (StringUtil.isNullOrEmpty(request.getUsername())) {
            log.info("[validateSignupRequest] - username is invalid ERROR");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Username is invalid!");
        }
        if (StringUtil.isNullOrEmpty(request.getPassword()) || !DataUtils.isSecurePassword(request.getPassword())) {
            log.info("[validateSignupRequest] - password is invalid ERROR");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Password is invalid!");
        }
        if (StringUtil.isNullOrEmpty(request.getEmail()) || !DataUtils.isEmail(request.getEmail())) {
            log.info("[validateSignupRequest] - email is invalid ERROR");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Email is invalid!");
        }
    }


}
