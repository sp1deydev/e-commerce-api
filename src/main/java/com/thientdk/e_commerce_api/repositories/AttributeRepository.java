package com.thientdk.e_commerce_api.repositories;

import com.thientdk.e_commerce_api.entities.AttributeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<AttributeEntity, String> {

    Page<AttributeEntity> findByNameContaining(String keySearch, Pageable pageable);
}
