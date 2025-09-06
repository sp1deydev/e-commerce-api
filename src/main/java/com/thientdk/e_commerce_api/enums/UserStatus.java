package com.thientdk.e_commerce_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE(1, "ACTIVE"),
    DISABLE(0, "DISABLE");

    private final Integer value;
    private final String status;
}
