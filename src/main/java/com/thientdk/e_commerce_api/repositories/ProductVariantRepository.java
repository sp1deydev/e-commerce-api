package com.thientdk.e_commerce_api.repositories;

import com.thientdk.e_commerce_api.entities.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, String> {
}
