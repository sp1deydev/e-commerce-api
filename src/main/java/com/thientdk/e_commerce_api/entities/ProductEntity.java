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
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private String category_id;

    @Column(name = "description")
    private String description;

    @Column(name = "base_price")
    private Number base_price;
}
