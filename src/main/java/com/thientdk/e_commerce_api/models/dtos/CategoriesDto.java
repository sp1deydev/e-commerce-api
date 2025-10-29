package com.thientdk.e_commerce_api.models.dtos;


public interface CategoriesDto {
    String getId();
    String getName();
    String getSlug();
    String getParentId();
    String getDescription();
    String getChildren();
}
