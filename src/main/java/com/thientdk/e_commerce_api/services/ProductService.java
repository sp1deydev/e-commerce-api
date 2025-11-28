package com.thientdk.e_commerce_api.services;

import com.thientdk.e_commerce_api.aop.exceptions.ApiException;
import com.thientdk.e_commerce_api.aop.exceptions.ErrorCode;
import com.thientdk.e_commerce_api.entities.ProductEntity;
import com.thientdk.e_commerce_api.models.requests.ProductRequest;
import com.thientdk.e_commerce_api.models.responses.TextResponse;
import com.thientdk.e_commerce_api.repositories.ProductRepository;
import com.thientdk.e_commerce_api.repositories.ProductVariantAttributeRepository;
import com.thientdk.e_commerce_api.repositories.ProductVariantRepository;
import com.thientdk.e_commerce_api.utils.UploadUtils;
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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantAttributeRepository productVariantAttributeRepository;


    /*todo: check white list products, just get product is white list*/
    /*Products*/

    /*Insert*/
    public TextResponse insertProduct(MultipartFile file, ProductRequest request) throws IOException {
        log.info("[insertProduct] - START");

        /*Upload image*/
        try {

            String fileUrl = UploadUtils.uploadProductImage(file);

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
    public TextResponse updateProduct(MultipartFile file, ProductRequest request) throws IOException {
        log.info("[updateProduct] - START");
        Optional<ProductEntity> opt = productRepository.findById(request.getId());
        if (opt.isEmpty()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Product not found");
        }
        /*Upload image*/
        try {

            String fileUrl = UploadUtils.uploadProductImage(file);

            ProductEntity productEntity = opt.get();
            productEntity.setName(request.getName());
            productEntity.setCategoryId(request.getCategoryId());
            productEntity.setBasePrice(request.getBasePrice());
            productEntity.setDescription(request.getDescription());
            productEntity.setImageUrl(fileUrl);

            productRepository.save(productEntity);

            log.info("[updateProduct] - END");
            return new TextResponse("Update product successfully");
        }
        catch (Exception e) {
            log.info("[updateProduct] - ERROR: {}", e.getMessage());
            return new TextResponse("Update product failed: " + e.getMessage());
        }

    }

    /*Get one*/
    public ProductEntity getOne(String id) {
        log.info("[getOne] - START");
        ProductEntity productEntity = productRepository.findById(id).orElse(null);
        if (productEntity == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Product not found");
        }
        log.info("[getOne] - END");
        return productEntity;
    }
    /*Get list*/

    /*Delete*/ /*todo: delete related product variants*/


    /*Product Variants*/

    /*Insert update*/

    /*Get one*/

    /*Get list*/

    /*Delete*/

    /*Product Variant Attributes*/

    /*Insert update*/

    /*Delete*/
}
