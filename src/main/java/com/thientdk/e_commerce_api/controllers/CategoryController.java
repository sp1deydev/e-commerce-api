package com.thientdk.e_commerce_api.controllers;

import com.thientdk.e_commerce_api.models.dtos.CategoryDto;
import com.thientdk.e_commerce_api.models.dtos.CategoriesDto;
import com.thientdk.e_commerce_api.models.responses.CategoriesResponse;
import com.thientdk.e_commerce_api.models.responses.TextResponse;
import com.thientdk.e_commerce_api.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public List<CategoriesResponse> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping()
    public TextResponse insertUpdateCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.insertUpdateCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    public TextResponse deleteCategory(@PathVariable(name = "id", required = false) String id) {
        return categoryService.deleteCategory(id);
    }
}
