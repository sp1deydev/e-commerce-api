package com.thientdk.e_commerce_api.entities;

import com.thientdk.e_commerce_api.entities.abstractions.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_variants")
public class ProductVariantEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id")
    private String product_id;

    @Column(name = "sku")
    private String sku;

    @Column(name = "stock_quantity")
    private Integer stock_quantity;

    @Column(name = "base_price")
    private Number base_price;

    @Column(name = "image_url")
    private String image_url;
}
