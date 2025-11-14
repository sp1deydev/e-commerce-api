package com.thientdk.e_commerce_api.models.requests;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ProductRequest {
    private String id;
    private String name;
    private String categoryId;
    private String description;
    private Long basePrice;
    private MultipartFile image;
}
