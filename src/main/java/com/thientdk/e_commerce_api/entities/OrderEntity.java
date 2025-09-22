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
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "total_amount")
    private Number totalAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "base_price")
    private Number basePrice;
}
