package com.thientdk.e_commerce_api.models.requests;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;

}
