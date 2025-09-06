package com.thientdk.e_commerce_api.mappers;

import com.thientdk.e_commerce_api.entities.UserEntity;
import com.thientdk.e_commerce_api.enums.Role;
import com.thientdk.e_commerce_api.models.responses.UserResponse;

public class UserMapper {
    public static UserResponse fromUserEntityToUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .active(userEntity.getActive())
                .role(Role.getRole(userEntity.getRole()).getRole())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .createdBy(userEntity.getCreatedBy())
                .updatedBy(userEntity.getUpdatedBy())
                .build();
    }
}
