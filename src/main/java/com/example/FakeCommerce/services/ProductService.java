package com.example.FakeCommerce.services;
import java.util.ArrayList;
import java.util.List;

import com.example.FakeCommerce.schema.Category;
import com.example.FakeCommerce.schema.Product;
import com.example.FakeCommerce.dtos.CreateProductRequestDto;
import com.example.FakeCommerce.dtos.GetProductResponseDto;
import com.example.FakeCommerce.repositories.ProductRepository; 

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    public List<GetProductResponseDto> getAllProducts(){
        //findAll() is a method provided by JpaRepository which returns all the records from the database
        //return productRepository.findAll();
        List<Product> products = productRepository.findAll();

        List<GetProductResponseDto>responseDtos = new ArrayList<>();

        for(Product product : products){
             GetProductResponseDto responseDto = GetProductResponseDto.builder()
                                        .id(product.getId())
                                        .title(product.getTitle())
                                        .description(product.getDescription())
                                        .price(product.getPrice())
                                        .image(product.getImage())
                                        .rating(product.getRating())
                                        .build();
            responseDtos.add(responseDto);
        }
        return responseDtos;


        // With help of stream api
        // return products.stream().map(product -> GetProductResponseDto.builder()
        //                                 .id(product.getId())
        //                                 .title(product.getTitle())   
        //                                 .description(product.getDescription())
        //                                 .price(product.getPrice())
        //                                 .image(product.getImage())
        //                                 .rating(product.getRating())
        //                                 .build()).toList();
        
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
