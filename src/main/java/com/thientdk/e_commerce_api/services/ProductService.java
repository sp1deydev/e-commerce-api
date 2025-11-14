package com.thientdk.e_commerce_api.services;

import com.thientdk.e_commerce_api.entities.ProductEntity;
import com.thientdk.e_commerce_api.models.requests.ProductRequest;
import com.thientdk.e_commerce_api.models.responses.TextResponse;
import com.thientdk.e_commerce_api.repositories.ProductRepository;
import com.thientdk.e_commerce_api.repositories.ProductVariantAttributeRepository;
import com.thientdk.e_commerce_api.repositories.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantAttributeRepository productVariantAttributeRepository;

    @Value("${uploads.image-dir}")
    private String imagesUploadDir;

    @Value("${uploads.image-pre-url}")
    private String imagePreUrl;

    /*todo: check white list products, just get product is white list*/
    /*Products*/

    /*Insert*/
    public TextResponse insertProduct(MultipartFile file, ProductRequest request) throws IOException {
        log.info("[insertProduct] - START");

        /*Upload image*/
        try {

            Path folderPath = Paths.get(imagesUploadDir).normalize();
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String fileName = timestamp + "_" + file.getOriginalFilename();
            Path filePath = folderPath.resolve(fileName);

            Files.write(filePath, file.getBytes());
            String fileUrl = imagePreUrl + imagesUploadDir + fileName;

            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(request.getName());
            productEntity.setCategoryId(request.getCategoryId());
            productEntity.setBasePrice(request.getBasePrice());
            productEntity.setDescription(request.getDescription());
            productEntity.setImageUrl(fileUrl);

            productRepository.save(productEntity);

            log.info("[insertProduct] - END");
            return new TextResponse("Insert product successfully");
        }
        catch (Exception e) {
            log.info("[insertProduct] - ERROR: {}", e.getMessage());
            return new TextResponse("Insert product failed: " + e.getMessage());
        }
    }

    /*Update*/

    /*Get one*/

    /*Get list*/

    /*Delete*/


    /*Product Variants*/

    /*Insert update*/

    /*Get one*/

    /*Get list*/

    /*Delete*/

    /*Product Variant Attributes*/

    /*Insert update*/

    /*Delete*/
}
