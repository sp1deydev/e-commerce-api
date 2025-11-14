package com.thientdk.e_commerce_api.repositories;

import com.thientdk.e_commerce_api.entities.ProductVariantAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantAttributeRepository extends JpaRepository<ProductVariantAttributeEntity, String> {
}
