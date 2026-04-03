package com.example.FakeCommerce.services;
import java.util.List;

import com.example.FakeCommerce.schema.Category;
import com.example.FakeCommerce.schema.Product;
import com.example.FakeCommerce.dtos.CreateProductRequestDto;
import com.example.FakeCommerce.repositories.ProductRepository; 

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    public List<Product> getAllProducts(){
        //findAll() is a method provided by JpaRepository which returns all the records from the database
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException( "Product not found"));
    }

    public Product createProduct(CreateProductRequestDto requestDto){
        Category category = categoryService.getCategoryById(requestDto.getCategoryId());
        Product newProduct = Product.builder().title(requestDto.getTitle())
                                    .description(requestDto.getDescription())
                                    .price(requestDto.getPrice())
                                    .image(requestDto.getImage())
                                    .category(category)
                                    .rating(requestDto.getRating())
                                    .build();   
        return productRepository.save(newProduct);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);

    }

    public List<Product> getProductsByCategory(String category){
        return productRepository.findByCategory(category);
    }


    // second get mapping using params
   public List<Product> getProductsByCategoryTemp(String category){
        return productRepository.findByCategory(category);
    }

    // public List<String> getUniqueCategories() {
    //     List<Product> products = productRepository.findAll();
    //     return products.stream()
    //                    .map(Product::getCategory)
    //                    .distinct()
    //                    .toList();
    // }

    public List<String> getUniqueCategories(){
        return productRepository.findAllCategories();
    }
}
