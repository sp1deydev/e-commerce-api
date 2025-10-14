package com.thientdk.e_commerce_api.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValueDto {
    private String id;
    private String attributeId;
    private String value;
}
