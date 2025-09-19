package com.thientdk.e_commerce_api.entities;

import com.thientdk.e_commerce_api.entities.abstractions.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItemEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "order_id")
    private String order_id;

    @Column(name = "product_variant_id")
    private String product_variant_id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Number price;
}
