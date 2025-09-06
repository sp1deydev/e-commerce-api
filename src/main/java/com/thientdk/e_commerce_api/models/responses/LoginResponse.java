package com.thientdk.e_commerce_api.models.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
}
