package com.example.FakeCommerce.controllers;

import com.example.FakeCommerce.FakeCommerceApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FakeCommerce.services.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.FakeCommerce.dtos.CreateProductRequestDto;
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
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @PostMapping
    public Product createProduct(@RequestBody CreateProductRequestDto requestDto){
        return productService.createProduct(requestDto);
    }
    
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category){
        return productService.getProductsByCategory(category);
    }
     @GetMapping("/search")
    public List<Product>
    getProductsByCategoryTemp(@RequestParam("category")String category){
        return productService.getProductsByCategory(category);
    }

    // write an api to get all unique categories
    @GetMapping("/Allcategories")
    public List<String> getUniqueCategories() {
        return productService.getUniqueCategories();
    }

    @GetMapping("/uniqueCategories")
    public List<String> getAllUniqueCategories(){
        return productService.getUniqueCategories();       
    }
}
