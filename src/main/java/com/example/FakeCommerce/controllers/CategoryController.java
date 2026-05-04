package com.example.FakeCommerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FakeCommerce.dtos.CreateCategoryRequestDto;
import com.example.FakeCommerce.schema.Category;
import com.example.FakeCommerce.services.CategoryService;
import com.example.FakeCommerce.utils.ApiResponse;
import com.example.FakeCommerce.exceptions.ResourceNotFoundException;
import com.example.FakeCommerce.exceptions.NoCategoriesFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody CreateCategoryRequestDto requestDto){
         Category category = categoryService.createCategory(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(category,"Category created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        System.out.println("Getting all categories is hit by api call");
        List<Category> categories = categoryService.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            throw new NoCategoriesFoundException("No categories found");
        }
        return ResponseEntity.ok(ApiResponse.success(categories, "Categories fetched successfully"));
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        return ResponseEntity.ok(ApiResponse.success(category, "Category fetched successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Category deleted successfully"));
    }
}
