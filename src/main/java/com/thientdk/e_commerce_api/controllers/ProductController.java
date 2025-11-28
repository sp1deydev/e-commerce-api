package com.thientdk.e_commerce_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thientdk.e_commerce_api.entities.ProductEntity;
import com.thientdk.e_commerce_api.models.requests.ProductRequest;
import com.thientdk.e_commerce_api.models.responses.TextResponse;
import com.thientdk.e_commerce_api.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "")
    public TextResponse insertProduct(@RequestPart("file") MultipartFile file,
                                      @RequestPart("data") String data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest request = mapper.readValue(data, ProductRequest.class);
        return productService.insertProduct(file, request);
    }

    @GetMapping("/{id}")
    public ProductEntity getOne(@PathVariable("id") String id) {
        return productService.getOne(id);
    }
}
