package com.thientdk.e_commerce_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thientdk.e_commerce_api.aop.exceptions.ApiException;
import com.thientdk.e_commerce_api.aop.exceptions.ErrorCode;
import com.thientdk.e_commerce_api.entities.CategoryEntity;
import com.thientdk.e_commerce_api.models.dtos.CategoryDto;
import com.thientdk.e_commerce_api.models.dtos.CategoriesDto;
import com.thientdk.e_commerce_api.models.responses.CategoriesResponse;
import com.thientdk.e_commerce_api.models.responses.TextResponse;
import com.thientdk.e_commerce_api.repositories.CategoryRepository;
import com.thientdk.e_commerce_api.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ObjectMapper objectMapper;

    public TextResponse insertUpdateCategory(CategoryDto request) {
        if (request.getId() == null) {
            log.info("[insertUpdateCategory] - INSERT START");

            CategoryEntity entity = new CategoryEntity();
            entity.setName(request.getName());
            entity.setSlug(request.getSlug());
            entity.setParentId(request.getParentId());
            entity.setDescription(request.getDescription());
//            if (request.getParentId() != null) {
//                Optional<CategoryEntity> optParent = categoryRepository.findById(request.getParentId());
//                if (optParent.isEmpty()) {
//                    log.error("[insertUpdateCategory] - ERROR - Parent ID: {} is not found", request.getParentId());
//                    throw new ApiException(ErrorCode.BAD_REQUEST, "Parent ID is not found");
//                }
//                entity.setParent(optParent.get());
//            }
            categoryRepository.save(entity);
            log.info("[insertUpdateCategory] - INSERT END");
            return new TextResponse("Insert category successfully");
        } else {
            log.info("[insertUpdateCategory] - UPDATE START");
            Optional<CategoryEntity> opt = categoryRepository.findById(request.getId());
            if (opt.isEmpty()) {
                log.error("[insertUpdateCategory] - ERROR - Category with ID: {} not found ", request.getParentId());
                throw new ApiException(ErrorCode.BAD_REQUEST, "Category is not found");
            }
            CategoryEntity entity = opt.get();

            entity.setName(request.getName());
            entity.setSlug(request.getSlug());
            entity.setParentId(request.getParentId());
            entity.setDescription(request.getDescription());
//            if (request.getParentId() != null) {
//                Optional<CategoryEntity> optParent = categoryRepository.findById(request.getParentId());
//                if (optParent.isEmpty()) {
//                    log.error("[insertUpdateCategory] - ERROR - Parent ID: {} is not found", request.getParentId());
//                    throw new ApiException(ErrorCode.BAD_REQUEST, "Parent ID is not found");
//                }
//                entity.setParent(optParent.get());
//            }
            categoryRepository.save(entity);
            log.info("[insertUpdateCategory] - UPDATE END");
            return new TextResponse("Update category successfully");
        }
    }

    public TextResponse deleteCategory(String id) {
        log.info("[deleteCategory] - START");
        if (StringUtil.isNullOrEmpty(id) || !categoryRepository.existsById(id)) {
            log.error("[deleteCategory] - Category not found");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
        categoryRepository.deleteAllByParentId(id);
        log.info("[deleteCategory] - END");
        return new TextResponse("Delete category successfully.");
    }

    public List<CategoriesResponse> getCategories() {
        log.info("[getCategories] - START");
        List<CategoriesResponse> response = null;
        try {
            List<CategoriesDto> result = categoryRepository.getListCategories();
            log.info("[getCategories] - END");
            return result.stream().map((data) -> {
                try {
                    return mappingCatogories(data);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
        }
        catch (Exception e) {
            log.error("[getCategories] - ERROR - {}", e.getMessage());
        }
        return response;
    }

    private CategoriesResponse mappingCatogories(CategoriesDto dto) throws JsonProcessingException {
        CategoriesResponse response = new CategoriesResponse();
        response.setId(dto.getId());
        response.setName(dto.getName());
        response.setSlug(dto.getSlug());
        response.setParentId(dto.getParentId());
        response.setDescription(dto.getDescription());
        if (dto.getChildren() != null) {
            response.setChildren(objectMapper.readValue(
                    dto.getChildren(),
                    new TypeReference<>() {
                    }
            ));
        }
        return response;
    }
}
