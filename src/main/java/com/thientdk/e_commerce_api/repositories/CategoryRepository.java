package com.thientdk.e_commerce_api.repositories;

import com.thientdk.e_commerce_api.entities.CategoryEntity;
import com.thientdk.e_commerce_api.models.dtos.CategoriesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

    int deleteAllByParentId(String parentId);

    @Query(value = "SELECT c.*, " +
            "  (SELECT json_agg(ca)::text FROM categories ca WHERE ca.parent_id = c.id) AS children " +
            "FROM categories c WHERE c.parent_id IS NULL",
            nativeQuery = true)
    List<CategoriesDto> getListCategories();

}
