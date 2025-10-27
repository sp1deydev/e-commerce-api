package com.thientdk.e_commerce_api.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private String id;
    private String name;
    private String slug;
    private String parentId;
    private String description;
}
