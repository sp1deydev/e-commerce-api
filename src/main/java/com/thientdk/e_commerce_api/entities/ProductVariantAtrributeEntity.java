package com.thientdk.e_commerce_api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_variants_attributes")
public class ProductVariantAtrributeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "product_variant_id")
    private String productVariantId;

    @Column(name = "attribute_value_id")
    private String attributeValueId;

}
