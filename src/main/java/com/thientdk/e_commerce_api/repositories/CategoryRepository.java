package com.thientdk.e_commerce_api.repositories;

import com.thientdk.e_commerce_api.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

    List<CategoryEntity> findByParentIsNull();

}
