package com.thientdk.e_commerce_api.models.responses;

import com.thientdk.e_commerce_api.entities.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriesResponse {
    private String id;
    private String name;
    private String slug;
    private String parentId;
    private String description;
    private List<CategoryEntity> children;
}