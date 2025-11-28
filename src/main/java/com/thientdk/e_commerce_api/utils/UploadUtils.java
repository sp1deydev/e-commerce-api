package com.thientdk.e_commerce_api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UploadUtils {

    @Value("${uploads.product-image-dir}")
    public static String productImageDir;

    @Value("${uploads.image-pre-url}")
    public static String imagePreUrl;

    public static String uploadProductImage(MultipartFile file) throws IOException {
        Path folderPath = Paths.get(productImageDir).normalize();
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String fileName = timestamp + "_" + file.getOriginalFilename();
        Path filePath = folderPath.resolve(fileName);

        Files.write(filePath, file.getBytes());

        return imagePreUrl + productImageDir + fileName;
    }
}

