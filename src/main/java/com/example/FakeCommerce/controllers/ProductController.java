package com.example.FakeCommerce.controllers;

import com.example.FakeCommerce.FakeCommerceApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FakeCommerce.services.ProductService;
import com.example.FakeCommerce.utils.ApiResponse;
import com.example.FakeCommerce.exceptions.ResourceNotFoundException;
import com.example.FakeCommerce.exceptions.NoProductsFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.FakeCommerce.dtos.CreateProductRequestDto;
import com.example.FakeCommerce.dtos.GetProductResponseDto;
import com.example.FakeCommerce.dtos.GetProductWithDetailsResponseDto;
import com.example.FakeCommerce.schema.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor

public class ProductController {

   // private final FakeCommerce.FakeCommerceApplication fakeCommerceApplication;
    private final ProductService productService;

   //

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetProductResponseDto>>> getAllProducts() {
        List<GetProductResponseDto> products = productService.getAllProducts();
        if (products == null || products.isEmpty()) {
            throw new NoProductsFoundException("No products found");
        }
        return ResponseEntity.ok(ApiResponse.success(products, "Products fetched successfully"));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody CreateProductRequestDto requestDto) {
        Product product = productService.createProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(product, "Product created successfully"));
    }
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<GetProductResponseDto>> getProductById(@PathVariable Long id) {
        GetProductResponseDto product = null;
        try {
            product = productService.getProductById(id);
        } catch (RuntimeException ex) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        return ResponseEntity.ok(ApiResponse.success(product, "Product fetched successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        try {
            productService.getProductById(id);
        } catch (RuntimeException ex) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Product deleted successfully"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        if (products == null || products.isEmpty()) {
            throw new NoProductsFoundException("No products found for category: " + category);
        }
        return ResponseEntity.ok(ApiResponse.success(products, "Products fetched successfully for category: " + category));
    }
     @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategoryTemp(@RequestParam("category") String category) {
        List<Product> products = productService.getProductsByCategory(category);
        if (products == null || products.isEmpty()) {
            throw new NoProductsFoundException("No products found for category: " + category);
        }
        return ResponseEntity.ok(ApiResponse.success(products, "Products fetched successfully for category: " + category));
    }

    // write an api to get all unique categories
    @GetMapping("/Allcategories")
    public ResponseEntity<ApiResponse<List<String>>> getUniqueCategories() {
        List<String> categories = productService.getUniqueCategories();
        if (categories == null || categories.isEmpty()) {
            throw new NoProductsFoundException("No unique categories found");
        }
        return ResponseEntity.ok(ApiResponse.success(categories, "Unique categories fetched successfully"));
    }

    @GetMapping("/uniqueCategories")
    public ResponseEntity<ApiResponse<List<String>>> getAllUniqueCategories() {
        List<String> categories = productService.getUniqueCategories();
        if (categories == null || categories.isEmpty()) {
            throw new NoProductsFoundException("No unique categories found");
        }
        return ResponseEntity.ok(ApiResponse.success(categories, "Unique categories fetched successfully"));
    }
     
    // Adder after git commit 3 and to check with postman
     @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse<GetProductWithDetailsResponseDto>> getProductWithDetails(@PathVariable Long id) {
        GetProductWithDetailsResponseDto productDetails;
        try {
            productDetails = productService.getProductWithDetailsById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Product details not found with id: " + id);
        }
        return ResponseEntity.ok(ApiResponse.success(productDetails, "Product details fetched successfully"));
    }
}
