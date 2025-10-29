package com.thientdk.e_commerce_api.entities;

import com.thientdk.e_commerce_api.entities.abstractions.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "parent_id", insertable = false, updatable = false)
    private String parentId;

    @Column(name = "description")
    private String description;
}
