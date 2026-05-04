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
    public List<Category> getAllCategories(){
        System.out.println("Getting all categories is hit by api call");
        return categoryService.getAllCategories();
    }
 
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable long id){
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
    }
}
