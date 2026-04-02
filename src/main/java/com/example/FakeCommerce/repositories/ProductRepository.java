package com.example.FakeCommerce.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.FakeCommerce.schema.Product;
import java.util.List;

@Repository
// here we are giving the type of entity and the type of primary key to the JpaRepository
public interface ProductRepository extends JpaRepository<Product , Long> {
    
    // function made by me 
    List<Product> findByCategory(String category);
    @Query(nativeQuery = true, value = "SELECT DISTINCT  category FROM products")
    List<String>findAllCategories();
}
