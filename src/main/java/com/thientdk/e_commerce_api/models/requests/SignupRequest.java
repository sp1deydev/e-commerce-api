package com.thientdk.e_commerce_api.models.requests;

import lombok.Data;

@Data
public class SignupRequest {

    private String username;
    private String password;
    private String email;
}
