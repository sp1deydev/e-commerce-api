package com.thientdk.e_commerce_api.services;

import com.thientdk.e_commerce_api.aop.exceptions.ApiException;
import com.thientdk.e_commerce_api.aop.exceptions.ErrorCode;
import com.thientdk.e_commerce_api.entities.CategoryEntity;
import com.thientdk.e_commerce_api.models.dtos.CategoryDto;
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

    public TextResponse insertUpdateCategory(CategoryDto request) {
        if (request.getId() == null) {
            log.info("[insertUpdateCategory] - INSERT START");
            if (!categoryRepository.existsById(request.getParentId())) {
                log.error("[insertUpdateCategory] - ERROR - Parent ID: {} is not found", request.getParentId());
                throw new ApiException(ErrorCode.BAD_REQUEST, "Parent ID is not found");
            }
            CategoryEntity entity = new CategoryEntity();
            entity.setName(request.getName());
            entity.setSlug(request.getSlug());
            entity.setParentId(request.getParentId());
            entity.setDescription(request.getDescription());
            categoryRepository.save(entity);
            log.info("[insertUpdateCategory] - INSERT END");
            return new TextResponse("Insert category successfully");
        }
        else {
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
        log.info("[deleteCategory] - END");
        return new TextResponse("Delete category successfully.");
    }

    public List<CategoryEntity> getListCategories() {
        log.info("[getListCategories] - START");
        List<CategoryEntity> result = categoryRepository.findByParentIsNull();
        log.info("[getListCategories] - END");
        return result;
    }

}
