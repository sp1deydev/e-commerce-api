package com.thientdk.e_commerce_api.repositories;

import com.thientdk.e_commerce_api.entities.AttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValueEntity, String> {
    void deleteByAttributeId(String attributeId);

    List<AttributeValueEntity> findAttributeValueEntitiesByAttributeId(String id);
}
